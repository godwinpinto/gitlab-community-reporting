package com.gp.gdd.utils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.gp.gdd.vo.ServiceResponseVO;
import reactor.core.publisher.Mono;

/**
 * REST API Client to fetch data from Gitlab
 */
@Component
public class WebClientWrapper {

    @Autowired
    WebClient customWebClient;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Used to fetch data from gitlab
     * 
     * @param pageNo Requested Page No
     * @param endpoint Generic URL of Gitlab
     * @param additionalUrlParams Additional paramaters to fetch data
     * @return ServiceResponseVO Response POJO to apply business logic for subsequent calls
     * @throws Exception Any exceptions that the API might encounter
     */
    public ServiceResponseVO callGitlabService(int pageNo, String endpoint,
            String additionalUrlParams) throws Exception {
        ServiceResponseVO serviceResponseVO = new ServiceResponseVO();
        try {
            String newEndpoint = endpoint + "?per_page=100&page=" + pageNo
                    + (additionalUrlParams != null && !additionalUrlParams.equals("")
                            ? "&" + additionalUrlParams
                            : "");

            ResponseEntity<JsonNode> response =
                    customWebClient.get().uri(newEndpoint).acceptCharset(StandardCharsets.UTF_8)
                            .retrieve().onStatus(HttpStatus::isError, response2 -> {
                                logger.error("Error in reponse header", response2.statusCode());
                                return Mono.error(new Exception(
                                        "Error in Response" + response2.statusCode()));
                            }).toEntity(JsonNode.class).onErrorMap(Throwable.class,
                                    throwable -> new Exception(throwable.getMessage()))
                            .block();
            HttpHeaders headers = response.getHeaders();
            List<String> headerValues = headers.get("X-Total-Pages");

            serviceResponseVO.setPageNo(pageNo);
            if (headerValues == null || headerValues.isEmpty()) {
                serviceResponseVO.setTotalPages(0);
            } else {
                if (headerValues.get(0).toString() == "") {
                    serviceResponseVO.setTotalPages(0);
                } else {
                    int totalPage = Integer.parseInt(headerValues.get(0));
                    serviceResponseVO.setTotalPages(totalPage);
                }
            }
            serviceResponseVO.setResponseJson(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return serviceResponseVO;
    }
}
