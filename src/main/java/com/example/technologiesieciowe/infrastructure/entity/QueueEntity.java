package com.example.technologiesieciowe.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

/**
 * Entity class representing a queue.
 */
@Entity
@Table(name = "queue", schema = "library")
public class QueueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "queue_id")
    private Integer queueId;

    @ManyToOne
    @JsonIgnoreProperties({"loans", "archiveLoans", "reviews", "role", "userPassword", "email", "userFirstName", "userLastName"})
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;

    @ManyToOne
    @JsonIgnoreProperties({"loans", "archiveLoans", "reviews", "isbn", "author", "publisher", "publishYear", "availableCopies"})
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "queuing_date")
    private String queuingDate;

    /**
     * Get the ID of the queue.
     * @return The ID of the queue.
     */
    public Integer getQueueId() {
        return queueId;
    }

    /**
     * Set the ID of the queue.
     * @param queueId The ID of the queue to set.
     */
    public void setQueueId(Integer queueId) {
        this.queueId = queueId;
    }

    /**
     * Get the book associated with the queue.
     * @return The book associated with the queue.
     */
    public BookEntity getBook() {
        return book;
    }

    /**
     * Set the book associated with the queue.
     * @param book The book associated with the queue to set.
     */
    public void setBook(BookEntity book) {
        this.book = book;
    }

    /**
     * Get the user associated with the queue.
     * @return The user associated with the queue.
     */
    public UserEntity getUser() {
        return user;
    }

    /**
     * Set the user associated with the queue.
     * @param user The user associated with the queue to set.
     */
    public void setUser(UserEntity user) {
        this.user = user;
    }

    /**
     * Get the queuing date of the queue.
     * @return The queuing date of the queue.
     */
    public String getQueuingDate() {
        return queuingDate;
    }

    /**
     * Set the queuing date of the queue.
     * @param queuingDate The queuing date of the queue to set.
     */
    public void setQueuingDate(String queuingDate) {
        this.queuingDate = queuingDate;
    }
}