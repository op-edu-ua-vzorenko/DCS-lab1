package ua.edu.lab.lab1.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "posts",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_post_channel_external", columnNames = {"channel_id","external_id","source_type"})
        },
        indexes = {
                @Index(name = "ix_post_channel_posted_at", columnList = "channel_id,posted_at"),
                @Index(name = "ix_post_author", columnList = "author_id")
        })
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Канал, к которому относится пост */
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "channel_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_post_channel"))
    private Channel channel;

    @Column(name = "external_id", nullable = false, length = 128)
    private String externalId;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_type", nullable = false, length = 32)
    private SourceType sourceType = SourceType.TELEGRAM;

    /** Автор поста (может быть null для системных сообщений) */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", foreignKey = @ForeignKey(name = "fk_post_author"))
    private Author author;

    @Column(name = "posted_at", nullable = false)
    private Instant postedAt = Instant.now();

    @Lob
    @Column(name = "text")
    private String text;

    /** Ссылка на пост, на который был сделан ответ */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_to_post_id",
            foreignKey = @ForeignKey(name = "fk_post_reply_to"))
    private Post replyTo;

    @Column(name = "is_pinned", nullable = false)
    private Boolean pinned;

    @Column(name = "is_edited", nullable = false)
    private Boolean edited;

    @Column(name = "edited_at")
    private Instant editedAt;

    @Column(name = "permalink", length = 1024)
    private String permalink;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    @Column(name = "content_length")
    private Integer contentLength;

    @PreUpdate
    void touch() { this.updatedAt = Instant.now(); }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public SourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(SourceType sourceType) {
        this.sourceType = sourceType;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Instant getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(Instant postedAt) {
        this.postedAt = postedAt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Post getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(Post replyTo) {
        this.replyTo = replyTo;
    }

    public Boolean isPinned() {
        return pinned;
    }

    public void setPinned(Boolean pinned) {
        this.pinned = pinned;
    }

    public Boolean isEdited() {
        return edited;
    }

    public void setEdited(Boolean edited) {
        this.edited = edited;
    }

    public Instant getEditedAt() {
        return editedAt;
    }

    public void setEditedAt(Instant editedAt) {
        this.editedAt = editedAt;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getContentLength() { return contentLength; }

    public void setContentLength(Integer contentLength) { this.contentLength = contentLength; }
}
