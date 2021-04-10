package nl.shashi.graphql.graphqlspring.service.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import nl.shashi.graphql.graphqlspring.domain.Book;
import nl.shashi.graphql.graphqlspring.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookDataFetcher implements DataFetcher<Book> {

    private final BookRepository bookRepository;

    @Autowired
    public BookDataFetcher(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book get(DataFetchingEnvironment dataFetchingEnvironment) {
        String isn = dataFetchingEnvironment.getArgument("id");
        return bookRepository.findById(isn).get();
    }
}
