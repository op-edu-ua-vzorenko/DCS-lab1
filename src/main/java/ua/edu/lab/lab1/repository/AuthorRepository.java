package ua.edu.lab.lab1.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.lab.lab1.model.Author;
import ua.edu.lab.lab1.model.SourceType;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByExternalIdAndSourceType(String externalId, SourceType sourceType);
}