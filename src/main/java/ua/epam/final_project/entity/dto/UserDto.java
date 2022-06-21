package ua.epam.final_project.entity.dto;

import ua.epam.final_project.entity.User;

public class UserDto {

    private int id;
    private String login;
    private String password;
    private String email;
    private String name;
    private String userImage;
    private int balance;
    private String role;

    public UserDto() {}
    /**
     * Create UserDto based on User entity
     * @param user - user entity
     */
    public UserDto(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.password = "";
        this.email = user.getEmail();
        this.name = user.getName();
        this.userImage = user.getEmail();
        this.balance = user.getBalance();
        this.role = user.getRole();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
