package com.example.technologiesieciowe.infrastructure.repository;

import com.example.technologiesieciowe.infrastructure.entity.BookEntity;
import com.example.technologiesieciowe.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Repository interface for accessing and manipulating BookEntity objects in the database.
 */
@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer> {

    /**
     * Retrieves a BookEntity by its ISBN.
     *
     * @param isbn The ISBN of the book to retrieve.
     * @return The BookEntity with the specified ISBN, or null if not found.
     */
    BookEntity getByIsbn(String isbn);

    /**
     * Retrieves a BookEntity by its title.
     *
     * @param title The title of the book to retrieve.
     * @return The BookEntity with the specified title, or null if not found.
     */
    BookEntity getByTitle(String title);
}