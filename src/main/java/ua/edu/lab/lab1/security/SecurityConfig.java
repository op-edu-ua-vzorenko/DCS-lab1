package ua.edu.lab.lab1.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider; // Додали залежність

    // Тепер ми приймаємо AuthenticationProvider, який створено в іншому файлі
    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // --- 1. БЛОК ЗАПРЕТОВ (Специфичные правила) ---

                        // Создавать и менять каналы может ТОЛЬКО Админ
                        .requestMatchers(HttpMethod.POST, "/channels/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/channels/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/channels/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/channels/**").hasAnyRole("USER", "ADMIN")

                        // --- 2. БЛОК ВСЕДОЗВОЛЕННОСТИ ---

                        // Всё остальное (включая GET /channels, /posts, /auth и будущие контроллеры) - ОТКРЫТО
                        .anyRequest().permitAll()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}