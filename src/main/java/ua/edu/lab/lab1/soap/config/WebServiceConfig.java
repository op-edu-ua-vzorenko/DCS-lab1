package ua.edu.lab.lab1.soap.config;

import jakarta.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.edu.lab.lab1.soap.service.AuthorService;
import ua.edu.lab.lab1.soap.service.ChannelService;

@Configuration
public class WebServiceConfig {

    private final Bus bus;
    private final AuthorService authorService;
    private final ChannelService channelService;

    // Spring автоматично "вставить" сюди всі необхідні компоненти
    public WebServiceConfig(Bus bus, AuthorService authorService, ChannelService channelService) {
        this.bus = bus;
        this.authorService = authorService;
        this.channelService = channelService;
    }

    // Цей метод створить і опублікує ендпоінт для AuthorService
    @Bean
    public Endpoint authorEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, authorService);
        // Ось саме той рядок, який визначає адресу!
        endpoint.publish("/author");
        return endpoint;
    }

    // А цей - для ChannelService
    @Bean
    public Endpoint channelEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, channelService);
        // І тут ми визначаємо його адресу
        endpoint.publish("/channel");
        return endpoint;
    }
}