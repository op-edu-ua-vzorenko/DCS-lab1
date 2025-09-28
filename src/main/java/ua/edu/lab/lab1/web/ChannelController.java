package ua.edu.lab.lab1.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.lab.lab1.model.Channel;
import ua.edu.lab.lab1.repository.ChannelRepository;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/channels")
public class ChannelController {

    private final ChannelRepository repo;

    public ChannelController(ChannelRepository repo) {
        this.repo = repo;
    }

    // список всех каналов
    @GetMapping
    public List<Channel> getAll() {
        return repo.findAll();
    }

    // получить по id
    @GetMapping("/{id}")
    public ResponseEntity<Channel> getById(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // создать
    @PostMapping
    public ResponseEntity<Channel> create(@RequestBody Channel channel) {
        Channel saved = repo.save(channel);
        return ResponseEntity.created(URI.create("/channels/" + saved.getId())).body(saved);
    }

    // частичное обновление (любые поля, что пришли != null)
    @PatchMapping("/{id}")
    public ResponseEntity<Channel> update(@PathVariable Long id, @RequestBody Channel patch) {
        return repo.findById(id).map(existing -> {
            if (patch.getName() != null) existing.setName(patch.getName());
            if (patch.getDescription() != null) existing.setDescription(patch.getDescription());
            Channel saved = repo.save(existing);
            return ResponseEntity.ok(saved);
        }).orElse(ResponseEntity.notFound().build());
    }

    // удалить
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}