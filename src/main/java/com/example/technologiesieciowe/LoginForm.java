package com.example.technologiesieciowe;

/**
 * Represents a login form with login and password fields.
 */
public class LoginForm {
    private String login;
    private String password;

    /**
     * Constructs a login form with the specified login and password.
     *
     * @param login    the login
     * @param password the password
     */
    public LoginForm(String login, String password) {
        this.login = login;
        this.password = password;
    }

    /**
     * Gets the login.
     *
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets the login.
     *
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}