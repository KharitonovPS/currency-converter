package org.kps.currency.web.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.kps.currency.domain.currency.dto.CurrencyApiDTO;
import org.kps.currency.domain.currency.entity.CurrencyEntity;
import org.kps.currency.domain.currency.mapper.CurrencyApiMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.List;

@Getter
@Setter
@Service
@Slf4j
public class CurrencyClientImpl implements CurrencyClient {

    @Value("${client.properties.url}")
    private String url;

    @Value("${client.properties.key}")
    private String key;

    private final ObjectMapper mapper;

    private final HttpClient client;
    private final CurrencyApiMapper currencyApiMapper;


    public CurrencyClientImpl(CurrencyApiMapper currencyApiMapper) {
        this.currencyApiMapper = currencyApiMapper;
        this.mapper = new ObjectMapper();
        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofMillis(1000))
                .build();
    }

    public List<CurrencyEntity> getDataFromAPI() {
        HttpRequest getRequest = createGetRequest();
        String response = sendRequest(client, getRequest);
        CurrencyApiDTO apiDTO = deserialize(response);
        return currencyApiMapper.currencyDtoToEntity(apiDTO);
    }


    private HttpRequest createGetRequest() {
        try {
            String URI = url + key;
            log.info("Sending request to API -> {}", URI);
            return HttpRequest.newBuilder()
                    .uri(new URI(URI))
                    .GET()
                    .build();
        } catch (URISyntaxException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public CurrencyApiDTO deserialize(String json) {
        try {
            return mapper.readValue(json, CurrencyApiDTO.class);
        } catch (JsonProcessingException exception) {
            log.info("Failed to deserialize response: " +
                            exception.getMessage(),
                    exception);
            throw new RuntimeException();
        }
    }
}