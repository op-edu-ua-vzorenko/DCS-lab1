package ua.edu.lab.lab1.soap.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChannelCreateRequest", propOrder = {
        "channelId",
        "type",
        "title",
        "username",
        "description",
        "inviteLink",
        "photoUrl",
        "isForum",
        "hasProtectedContent",
        "joinToSendMessages"
})
public class ChannelCreateRequest {

    @XmlElement(required = true)
    protected Long channelId; // ID з Telegram

    @XmlElement(required = true)
    protected String type;

    @XmlElement(required = true)
    protected String title;

    protected String username;
    protected String description;
    protected String inviteLink;
    protected String photoUrl;
    protected Boolean isForum;
    protected Boolean hasProtectedContent;
    protected Boolean joinToSendMessages;

    public ChannelCreateRequest() {
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
}