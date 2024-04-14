package com.example.technologiesieciowe.infrastructure.repository;

import com.example.technologiesieciowe.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing and manipulating UserEntity objects in the database.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    /**
     * Retrieves a user by their username.
     *
     * @param userName The username of the user to retrieve.
     * @return The UserEntity object corresponding to the provided username, or null if not found.
     */
    UserEntity getByUserName(String userName);

    /**
     * Retrieves a user by their email address.
     *
     * @param email The email address of the user to retrieve.
     * @return The UserEntity object corresponding to the provided email address, or null if not found.
     */
    UserEntity getByEmail(String email);
}