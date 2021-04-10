package nl.shashi.graphql.graphqlspring.service;

import com.fasterxml.jackson.databind.util.ArrayBuilders;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import nl.shashi.graphql.graphqlspring.domain.Book;
import nl.shashi.graphql.graphqlspring.repository.BookRepository;
import nl.shashi.graphql.graphqlspring.service.datafetcher.AllBooksDataFetcher;
import nl.shashi.graphql.graphqlspring.service.datafetcher.BookDataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

@Service
public class GraphQLService {


    @Value("classpath:books.graphql")
    Resource resource;

    private GraphQL graphQL;
    private final BookRepository bookRepository;
    private final AllBooksDataFetcher allBooksDataFetcher;
    private final BookDataFetcher bookDataFetcher;

    @Autowired
    public GraphQLService(BookRepository bookRepository, AllBooksDataFetcher allBooksDataFetcher, BookDataFetcher bookDataFetcher) {
        this.bookRepository = bookRepository;
        this.allBooksDataFetcher = allBooksDataFetcher;
        this.bookDataFetcher = bookDataFetcher;
    }

    @PostConstruct
    private void loadSchema() throws IOException {
        loadData();
        File schemaFile = resource.getFile();
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
        graphQL = GraphQL.newGraphQL(schema).build();
    }

    private void loadData() {

        Stream.of(

                new Book("1402728131", "Book of Clouds", "Kindle Edition",
                        new String[] {
                                "Chloe Aridjis"
                        }, "Nov 2017"),
                new Book("1593278551", "Cloud Arch & Engineering", "Oriel",
                        new String[] {
                                "Peter", "Sam"
                        }, "Jan 2015"),
                new Book("B01M0S60BV", "Java 9 Programming", "Oriel",
                        new String[] {
                                "Venkat", "Ram"
                        }, "Dec 2016")
        ).forEach(bookRepository::save);
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring -> typeWiring
                        .dataFetcher("allBooks", allBooksDataFetcher)
                        .dataFetcher("book", bookDataFetcher))
                .build();
    }


    public GraphQL getGraphQL() {
        return graphQL;
    }
}
