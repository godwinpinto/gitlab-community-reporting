package com.gp.gdd.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

/**
 * Class to add webclient configuration at global level
 */
@Configuration
public class GitlabWebClientConfig {

    /**
     * Fetch the base url of gitlab from properties or environment
     */
    @Value("${gitlab.url.base}")
    private String gitlabBaseUrl;

    /**
     * Fetch the auth token of gitlab from properties or environment
     */
    @Value("${gitlab.token}")
    private String gitlabToken;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    
    /** 
     * Adds predefined fields to web api across application 
     * @param webClientBuilder
     * @return WebClient
     */
    @Bean
    public WebClient customWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl(gitlabBaseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + gitlabToken)
                .defaultHeader(HttpHeaders.CONTENT_ENCODING, "gzip").filter(logResponse())
                .filter(logRequest()).build();
    }

    
    /** 
     * Generic logger to Log input of all API calls
     * @return ExchangeFilterFunction
     */
    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            logger.debug("Gitlab Server Method and URL: {} {}", clientRequest.method(), clientRequest.url());
            return Mono.just(clientRequest);
        });
    }

    /** 
     * Generic logger to Log output of all API calls
     * @return ExchangeFilterFunction
     */
    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            logger.debug("Gitlab Server HTTP status code: {}", clientResponse.statusCode());
            return Mono.just(clientResponse);
        });
    }

}
