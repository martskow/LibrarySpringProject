package com.example.technologiesieciowe.infrastructure.repository;

import com.example.technologiesieciowe.infrastructure.entity.BookEntity;
import com.example.technologiesieciowe.infrastructure.entity.LoanArchiveEntity;
import com.example.technologiesieciowe.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * Repository interface for accessing and manipulating LoanArchiveEntity objects in the database.
 */
@Repository
public interface LoanArchiveRepository extends JpaRepository<LoanArchiveEntity, Integer> {

    /**
     * Checks if a loan archive entry exists for a given user and book.
     *
     * @param user Optional<UserEntity> representing the user involved in the loan archive entry.
     * @param book Optional<BookEntity> representing the book involved in the loan archive entry.
     * @return true if a loan archive entry exists for the given user and book, false otherwise.
     */
    boolean existsByUserAndBook(Optional<UserEntity> user, Optional<BookEntity> book);
}