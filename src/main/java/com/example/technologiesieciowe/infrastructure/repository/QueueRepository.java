package com.example.technologiesieciowe.infrastructure.repository;

import com.example.technologiesieciowe.infrastructure.entity.QueueEntity;
import com.example.technologiesieciowe.infrastructure.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing and manipulating QueueEntity objects in the database.
 */
@Repository
public interface QueueRepository extends JpaRepository<QueueEntity, Integer> {

    /**
     * Retrieves all places in a queue for a given book ID.
     *
     * @param bookId The ID of the book for which places in a queue are to be retrieved.
     * @return An Iterable containing all places in a queue associated with the given book ID.
     */
    Iterable<QueueEntity> getByBookId(Integer bookId);

    /**
     * Retrieves all places in a queue for a given user ID.
     *
     * @param userId The ID of the user for which places in a queue are to be retrieved.
     * @return An Iterable containing all places in a queue associated with the given user ID.
     */
    Iterable<QueueEntity> getByUserUserId(Integer userId);
}
