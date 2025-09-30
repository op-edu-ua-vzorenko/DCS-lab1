package ua.edu.lab.lab1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ua.edu.lab.lab1.model.Author;
import ua.edu.lab.lab1.model.Channel;
import ua.edu.lab.lab1.model.Post;
import ua.edu.lab.lab1.model.SourceType;
import ua.edu.lab.lab1.repository.AuthorRepository;
import ua.edu.lab.lab1.repository.ChannelRepository;
import ua.edu.lab.lab1.repository.PostRepository;

import java.time.Instant;
import java.time.LocalDateTime;

@SpringBootApplication
public class Lab1Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab1Application.class, args);
    }

    // этот бин выполнится при старте приложения
    @Bean
    CommandLineRunner initData(AuthorRepository authorRepo,
                               ChannelRepository channelRepo,
                               PostRepository postRepo) {
        return args -> {
            // ===== Authors =====
            Author a1 = new Author();
            a1.setExternalId("u12345");
            a1.setSourceType(SourceType.TELEGRAM);
            a1.setDisplayName("Alice Example");
            a1.setUsername("alice_ex");
            a1.setAvatarUrl("https://example.com/alice.jpg");
            a1.setBot(false);
            authorRepo.save(a1);

            Author a2 = new Author();
            a2.setExternalId("u67890");
            a2.setSourceType(SourceType.TELEGRAM);
            a2.setDisplayName("Bob Test");
            a2.setUsername("bob_test");
            a2.setAvatarUrl("https://example.com/bob.png");
            a2.setBot(false);
            authorRepo.save(a2);

            // ===== Channels =====
            Channel c1 = new Channel();
            c1.setChannelId(10001L);
            c1.setType("channel");
            c1.setTitle("Tech News UA");
            c1.setUsername("technewsua");
            c1.setDescription("Канал с IT новостями");
            c1.setInviteLink("https://t.me/technewsua");
            c1.setPhotoUrl("https://example.com/channel1.png");
            c1.setForum(false);
            c1.setHasProtectedContent(false);
            c1.setJoinToSendMessages(false);
            c1.setFetchedAt(LocalDateTime.now());
            channelRepo.save(c1);

            Channel c2 = new Channel();
            c2.setChannelId(10002L);
            c2.setType("supergroup");
            c2.setTitle("Developers Chat");
            c2.setUsername("devchat");
            c2.setDescription("Чат для программистов");
            c2.setInviteLink("https://t.me/devchat");
            c2.setPhotoUrl("https://example.com/channel2.png");
            c2.setForum(true);
            c2.setHasProtectedContent(false);
            c2.setJoinToSendMessages(true);
            c2.setFetchedAt(LocalDateTime.now());
            channelRepo.save(c2);

            // ===== Posts =====
            Post p1 = new Post();
            p1.setChannel(c1);
            p1.setExternalId("p1001");
            p1.setSourceType(SourceType.TELEGRAM);
            p1.setAuthor(a1);
            p1.setPostedAt(Instant.now().minusSeconds(3600));
            p1.setText("Сегодня вышел новый релиз Java 25!");
            p1.setPinned(false);
            p1.setEdited(false);
            p1.setPermalink("https://t.me/technewsua/1001");
            postRepo.save(p1);

            Post p2 = new Post();
            p2.setChannel(c1);
            p2.setExternalId("p1002");
            p2.setSourceType(SourceType.TELEGRAM);
            p2.setAuthor(a2);
            p2.setPostedAt(Instant.now().minusSeconds(1800));
            p2.setText("Oracle анонсировала новые возможности JDK.");
            p2.setPinned(false);
            p2.setEdited(false);
            p2.setPermalink("https://t.me/technewsua/1002");
            postRepo.save(p2);

            Post p3 = new Post();
            p3.setChannel(c2);
            p3.setExternalId("p2001");
            p3.setSourceType(SourceType.TELEGRAM);
            p3.setAuthor(a1);
            p3.setPostedAt(Instant.now().minusSeconds(600));
            p3.setText("Привет всем! Кто пробовал Spring Boot 3.3?");
            p3.setPinned(false);
            p3.setEdited(false);
            p3.setPermalink("https://t.me/devchat/2001");
            postRepo.save(p3);
        };
    }
}