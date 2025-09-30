package ua.edu.lab.lab1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.lab.lab1.model.PostMedia;

import java.util.List;

public interface PostMediaRepository extends JpaRepository<PostMedia, Long> {
    List<PostMedia> findByPostId(Long postId);
}
