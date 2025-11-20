package ua.edu.lab.lab1.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.lab.lab1.model.Post;
import ua.edu.lab.lab1.repository.PostRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import ua.edu.lab.lab1.config.RabbitConfig;
import ua.edu.lab.lab1.dto.PostEnrichTask;
import java.util.UUID;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostRepository repo;
    private final RabbitTemplate rabbitTemplate;

    // Обновляем конструктор под RabbitMQ
    public PostController(PostRepository repo, RabbitTemplate rabbitTemplate) {
        this.repo = repo;
        this.rabbitTemplate = rabbitTemplate;
    }

    /** Получить все посты (без сортировки, без пагинации). */
    @GetMapping
    public List<Post> list() {
        return repo.findAll();
    }


    /** Один пост по id. */
    @GetMapping("/{id}")
    public ResponseEntity<Post> get(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /** Создать пост. */
    @PostMapping
    public ResponseEntity<Post> create(@RequestBody Post body) {
        Post saved = repo.save(body);

        // 1. Формируем задачу для RabbitMQ
        String hash = UUID.randomUUID().toString(); // Рандом, как ты хотел
        PostEnrichTask task = new PostEnrichTask(saved.getId(), hash);

        // 2. Отправляем в RabbitMQ
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTING_KEY, task);

        System.out.println(" [Producer] Отправил задачу для Post ID: " + saved.getId() + " (Hash: " + hash + ")");
        return ResponseEntity.created(URI.create("/posts/" + saved.getId())).body(saved);
    }

    /** Обновить пост полностью. */
    @PutMapping("/{id}")
    public ResponseEntity<Post> put(@PathVariable Long id, @RequestBody Post body) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        body.setId(id);
        return ResponseEntity.ok(repo.save(body));
    }

    /** Удалить пост. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}