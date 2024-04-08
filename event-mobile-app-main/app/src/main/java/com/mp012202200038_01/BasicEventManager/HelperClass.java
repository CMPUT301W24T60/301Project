/*
 * HelperClass
 * This class represents a helper class for managing user information.
 */

package com.mp012202200038_01.BasicEventManager;

/**
 * The type Helper class.
 */
public class HelperClass {
    /**
     * The Name.
     */
    String name, /**
     * The Email.
     */
    email, /**
     * The Username.
     */
    username, /**
     * The Password.
     */
    password;

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Instantiates a new Helper class.
     *
     * @param name     the name
     * @param email    the email
     * @param username the username
     * @param password the password
     */
    public HelperClass(String name, String email, String username, String password) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    /**
     * Instantiates a new Helper class.
     */
    public HelperClass() {
    }
}
