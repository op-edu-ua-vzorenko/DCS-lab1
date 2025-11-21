package ua.edu.lab.lab1.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService; // сервіс, який знає про юзерів

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        // 1. Перевіряємо, чи є заголовок і чи починається він з "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); // Відрізаємо "Bearer " (7 символів)
            username = jwtService.extractUsername(token); // Витягуємо ім'я з токена
        }

        // 2. Якщо ім'я є, а в контексті безпеки ще пусто (користувач не зайшов)
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Завантажуємо деталі користувача з нашої "бази" (UserDetailsService)
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // 3. Валідуємо токен
            if (jwtService.validateToken(token, userDetails)) {
                // Створюємо об'єкт аутентифікації
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 4. Встановлюємо аутентифікацію в контекст (тепер Спрінг знає, хто це)
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Пропускаємо запит далі по ланцюжку
        filterChain.doFilter(request, response);
    }
}