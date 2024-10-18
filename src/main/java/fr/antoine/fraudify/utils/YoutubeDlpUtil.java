package fr.antoine.fraudify.utils;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.antoine.fraudify.configs.YoutubeDlpConfig;
import fr.antoine.fraudify.dto.VideoMetadata;

import fr.antoine.fraudify.exceptions.TrackDownloadException;

import fr.antoine.fraudify.utils.osstrategie.LinuxStrategy;
import fr.antoine.fraudify.utils.osstrategie.OsStrategy;
import fr.antoine.fraudify.utils.osstrategie.WindowsStrategy;
import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static org.springframework.data.jpa.repository.query.QueryUtils.getQueryString;


@Data
@Service
public class YoutubeDlpUtil {
    private static final Logger LOGGER = Logger.getLogger(YoutubeDlpUtil.class.getName());

    private final YoutubeDlpConfig youtubeDlpConfig;

    private OsStrategy osStrategy;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public YoutubeDlpUtil(YoutubeDlpConfig youtubeDlpConfig) {
        this.youtubeDlpConfig = youtubeDlpConfig;
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            System.out.println("Vous êtes sous Windows.");
            osStrategy = new WindowsStrategy(this.youtubeDlpConfig);
        } else if (os.contains("nux")) {
            System.out.println("Vous êtes sous Linux.");
            osStrategy = new LinuxStrategy(this.youtubeDlpConfig);
        } else {
            System.out.println("Système d'exploitation non supporté");
            throw new RuntimeException("Système d'exploitation non supporté");
        }
    }

    public VideoMetadata downloadVideoMetadata(String videoUrl) throws URISyntaxException, JsonProcessingException {
        VideoMetadata metadata = osStrategy.downloadMetadate(videoUrl);
        downloadAudioImage(metadata.title(), metadata.channel(), false);
        return metadata;
    }

    public void downloadVideoAudio(String videoUrl, String title) throws TrackDownloadException {
        osStrategy.downloadAudio(videoUrl, title);
    }

    public record Payload(
            String track,
            String artist,
            String method,
            String api_key,
            String format
    ) {

    }

    public void downloadAudioImage(String title, String artist, boolean download) throws URISyntaxException, JsonProcessingException {
        String url = "http://ws.audioscrobbler.com/2.0/";
        String apiKey = "03ba2d7bf27a0b9b9c11b8a6d767c4ef";

        Map<String, String> params = new HashMap<>();
        params.put("method", "track.getInfo");
        params.put("artist", artist);
        params.put("track", title);
        params.put("api_key", apiKey);
        params.put("format", "json");

        URI uri = new URI(url + "?" + getQueryString(params));

        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        String data = response.getBody();

        System.out.println("data: " + data);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonData = objectMapper.readTree(data);

        if (jsonData.has("track") && jsonData.get("track").has("album")) {
            JsonNode images = jsonData.get("track").get("album").get("image");
            String img = images.size() > 0 ? images.get(images.size() - 1).get("#text").asText() : null;

            if (img != null && download) {
                System.out.println("image trouvée download...");
                System.out.println(img);
                //  downloadImage(img, title, artist); // Implemente cette méthode selon tes besoins
            } else if (img != null) {
                System.out.println("image trouvée");
                System.out.println(img);

            } else {
                System.out.println("image non trouvée");
            }
        } else {
            System.out.println("image non trouvée");
        }
    }

    private String getQueryString(Map<String, String> params) {
        StringBuilder queryString = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (!queryString.isEmpty()) {
                queryString.append("&");
            }
            try {
                // Encode les clés et les valeurs des paramètres
                String encodedKey = URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString());
                String encodedValue = URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString());
                queryString.append(encodedKey).append("=").append(encodedValue);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("Erreur d'encodage des paramètres de la requête", e);
            }
        }
        return queryString.toString();
    }
}
