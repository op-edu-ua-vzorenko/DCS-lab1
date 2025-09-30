package ua.edu.lab.lab1.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.lab.lab1.model.Author;
import ua.edu.lab.lab1.repository.AuthorRepository;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorRepository repo;

    public AuthorController(AuthorRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Author> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getById(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Author> create(@RequestBody Author a) {
        Author saved = repo.save(a);
        return ResponseEntity.created(URI.create("/authors/" + saved.getId())).body(saved);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Author> update(@PathVariable Long id, @RequestBody Author patch) {
        return repo.findById(id).map(existing -> {
            if (patch.getDisplayName() != null) existing.setDisplayName(patch.getDisplayName());
            if (patch.getUsername() != null)     existing.setUsername(patch.getUsername());
            if (patch.getAvatarUrl() != null)    existing.setAvatarUrl(patch.getAvatarUrl());
            if (patch.getSourceType() != null)   existing.setSourceType(patch.getSourceType());
            if (patch.getExternalId() != null)   existing.setExternalId(patch.getExternalId());
            if (patch.isBot() != null)        existing.setBot(patch.isBot());
            Author saved = repo.save(existing);
            return ResponseEntity.ok(saved);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}