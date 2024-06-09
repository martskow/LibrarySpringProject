package com.example.technologiesieciowe.service;

import com.example.technologiesieciowe.infrastructure.entity.UserEntity;
import com.example.technologiesieciowe.infrastructure.repository.UserRepository;
import com.example.technologiesieciowe.service.error.FieldRequiredException;
import com.example.technologiesieciowe.service.error.UserErrors.UserAccessDeniedException;
import com.example.technologiesieciowe.service.error.UserErrors.UserAlreadyExistsException;
import com.example.technologiesieciowe.service.error.UserErrors.UserEmailExistsException;
import com.example.technologiesieciowe.service.error.UserErrors.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Service class for managing users.
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Constructs a new instance of UserService.
     * @param userRepository The repository for users.
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Adds a new user.
     * @param user The user entity to add.
     * @return The added user entity.
     * @throws FieldRequiredException If any required field is null.
     * @throws UserAlreadyExistsException If a user with the same username already exists.
     * @throws UserEmailExistsException If a user with the same email already exists.
     */
    @Transactional
    public UserEntity addUser(UserEntity user) {
        if (user.getUserName() == null) {
            throw FieldRequiredException.create("User name");
        } else if (user.getUserPassword() == null) {
            throw FieldRequiredException.create("Password");
        } else if (user.getEmail() == null) {
            throw FieldRequiredException.create("Email");
        } else if (user.getUserFirstName() == null) {
            throw FieldRequiredException.create("First Name");
        } else if (user.getUserLastName() == null) {
            throw FieldRequiredException.create("Last Name");
        }

        Optional<UserEntity> existingUser = Optional.ofNullable(userRepository.getByUserName(user.getUserName()));
        if (existingUser.isPresent()) {
            throw UserAlreadyExistsException.create(user.getUserName());
        }

        Optional<UserEntity> existingEmail = Optional.ofNullable(userRepository.getByEmail(user.getEmail()));
        if (existingEmail.isPresent()) {
            throw UserEmailExistsException.create(user.getEmail());
        }

        String hashedPassword = passwordEncoder.encode(user.getUserPassword());
        user.setUserPassword(hashedPassword);
        return userRepository.save(user);
    }

    /**
     * Retrieves all users.
     * @return A list of all users.
     */
    public Iterable<UserEntity> getAll(){
        return userRepository.findAll();
    }

    /**
     * Retrieves a specific user by their ID.
     * @param userId The ID of the user to retrieve.
     * @return The user entity.
     * @throws UserNotFoundException If the user with the specified ID is not found.
     */
    public UserEntity getOne(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.create(userId.toString()));
    }

    /**
     * Deletes a user by their ID.
     * @param id The ID of the user to delete.
     */
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    /**
     * Edits a user.
     * @param userId The ID of the user to edit.
     * @param editedUser The edited user entity.
     * @return The edited user entity.
     * @throws UserNotFoundException If the user with the specified ID is not found.
     * @throws UserAccessDeniedException If the logged-in user is not authorized to edit the user.
     * @throws UserAlreadyExistsException If the new username already exists.
     * @throws UserEmailExistsException If the new email already exists.
     */
    public UserEntity editUser(Integer userId, UserEntity editedUser) {
        UserEntity userToEdit = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.create(userId.toString()));

        String loggedInUsername = LoginService.getLoggedInUserId();
        String loggedInUserRole = LoginService.getLoggedInUserRole();
        if (!((loggedInUserRole.equals("ROLE_ADMIN") || loggedInUserRole.equals("ROLE_LIBRARIAN")) ||
                (userToEdit.getUserId().toString().equals(loggedInUsername)))) {
            throw UserAccessDeniedException.create("You are not allowed to edit information about this user.");
        }

        String newUserName = editedUser.getUserName();
        String newEmail = editedUser.getEmail();
        String newFirstName = editedUser.getUserFirstName();
        String newLastName = editedUser.getUserLastName();

        if (newUserName != null) {
            Optional<UserEntity> existingUser = Optional.ofNullable(userRepository.getByUserName(editedUser.getUserName()));
            if (existingUser.isPresent()) {
                throw UserAlreadyExistsException.create(userToEdit.getUserName());
            } else {
                userToEdit.setUserName(newUserName);
            }

        }
        if (newEmail != null) {
            Optional<UserEntity> existingEmail = Optional.ofNullable(userRepository.getByEmail(editedUser.getEmail()));
            if (existingEmail.isPresent()) {
                throw UserEmailExistsException.create(userToEdit.getEmail());
            } else {
                userToEdit.setEmail(newEmail);
            }
        }
        if (newFirstName != null) {
            userToEdit.setUserFirstName(newFirstName);
        }
        if (newLastName != null) {
            userToEdit.setUserLastName(newLastName);
        }

        return userRepository.save(userToEdit);
    }

    /**
     * Changes the password for a user.
     * @param userId The ID of the user whose password is to be changed.
     * @param userToChangePassword The entity containing the new password.
     * @return The user entity with the updated password.
     * @throws UserNotFoundException If the user with the specified ID is not found.
     * @throws UserAccessDeniedException If the logged-in user is not authorized to change the password.
     * @throws FieldRequiredException If the new password is null.
     */
    public UserEntity newUserPassword (Integer userId, UserEntity userToChangePassword) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.create(userId.toString()));

        String loggedInUsername = LoginService.getLoggedInUserId();
        String loggedInUserRole = LoginService.getLoggedInUserRole();
        if (!(loggedInUserRole.equals("ROLE_ADMIN") || (userId.toString().equals(loggedInUsername)))) {
            throw UserAccessDeniedException.create("You are not allowed to change this user password.");
        }

        if (userToChangePassword.getUserPassword() == null) {
            throw FieldRequiredException.create("Password");
        } else {
            String newHashedPassword = passwordEncoder.encode(userToChangePassword.getUserPassword());
            user.setUserPassword(newHashedPassword);
        }

        return userRepository.save(user);
    }

    public Map<String, Integer> userStats (Integer userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.create(userId.toString()));

        String loggedInUsername = LoginService.getLoggedInUserId();
        String loggedInUserRole = LoginService.getLoggedInUserRole();
        if (!(loggedInUserRole.equals("ROLE_ADMIN") || (userId.toString().equals(loggedInUsername)))) {
            throw UserAccessDeniedException.create("You are not allowed to get this user information.");
        }

        Map<String, Integer> stats = new HashMap<>();
        Integer borrowedBooks = user.getLoans().size() + user.getArchiveLoans().size();
        Integer reviews = user.getReviews().size();
        Integer nowReading = user.getLoans().size();
        Integer inQueue = user.getQueues().size();
        stats.put("Borrowed books", borrowedBooks);
        stats.put("Reviews", reviews);
        stats.put("Now reading", nowReading);
        stats.put("In queue", inQueue);
        return stats;
    }
}