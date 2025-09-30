package ua.edu.lab.lab1.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "post_stats")
public class PostStats {
    @Id
    @Column(name = "post_id")
    private Long postId; // PK = FK

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id",
            foreignKey = @ForeignKey(name = "fk_stats_post"))
    private Post post;

    @Column(name = "views")
    private Long views;

    @Column(name = "forwards")
    private Long forwards;

    @Column(name = "replies")
    private Long replies;

    @Column(name = "reactions_total")
    private Long reactionsTotal;

    @Column(name = "last_updated_at", nullable = false)
    private Instant lastUpdatedAt = Instant.now();

    public void touch() { this.lastUpdatedAt = Instant.now(); }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public Long getForwards() {
        return forwards;
    }

    public void setForwards(Long forwards) {
        this.forwards = forwards;
    }

    public Long getReplies() {
        return replies;
    }

    public void setReplies(Long replies) {
        this.replies = replies;
    }

    public Long getReactionsTotal() {
        return reactionsTotal;
    }

    public void setReactionsTotal(Long reactionsTotal) {
        this.reactionsTotal = reactionsTotal;
    }

    public Instant getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(Instant lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }
}