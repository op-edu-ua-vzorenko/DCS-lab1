package ua.edu.lab.lab1.soap.service;

import jakarta.jws.WebService;
import org.springframework.stereotype.Service;
import ua.edu.lab.lab1.model.Channel;
import ua.edu.lab.lab1.repository.ChannelRepository;
import ua.edu.lab.lab1.soap.contract.ChannelServiceInterface; // <-- Зверніть увагу на новий шлях до інтерфейсу
import ua.edu.lab.lab1.soap.dto.ChannelCreateRequest;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@Service
@WebService(
        serviceName = "ChannelService",
        portName = "ChannelServicePort",
        targetNamespace = "http://lab.edu.ua/lab1/soap",
        // Вказуємо повний шлях до вашого нового інтерфейсу
        endpointInterface = "ua.edu.lab.lab1.soap.contract.ChannelServiceInterface"
)
public class ChannelService implements ChannelServiceInterface { // <-- Реалізуємо новий інтерфейс

    private final ChannelRepository channelRepository;

    public ChannelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Override
    public Channel getChannelById(Long id) {
        return channelRepository.findById(id).orElse(null);
    }

    @Override
    public Channel addChannel(ChannelCreateRequest channelData) {
        Channel newChannel = new Channel();

        // Переносимо дані з DTO в нашу JPA-сутність
        newChannel.setChannelId(channelData.getChannelId());
        newChannel.setType(channelData.getType());
        newChannel.setTitle(channelData.getTitle());

        // Обробка необов'язкових полів
        newChannel.setUsername(channelData.getUsername());
        newChannel.setDescription(channelData.getDescription());
        newChannel.setInviteLink(channelData.getInviteLink());
        newChannel.setPhotoUrl(channelData.getPhotoUrl());
        newChannel.setForum(channelData.getForum() != null ? channelData.getForum() : false);
        newChannel.setHasProtectedContent(channelData.getHasProtectedContent() != null ? channelData.getHasProtectedContent() : false);
        newChannel.setJoinToSendMessages(channelData.getJoinToSendMessages() != null ? channelData.getJoinToSendMessages() : false);

        // Встановлюємо серверне поле
        newChannel.setFetchedAt(LocalDateTime.now());

        return channelRepository.save(newChannel);
    }

    @Override
    public Channel updateChannelDescription(Long id, String newDescription) {
        Optional<Channel> channelOptional = channelRepository.findById(id);

        if (channelOptional.isEmpty()) {
            return null; // Або можна кинути виняток, що канал не знайдено
        }

        Channel existingChannel = channelOptional.get();
        existingChannel.setDescription(newDescription);

        // Зберігаємо оновлений канал і повертаємо його
        return channelRepository.save(existingChannel);
    }

    @Override
    public List<Channel> getAllChannels() {
        return channelRepository.findAll();
    }
}