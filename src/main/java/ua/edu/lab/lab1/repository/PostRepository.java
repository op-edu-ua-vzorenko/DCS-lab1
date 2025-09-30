package ua.edu.lab.lab1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.lab.lab1.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}