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

    public WebServiceConfig(Bus bus, AuthorService authorService, ChannelService channelService) {
        this.bus = bus;
        this.authorService = authorService;
        this.channelService = channelService;
    }

    @Bean
    public Endpoint authorEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, authorService);
        endpoint.publish("/author");
        return endpoint;
    }

    @Bean
    public Endpoint channelEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, channelService);
        endpoint.publish("/channel");
        return endpoint;
    }
}