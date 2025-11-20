package ua.edu.lab.lab1.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ua.edu.lab.lab1.config.RabbitConfig;
import ua.edu.lab.lab1.dto.PostEnrichTask;
import ua.edu.lab.lab1.model.Post;
import ua.edu.lab.lab1.repository.PostRepository;

@Service
public class PostEnricherWorker {

    private final PostRepository repo;

    public PostEnricherWorker(PostRepository repo) {
        this.repo = repo;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void processTask(PostEnrichTask task) {
        System.out.println(" [Consumer] Получил задачу id=" + task.getPostId() + ", hash=" + task.getRandomHash());

        try {
            // Достаем пост
            Post post = repo.findById(task.getPostId()).orElse(null);

            if (post != null) {
                System.out.println(" [Consumer] Начинаю 'тяжелую' обработку (ждем 7 сек)...");
                Thread.sleep(7); // Фриз на 7 секунд

                int length = (post.getText() != null) ? post.getText().length() : 0;

                // Обогащаем и сохраняем
                post.setContentLength(length);
                repo.save(post);

                System.out.println(" [Consumer] ГОТОВО! Пост " + post.getId() + " обновлен. Длина: " + length);
            } else {
                System.err.println(" [Consumer] Ошибка: Пост не найден в БД!");
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}