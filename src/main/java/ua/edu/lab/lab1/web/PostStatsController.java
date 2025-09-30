package ua.edu.lab.lab1.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.lab.lab1.model.PostStats;
import ua.edu.lab.lab1.repository.PostStatsRepository;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/post-stats")
public class PostStatsController {

    private final PostStatsRepository repo;

    public PostStatsController(PostStatsRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<PostStats> list() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostStats> get(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PostStats> create(@RequestBody PostStats body) {
        PostStats saved = repo.save(body);
        return ResponseEntity.created(URI.create("/post-stats/" + saved.getPostId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostStats> update(@PathVariable Long id, @RequestBody PostStats body) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        body.setPostId(id);
        return ResponseEntity.ok(repo.save(body));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}