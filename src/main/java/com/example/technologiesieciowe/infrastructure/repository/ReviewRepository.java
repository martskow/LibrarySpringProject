package com.example.technologiesieciowe.infrastructure.repository;

import com.example.technologiesieciowe.infrastructure.entity.BookEntity;
import com.example.technologiesieciowe.infrastructure.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Repository interface for accessing and manipulating ReviewEntity objects in the database.
 */
@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {

    /**
     * Retrieves all reviews for a given book ID.
     *
     * @param bookId The ID of the book for which reviews are to be retrieved.
     * @return An Iterable containing all reviews associated with the given book ID.
     */
    Iterable<ReviewEntity> findByBookId(Integer bookId);

    /**
     * Retrieves all reviews submitted by a given user ID.
     *
     * @param userId The ID of the user who submitted the reviews.
     * @return An Iterable containing all reviews submitted by the given user ID.
     */
    Iterable<ReviewEntity> findByUserUserId(Integer userId);
}