package ua.edu.lab.lab1.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.lab.lab1.model.PostMedia;
import ua.edu.lab.lab1.repository.PostMediaRepository;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/post-media")
public class PostMediaController {

    private final PostMediaRepository repo;

    public PostMediaController(PostMediaRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<PostMedia> list() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostMedia> get(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PostMedia> create(@RequestBody PostMedia body) {
        PostMedia saved = repo.save(body);
        return ResponseEntity.created(URI.create("/post-media/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostMedia> update(@PathVariable Long id, @RequestBody PostMedia body) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        body.setId(id);
        return ResponseEntity.ok(repo.save(body));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}