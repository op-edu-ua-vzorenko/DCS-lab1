package ua.edu.lab.lab1.dto;

import java.io.Serializable;

public class PostEnrichTask implements Serializable {
    private Long postId;
    private String randomHash; // "учебная" рандомная строка

    public PostEnrichTask() {}

    public PostEnrichTask(Long postId, String randomHash) {
        this.postId = postId;
        this.randomHash = randomHash;
    }

    public Long getPostId() { return postId; }
    public String getRandomHash() { return randomHash; }
}