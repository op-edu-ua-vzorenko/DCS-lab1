package ua.edu.lab.lab1.config;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// опционально: включать только в dev
// import org.springframework.context.annotation.Profile;

import java.sql.SQLException;

@Configuration
// @Profile("dev") // <- раскомментируй, если хочешь включать только с профилем dev
public class H2ServerConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2TcpServer() throws SQLException {
        // поднимет TCP-сервер H2 на порту 9092
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
    }
}