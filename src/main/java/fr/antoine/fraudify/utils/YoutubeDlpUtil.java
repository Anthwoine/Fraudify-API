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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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

    public VideoMetadata downloadVideoMetadata(String videoUrl) throws URISyntaxException, IOException {
        VideoMetadata metadata = osStrategy.downloadMetadate(videoUrl);
        String image = downloadAudioImage(metadata.title(), metadata.channel(), true, metadata.id());
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

    public String downloadAudioImage(String title, String artist, boolean download, String id) throws URISyntaxException, IOException {
        String url = "http://ws.audioscrobbler.com/2.0/";
        String apiKey = "03ba2d7bf27a0b9b9c11b8a6d767c4ef";

        artist = "Bring Me The Horizon";
        title = "Top 10 staTues tHat CriEd bloOd";

        Map<String, String> params = new HashMap<>();
        params.put("method", "track.getInfo");
        params.put("artist", artist);
        params.put("track", title);
        params.put("api_key", apiKey);
        params.put("format", "json");

        String query = url + "?" + getQueryString(params);
        System.out.println("query: " + query);

        URI uri = new URI(query);

        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        String data = response.getBody();

        System.out.println("data: " + data);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonData = objectMapper.readTree(data);

        if (jsonData.has("track") && jsonData.get("track").has("album")) {
            JsonNode images = jsonData.get("track").get("album").get("image");
            String img = !images.isEmpty() ? images.get(images.size() - 1).get("#text").asText() : null;

            if (img != null && download) {
                System.out.println("image trouvée download...");
                System.out.println(img);
                downloadImage(img, id); // Implemente cette méthode selon tes besoins
                return img;
            } else if (img != null) {
                System.out.println("image trouvée");
                System.out.println(img);
                return img;
            } else {
                System.out.println("image non trouvée");
                return null;
            }
        } else {
            System.out.println("image non trouvée");
            return null;
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

    public void downloadImage(String imageUrl, String id) throws IOException {
        // On spécifie le dossier où on veut enregistrer l'image
        String outputFolder = "E:\\youtube-dl\\images\\";

        // Déduire l'extension du fichier à partir de l'URL (si possible)
        String extension = imageUrl.substring(imageUrl.lastIndexOf(".") + 1);
        if (!extension.equals("png") && !extension.equals("jpg") && !extension.equals("jpeg")) {
            extension = "png"; // Par défaut, on force le format PNG si l'extension n'est pas reconnue
        }

        // Nom complet du fichier de sortie
        String fileName = outputFolder + id + "." + extension;

        // Ouvre une connexion à l'URL
        URL url = new URL(imageUrl);
        try (InputStream in = url.openStream();
             FileOutputStream out = new FileOutputStream(fileName)) {

            // Télécharge et écrit le contenu de l'image dans le fichier
            byte[] buffer = new byte[4096];
            int n;
            while ((n = in.read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }
            System.out.println("Image téléchargée avec succès : " + fileName);
        } catch (IOException e) {
            System.err.println("Erreur lors du téléchargement de l'image : " + e.getMessage());
            throw e;
        }
    }
}
