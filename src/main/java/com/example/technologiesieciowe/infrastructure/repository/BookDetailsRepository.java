package com.example.technologiesieciowe.infrastructure.repository;

import com.example.technologiesieciowe.infrastructure.entity.BookDetailsEntity;
import com.example.technologiesieciowe.infrastructure.entity.BookEntity;
import com.example.technologiesieciowe.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing and manipulating BookDetailsEntity objects in the database.
 */
@Repository
public interface BookDetailsRepository extends JpaRepository<BookDetailsEntity, Integer> {

    /**
     * Retrieves a BookDetailsEntity by its genre.
     *
     * @param genre The genre of the book details to retrieve.
     * @return The BookDetailsEntity with the specified genre, or null if not found.
     */
    List<BookDetailsEntity> getByGenre(String genre);
}