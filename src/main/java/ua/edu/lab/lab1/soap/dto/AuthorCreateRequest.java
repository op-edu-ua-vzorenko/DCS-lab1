package ua.edu.lab.lab1.soap.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import ua.edu.lab.lab1.model.SourceType;

// JAXB анотації, щоб Java знала, як перетворити це на XML
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuthorCreateRequest", propOrder = {
        "externalId",
        "displayName",
        "username",
        "avatarUrl",
        "isBot"
})
public class AuthorCreateRequest {

    @XmlElement(required = true)
    protected String externalId; // Оголошуємо, що це поле обов'язкове

    @XmlElement(required = true)
    protected String displayName; // І це теж

    protected String username;
    protected String avatarUrl;
    protected Boolean isBot;

    // Важливо: для JAXB потрібен публічний конструктор без аргументів
    public AuthorCreateRequest() {
    }

    // Getters and Setters для всіх полів...
    // (IntelliJ IDEA може згенерувати їх для вас: Alt+Insert -> Getter and Setter)

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Boolean getBot() {
        return isBot;
    }

    public void setBot(Boolean bot) {
        isBot = bot;
    }
}