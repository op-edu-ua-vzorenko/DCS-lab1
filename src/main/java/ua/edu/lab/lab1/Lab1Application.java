package ua.edu.lab.lab1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ua.edu.lab.lab1.model.Channel;
import ua.edu.lab.lab1.repository.ChannelRepository;

import java.time.LocalDateTime;

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
            c1.setChannelId(1001L);
            c1.setType("channel");
            c1.setTitle("Tech Digest");
            c1.setUsername("techdigest");
            c1.setDescription("Щоденний дайджест про ІТ");
            c1.setInviteLink("https://t.me/techdigest");
            c1.setPhotoUrl("https://example.com/photo1.jpg");
            c1.setIsForum(false);
            c1.setHasProtectedContent(false);
            c1.setJoinToSendMessages(false);
            c1.setFetchedAt(LocalDateTime.now());
            repository.save(c1);

            Channel c2 = new Channel();
            c2.setChannelId(1002L);
            c2.setType("channel");
            c2.setTitle("News UA");
            c2.setUsername("newsua");
            c2.setDescription("Головні новини України");
            c2.setInviteLink("https://t.me/newsua");
            c2.setPhotoUrl("https://example.com/photo2.jpg");
            c2.setIsForum(false);
            c2.setHasProtectedContent(true);
            c2.setJoinToSendMessages(false);
            c2.setFetchedAt(LocalDateTime.now());
            repository.save(c2);

            Channel c3 = new Channel();
            c3.setChannelId(1003L);
            c3.setType("channel");
            c3.setTitle("Music Box");
            c3.setUsername("musicbox");
            c3.setDescription("Плейлісти та нові треки");
            c3.setInviteLink("https://t.me/musicbox");
            c3.setPhotoUrl("https://example.com/photo3.jpg");
            c3.setIsForum(false);
            c3.setHasProtectedContent(false);
            c3.setJoinToSendMessages(true);
            c3.setFetchedAt(LocalDateTime.now());
            repository.save(c3);
        };
    }
}