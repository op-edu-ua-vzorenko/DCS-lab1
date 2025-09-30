package ua.edu.lab.lab1.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "channels")
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // локальный ID в нашей БД

    @Column(name = "channel_id", unique = true)
    private Long channelId; // ID канала из Telegram
    private String type;         // тип ("channel", "supergroup" и т.д.)
    private String title;        // название канала
    private String username;     // @username канала (если есть)
    @Column(length = 2000)       // описание может быть длинным
    private String description;
    private String inviteLink;   // приглашение
    private String photoUrl;     // ссылка на фото (если сохраним)
    private Boolean isForum;     // является ли форумом
    private Boolean hasProtectedContent; // защита от пересылки
    private Boolean joinToSendMessages;  // нужно ли вступить для сообщений

    private LocalDateTime fetchedAt;     // когда мы сохранили данные

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInviteLink() {
        return inviteLink;
    }

    public void setInviteLink(String inviteLink) {
        this.inviteLink = inviteLink;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Boolean getForum() {
        return isForum;
    }

    public void setForum(Boolean forum) {
        isForum = forum;
    }

    public Boolean getHasProtectedContent() {
        return hasProtectedContent;
    }

    public void setHasProtectedContent(Boolean hasProtectedContent) {
        this.hasProtectedContent = hasProtectedContent;
    }

    public Boolean getJoinToSendMessages() {
        return joinToSendMessages;
    }

    public void setJoinToSendMessages(Boolean joinToSendMessages) {
        this.joinToSendMessages = joinToSendMessages;
    }

    public LocalDateTime getFetchedAt() {
        return fetchedAt;
    }

    public void setFetchedAt(LocalDateTime fetchedAt) {
        this.fetchedAt = fetchedAt;
    }
}