package fr.antoine.fraudify.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.antoine.fraudify.configs.YoutubeDlpConfig;
import fr.antoine.fraudify.dto.VideoMetadata;
import fr.antoine.fraudify.exceptions.MetaDataDownloadException;

import fr.antoine.fraudify.exceptions.TrackDownloadException;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.logging.Level;
import java.util.logging.Logger;


@Data
@RequiredArgsConstructor
@Component
public class YoutubeDlpUtil {
    private static final Logger LOGGER = Logger.getLogger(YoutubeDlpUtil.class.getName());

    private final YoutubeDlpConfig youtubeDlpConfig;

    public VideoMetadata downloadVideoMetadata(String videoUrl) {
        String youtubeDlPath = youtubeDlpConfig.getYoutubeDlPath();
        String metadataFile = youtubeDlpConfig.getMetadataFilePath() + "result.json";

        String[] command = {
                youtubeDlPath,
                "--dump-single-json",
                "--no-playlist",
                videoUrl
        };

        Process process = null;
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectOutput(new File(metadataFile));
            process = processBuilder.start();
            process.waitFor();

            if(process.exitValue() != 0) {
                throw new MetaDataDownloadException("Error downloading video metadata");
            }

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(new File(metadataFile), VideoMetadata.class);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "IOException occurred while downloading video metadata", ex);
            throw new MetaDataDownloadException("Error downloading video metadata",ex);
        } catch (InterruptedException ex) {
            LOGGER.log(Level.SEVERE, "InterruptedException occurred while downloading video metadata", ex);
            throw new MetaDataDownloadException("Error downloading video metadata", ex);
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
    }

    public void downloadAudio(String videoUrl, String title) throws TrackDownloadException {

        String youtubeDlPath = youtubeDlpConfig.getYoutubeDlPath();
        String audioFile = youtubeDlpConfig.getAudioFilePath() + title + ".m4a";

        String[] command = {
                youtubeDlPath,
                "-f", "bestaudio",
                "--extract-audio",
                "--audio-format", "m4a",
                "--no-playlist",
                "-o", audioFile,
                videoUrl
        };

        Process process = null;
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            process = processBuilder.start();

            try (
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))
            ) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains("[download]")) {
                        LOGGER.log(Level.INFO, line);
                    }
                }

                while ((line = errorReader.readLine()) != null) {
                    LOGGER.log(Level.SEVERE, line);
                }
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new TrackDownloadException("Error downloading audio");
            }
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "IOException occurred while downloading audio", ex);
            throw new TrackDownloadException("Error downloading audio", ex);
        } catch (InterruptedException ex) {
            LOGGER.log(Level.SEVERE, "InterruptedException occurred while downloading audio", ex);
            throw new TrackDownloadException("Error downloading audio", ex);
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
    }
}
