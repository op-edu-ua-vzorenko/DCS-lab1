package ua.edu.lab.lab1.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.lab.lab1.model.Channel;
import ua.edu.lab.lab1.repository.ChannelRepository;

import java.net.URI;
import java.time.LocalDateTime;
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

    // получить по id (локальному ID в нашей БД)
    @GetMapping("/{id}")
    public ResponseEntity<Channel> getById(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // создать
    @PostMapping
    public ResponseEntity<Channel> create(@RequestBody Channel channel) {
        channel.setFetchedAt(LocalDateTime.now()); // зафиксируем момент добавления
        Channel saved = repo.save(channel);
        return ResponseEntity.created(URI.create("/channels/" + saved.getId())).body(saved);
    }

    // частичное обновление (любые поля, что != null)
    @PatchMapping("/{id}")
    public ResponseEntity<Channel> update(@PathVariable Long id, @RequestBody Channel patch) {
        return repo.findById(id).map(existing -> {
            if (patch.getChannelId() != null) existing.setChannelId(patch.getChannelId());
            if (patch.getType() != null) existing.setType(patch.getType());
            if (patch.getTitle() != null) existing.setTitle(patch.getTitle());
            if (patch.getUsername() != null) existing.setUsername(patch.getUsername());
            if (patch.getDescription() != null) existing.setDescription(patch.getDescription());
            if (patch.getInviteLink() != null) existing.setInviteLink(patch.getInviteLink());
            if (patch.getPhotoUrl() != null) existing.setPhotoUrl(patch.getPhotoUrl());
            if (patch.getIsForum() != null) existing.setIsForum(patch.getIsForum());
            if (patch.getHasProtectedContent() != null) existing.setHasProtectedContent(patch.getHasProtectedContent());
            if (patch.getJoinToSendMessages() != null) existing.setJoinToSendMessages(patch.getJoinToSendMessages());
            existing.setFetchedAt(LocalDateTime.now()); // обновляем метку времени
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