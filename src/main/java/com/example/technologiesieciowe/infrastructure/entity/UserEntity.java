package com.example.technologiesieciowe.infrastructure.entity;

import com.example.technologiesieciowe.commonTypes.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;
/**
 * Entity class representing a user.
 */
@Entity
public class UserEntity {
    @Id@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer userId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<LoanEntity> loans;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<LoanArchiveEntity> archiveLoans;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ReviewEntity> reviews;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<QueueEntity> queues;

    @Basic
    @Column(name = "user_name", unique = true, nullable = false)
    private String userName;

    @Basic
    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    @Basic
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Basic
    @Column(name = "user_firstName", nullable = false)
    private String userFirstName;

    @Basic
    @Column(name = "user_lastName", nullable = false)
    private String userLastName;

    /**
     * Get the ID of the user.
     * @return The ID of the user.
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Set the ID of the user.
     * @param userId The ID of the user to set.
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    /**
     * Retrieves the list of loans associated with this user.
     * @return The list of loans.
     */
    public List<LoanEntity> getLoans() {
        return loans;
    }

    /**
     * Sets the list of loans associated with this user.
     * @param loans The list of loans to be set.
     */
    public void setLoans(List<LoanEntity> loans) {
        this.loans = loans;
    }

    /**
     * Retrieves the list of reviews written by this user.
     * @return The list of reviews.
     */
    public List<ReviewEntity> getReviews() {
        return reviews;
    }

    /**
     * Sets the list of reviews written by this user.
     * @param reviews The list of reviews to be set.
     */
    public void setReviews(List<ReviewEntity> reviews) {
        this.reviews = reviews;
    }

    /**
     * Retrieves the username of this user.
     * @return The username.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the username of this user.
     * @param userName The username to be set.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Retrieves the password of this user.
     * @return The password.
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * Sets the password of this user.
     * @param userPassword The password to be set.
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * Retrieves the role of this user.
     * @return The user role.
     */
    public UserRole getRole() {
        return role;
    }

    /**
     * Sets the role of this user.
     * @param role The role to be set.
     */
    public void setRole(UserRole role) {
        this.role = role;
    }

    /**
     * Retrieves the email address of this user.
     * @return The email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of this user.
     * @param email The email address to be set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the first name of this user.
     * @return The first name.
     */
    public String getUserFirstName() {
        return userFirstName;
    }

    /**
     * Sets the first name of this user.
     * @param userFirstName The first name to be set.
     */
    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    /**
     * Retrieves the last name of this user.
     * @return The last name.
     */
    public String getUserLastName() {
        return userLastName;
    }

    /**
     * Sets the last name of this user.
     * @param userLastName The last name to be set.
     */
    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    /**
     * Retrieves the list of archive loans associated with this user.
     * @return The list of archive loans.
     */
    public List<LoanArchiveEntity> getArchiveLoans() {
        return archiveLoans;
    }

    /**
     * Sets the list of archive loans associated with this user.
     * @param archiveLoans The list of archive loans to be set.
     */
    public void setArchiveLoans(List<LoanArchiveEntity> archiveLoans) {
        this.archiveLoans = archiveLoans;
    }

    /**
     * Retrieves the list of queues associated with this user.
     * @return The list of queues.
     */
    public List<QueueEntity> getQueues() {
        return queues;
    }

    /**
     * Sets the list of queues associated with this user.
     * @param queues The list of queues to be set.
     */
    public void setQueues(List<QueueEntity> queues) {
        this.queues = queues;
    }
}