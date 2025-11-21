package ua.edu.lab.lab1.dto;

public class AuthRequest {
    private String username;
    private String password;
    private String planet; // <-- 1. Новое поле

    // Геттеры и сеттеры
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPlanet() { return planet; } // <-- 2. Геттер
    public void setPlanet(String planet) { this.planet = planet; } // <-- 3. Сеттер
}