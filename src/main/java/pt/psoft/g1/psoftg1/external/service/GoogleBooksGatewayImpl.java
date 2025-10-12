package pt.psoft.g1.psoftg1.external.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Component
public class GoogleBooksGatewayImpl implements BookIsbnGateway {

    private final WebClient webClient;

    public GoogleBooksGatewayImpl(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://www.googleapis.com/books/v1/volumes").build();
    }

    @Override
    public Optional<String> getIsbnByTitle(String title) {
        String response = webClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("q", "intitle:" + title).build())
                .retrieve()
                .bodyToMono(String.class)
                .block();  // em apps reais usa Mono/Flux sem bloquear

        // Parse JSON para extrair ISBN (precisas de uma biblioteca como Jackson ou Gson)
        // Retorna apenas o primeiro ISBN encontrado
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);
            JsonNode items = root.path("items");
            if (items.isArray() && items.size() > 0) {
                JsonNode volumeInfo = items.get(0).path("volumeInfo");
                JsonNode industryIdentifiers = volumeInfo.path("industryIdentifiers");
                for (JsonNode id : industryIdentifiers) {
                    if (id.path("type").asText().equals("ISBN_13")) {
                        return Optional.of(id.path("identifier").asText());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}

