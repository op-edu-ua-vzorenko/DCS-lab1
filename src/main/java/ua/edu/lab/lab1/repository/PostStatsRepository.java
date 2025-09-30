package ua.edu.lab.lab1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.lab.lab1.model.PostStats;

public interface PostStatsRepository extends JpaRepository<PostStats, Long> {
}