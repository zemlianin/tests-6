package org.example.configurations.client;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.example.configurations.AppSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfiguration {
    private static String ATLAS_BASE_URL;
    private static String ATLAS_AGENT_BASE_URL;
    private static String KEYCLOAK_BASE_URL;
    public static int TIMEOUT;

    public static int LONG_TIMEOUT_MILLIS = 300000;

    @Autowired
    public WebClientConfiguration(AppSettings appSettings) {
        ATLAS_BASE_URL = appSettings.atlasUrl;
        ATLAS_AGENT_BASE_URL = appSettings.atlasAgentUrl;
        KEYCLOAK_BASE_URL = appSettings.keycloakUrl;
        TIMEOUT = appSettings.timeout;
    }

    @Bean
    @Qualifier("atlasClient")
    public WebClient atlasClientWithTimeout() {
        final var tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT)
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(LONG_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(LONG_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS));
                });

        return buildFromTcpClient(tcpClient, ATLAS_BASE_URL);
    }

    @Bean
    @Qualifier("atlasAgentClient")
    public WebClient atlasAgentClientWithTimeout() {
        final var tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT)
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS));
                });

        return buildFromTcpClient(tcpClient, ATLAS_AGENT_BASE_URL);
    }

    @Bean
    @Qualifier("atlasAgentRestartClient")
    public WebClient atlasAgentRestartClientWithTimeout() {
        final var tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT)
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(LONG_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(LONG_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS));
                });
        return buildFromTcpClient(tcpClient, ATLAS_AGENT_BASE_URL);
    }

    @Bean
    @Qualifier("keycloakClient")
    public WebClient keycloakClientWithTimeout() {
        final var tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT)
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS));
                });

        return buildFromTcpClient(tcpClient, KEYCLOAK_BASE_URL);
    }

    private WebClient buildFromTcpClient(TcpClient tcpClient, String baseUrl) {
        final int size = 16 * 1024 * 1024;
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
                .build();
        return WebClient.builder()
                .exchangeStrategies(strategies)
                .baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
    }
}