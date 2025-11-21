package ua.edu.lab.lab1.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    // Секретний ключ
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    // 1. Генерація токена (без додаткових claims)
    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName);
    }

    // 2. Генерація токена (з ролями та іншими даними)
    public String generateToken(String userName, Map<String, Object> claims) {
        return createToken(claims, userName);
    }

    // Створення токена
    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 30 хвилин
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Отримання ключа для підпису
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Витягування імені користувача (Subject) з токена
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Витягування дати закінчення дії токена
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Універсальний метод для витягування будь-якого claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Парсинг токена (отримання всіх даних)
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Перевірка, чи токен не прострочений
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // ВАЛІДАЦІЯ ТОКЕНА
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        // Токен валідний, якщо ім'я співпадає і час не вийшов
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}