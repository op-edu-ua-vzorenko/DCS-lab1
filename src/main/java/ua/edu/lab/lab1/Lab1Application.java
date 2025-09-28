package ua.edu.lab.lab1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ua.edu.lab.lab1.model.Channel;
import ua.edu.lab.lab1.repository.ChannelRepository;

@SpringBootApplication
public class Lab1Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab1Application.class, args);
    }

    // этот бин выполнится при старте приложения
    @Bean
    CommandLineRunner initDatabase(ChannelRepository repository) {
        return args -> {
            Channel c1 = new Channel();
            c1.setName("Tech Digest");
            c1.setDescription("Щоденний дайджест про ІТ");
            repository.save(c1);

            Channel c2 = new Channel();
            c2.setName("News UA");
            c2.setDescription("Головні новини України");
            repository.save(c2);

            Channel c3 = new Channel();
            c3.setName("Music Box");
            c3.setDescription("Плейлісти та нові треки");
            repository.save(c3);
        };
    }
}
