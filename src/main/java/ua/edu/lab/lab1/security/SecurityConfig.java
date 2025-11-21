package ua.edu.lab.lab1.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Дозволяє використовувати @PreAuthorize в контролерах
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    // 1. Наші користувачі (Planet Express Crew)
    @Bean
    public UserDetailsService userDetailsService() {
        // BENDER - Адмін, може все (робить свій луна-парк з блекджеком)
        UserDetails bender = User.builder()
                .username("bender")
                .password(passwordEncoder().encode("beer")) // Пароль шифруємо
                .roles("ADMIN")
                .build();

        // FRY - Звичайний юзер, просто читає
        UserDetails fry = User.builder()
                .username("fry")
                .password(passwordEncoder().encode("slurm"))
                .roles("USER")
                .build();

        // ZAPP - Генерал, думає що головний, але теж просто юзер
        UserDetails zapp = User.builder()
                .username("zapp")
                .password(passwordEncoder().encode("kif"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(bender, fry, zapp);
    }

    // 2. Ланцюжок фільтрів безпеки (Охорона)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // 1. Открываем вход и документацию
                        .requestMatchers("/auth/**", "/error", "/swagger-ui/**").permitAll()

                        // 2. Настраиваем КАНАЛЫ (наша жертва)
                        // GET /channels - доступно ВСЕМ (даже без токена)
                        .requestMatchers(HttpMethod.GET, "/channels").permitAll()
                        // POST /channels - только для ADMIN (Бендер)
                        .requestMatchers(HttpMethod.POST, "/channels").hasRole("ADMIN")

                        // 3. Всё остальное - доступно любому с токеном (USER или ADMIN)
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 3. Провайдер аутентифікації (зв'язує юзерів і шифратор паролів)
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // 4. Шифратор паролів (щоб не зберігати їх текстом)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}