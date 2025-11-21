package ua.edu.lab.lab1.dto;

public class AuthRequest {
    private String username;
    private String password;

    // Геттери і сеттери
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}