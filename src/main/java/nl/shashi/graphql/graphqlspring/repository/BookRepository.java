package nl.shashi.graphql.graphqlspring.repository;

import nl.shashi.graphql.graphqlspring.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
}
