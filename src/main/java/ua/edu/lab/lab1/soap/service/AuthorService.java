package ua.edu.lab.lab1.soap.service;

import jakarta.jws.WebService;
import org.springframework.stereotype.Service;
import ua.edu.lab.lab1.model.Author;
import ua.edu.lab.lab1.model.SourceType;
import ua.edu.lab.lab1.repository.AuthorRepository;
import ua.edu.lab.lab1.soap.contract.AuthorServiceInterface;
import ua.edu.lab.lab1.soap.dto.AuthorCreateRequest;
import java.util.List;

@Service
// Анотація, яка вказує, що це реалізація нашого веб-сервісу
@WebService(
        serviceName = "AuthorService",
        portName = "AuthorServicePort",
        targetNamespace = "http://lab.edu.ua/lab1/soap",
        endpointInterface = "ua.edu.lab.lab1.soap.contract.AuthorServiceInterface"
)
public class AuthorService implements AuthorServiceInterface {

    private final AuthorRepository authorRepository;

    // Spring автоматично "вставить" сюди ваш репозиторій (Dependency Injection)
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author getAuthorById(Long id) {
        // findById повертає Optional, тому ми використовуємо orElse(null)
        return authorRepository.findById(id).orElse(null);
    }

    @Override
    public Author addAuthor(AuthorCreateRequest authorData) {
        Author newAuthor = new Author();

        // Переносимо дані з DTO в нашу модель для збереження в БД
        newAuthor.setExternalId(authorData.getExternalId());
        newAuthor.setDisplayName(authorData.getDisplayName());

        // Для необов'язкових полів ставимо значення за замовчуванням
        newAuthor.setUsername(authorData.getUsername() != null ? authorData.getUsername() : "");
        newAuthor.setAvatarUrl(authorData.getAvatarUrl() != null ? authorData.getAvatarUrl() : "");
        newAuthor.setBot(authorData.getBot() != null ? authorData.getBot() : false);

        // Встановлюємо значення, які клієнт не передає
        newAuthor.setSourceType(SourceType.TELEGRAM); // Припустимо, що сервіс працює тільки з Telegram

        return authorRepository.save(newAuthor);
    }

    @Override
    public boolean deleteAuthor(Long id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
}