package com.example.technologiesieciowe.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

/**
 * Entity class representing a book.
 */
@Entity
@Table(name = "books", schema = "library")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<LoanEntity> loans;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<LoanArchiveEntity> archiveLoans;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ReviewEntity> reviews;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<QueueEntity> queues;

    @Basic
    @Column(name = "isbn", unique = true, nullable = false)
    private String isbn;

    @Basic
    @Column(name = "title", nullable = false)
    private String title;

    @Basic
    @Column(name = "author", nullable = false)
    private String author;

    @Basic
    @Column(name = "publisher")
    private String publisher;

    @Basic
    @Column(name = "publish_year")
    private String publishYear;

    @Basic
    @Column(name = "available_copies", nullable = false)
    private Integer availableCopies;

    /**
     * Get the ID of the book.
     * @return The ID of the book.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set the ID of the book.
     * @param id The ID of the book to set.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Get the ISBN of the book.
     * @return The ISBN of the book.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Set the ISBN of the book.
     * @param isbn The ISBN of the book to set.
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Get the title of the book.
     * @return The title of the book.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the title of the book.
     * @param title The title of the book to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the author of the book.
     * @return The author of the book.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Set the author of the book.
     * @param author The author of the book to set.
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Get the publisher of the book.
     * @return The publisher of the book.
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Set the publisher of the book.
     * @param publisher The publisher of the book to set.
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * Get the publish year of the book.
     * @return The publish year of the book.
     */
    public String getPublishYear() {
        return publishYear;
    }

    /**
     * Set the publish year of the book.
     * @param publishYear The publish year of the book to set.
     */
    public void setPublishYear(String publishYear) {
        this.publishYear = publishYear;
    }

    /**
     * Get the number of available copies of the book.
     * @return The number of available copies of the book.
     */
    public Integer getAvailableCopies() {
        return availableCopies;
    }

    /**
     * Set the number of available copies of the book.
     * @param availableCopies The number of available copies of the book to set.
     */
    public void setAvailableCopies(Integer availableCopies) {
        this.availableCopies = availableCopies;
    }

    /**
     * Get the list of loans associated with the book.
     * @return The list of loans associated with the book.
     */
    public List<LoanEntity> getLoans() {
        return loans;
    }

    /**
     * Set the list of loans associated with the book.
     * @param loans The list of loans associated with the book to set.
     */
    public void setLoans(List<LoanEntity> loans) {
        this.loans = loans;
    }

    /**
     * Get the list of loan archive entries associated with the book.
     * @return The list of loan archive entries associated with the book.
     */
    public List<LoanArchiveEntity> getArchiveLoans() {
        return archiveLoans;
    }

    /**
     * Set the list of loan archive entries associated with the book.
     * @param archiveLoans The list of loan archive entries associated with the book to set.
     */
    public void setArchiveLoans(List<LoanArchiveEntity> archiveLoans) {
        this.archiveLoans = archiveLoans;
    }

    /**
     * Get the list of reviews associated with the book.
     * @return The list of reviews associated with the book.
     */
    public List<ReviewEntity> getReviews() {
        return reviews;
    }

    /**
     * Set the list of reviews associated with the book.
     * @param reviews The list of reviews associated with the book to set.
     */
    public void setReviews(List<ReviewEntity> reviews) {
        this.reviews = reviews;
    }

    /**
     * Get the list of queues associated with the book.
     * @return The list of queues associated with the book.
     */
    public List<QueueEntity> getQueues() {
        return queues;
    }

    /**
     * Set the list of queues associated with the book.
     * @param queues The list of queues associated with the book to set.
     */
    public void setQueues(List<QueueEntity> queues) {
        this.queues = queues;
    }
}