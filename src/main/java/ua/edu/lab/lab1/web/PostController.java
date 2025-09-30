package ua.edu.lab.lab1.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.lab.lab1.model.Post;
import ua.edu.lab.lab1.repository.PostRepository;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostRepository repo;

    public PostController(PostRepository repo) {
        this.repo = repo;
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