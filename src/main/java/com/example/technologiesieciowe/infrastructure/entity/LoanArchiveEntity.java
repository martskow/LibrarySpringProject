package com.example.technologiesieciowe.infrastructure.entity;

import jakarta.persistence.*;

/**
 * Entity class representing a loan archive entry.
 */
@Entity
@Table(name = "loans_archive", schema = "library")
public class LoanArchiveEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "loanarchive_id")
    private Integer loanArchiveId;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Basic
    @Column(name = "loan_date", nullable = false)
    private String loanDate;

    @Column(name = "due_date", nullable = false)
    private String dueDate;

    @Column(name = "return_date", nullable = false)
    private String returnDate;

    @Column(name = "isAfterdue_date")
    private Boolean isAfterDueDate;

    /**
     * Get the ID of the loan archive entry.
     * @return The ID of the loan archive entry.
     */
    public Integer getLoanArchiveId() {
        return loanArchiveId;
    }

    /**
     * Set the ID of the loan archive entry.
     * @param loanArchiveId The ID of the loan archive entry to set.
     */
    public void setLoanArchiveId(Integer loanArchiveId) {
        this.loanArchiveId = loanArchiveId;
    }

    /**
     * Get the book associated with the loan archive entry.
     * @return The book associated with the loan archive entry.
     */
    public BookEntity getBook() {
        return book;
    }

    /**
     * Set the book associated with the loan archive entry.
     * @param book The book associated with the loan archive entry to set.
     */
    public void setBook(BookEntity book) {
        this.book = book;
    }

    /**
     * Get the user associated with the loan archive entry.
     * @return The user associated with the loan archive entry.
     */
    public UserEntity getUser() {
        return user;
    }

    /**
     * Set the user associated with the loan archive entry.
     * @param user The user associated with the loan archive entry to set.
     */
    public void setUser(UserEntity user) {
        this.user = user;
    }

    /**
     * Get the loan date of the loan archive entry.
     * @return The loan date of the loan archive entry.
     */
    public String getLoanDate() {
        return loanDate;
    }

    /**
     * Set the loan date of the loan archive entry.
     * @param loanDate The loan date of the loan archive entry to set.
     */
    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    /**
     * Get the due date of the loan archive entry.
     * @return The due date of the loan archive entry.
     */
    public String getDueDate() {
        return dueDate;
    }

    /**
     * Set the due date of the loan archive entry.
     * @param dueDate The due date of the loan archive entry to set.
     */
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Get the return date of the loan archive entry.
     * @return The return date of the loan archive entry.
     */
    public String getReturnDate() {
        return returnDate;
    }

    /**
     * Set the return date of the loan archive entry.
     * @param returnDate The return date of the loan archive entry to set.
     */
    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * Check if the return date of the loan archive entry is after the due date.
     * @return True if the return date is after the due date, otherwise false.
     */
    public Boolean getIsAfterDueDate() {
        return isAfterDueDate;
    }

    /**
     * Set whether the return date of the loan archive entry is after the due date.
     * @param isAfterDueDate True if the return date is after the due date, otherwise false.
     */
    public void setIsAfterDueDate(Boolean isAfterDueDate) {
        this.isAfterDueDate = isAfterDueDate;
    }
}