package fr.antoine.fraudify.utils.osstrategie;

import fr.antoine.fraudify.configs.YoutubeDlpConfig;
import fr.antoine.fraudify.dto.VideoMetadata;
import fr.antoine.fraudify.exceptions.TrackDownloadException;

public class LinuxStrategy extends OsStrategy {

    public LinuxStrategy(YoutubeDlpConfig youtubeDlpConfig) {
        super(youtubeDlpConfig);
    }

    public VideoMetadata downloadMetadate(String videoUrl) {
        String youtubeDlPath = getYoutubeDlpConfig().getYoutubeDlPath();
        String metadataFile = getYoutubeDlpConfig().getMetadataFilePath() + "metadata.json";

        String[] command = {
                youtubeDlPath,
                "--dump-single-json",
                "--no-playlist",
                videoUrl
        };

        return processMetadata(command, metadataFile); //comment
    }



    public void downloadAudio(String videoUrl, String title) throws TrackDownloadException {
        String youtubeDlPath = getYoutubeDlpConfig().getYoutubeDlPath();
        String audioFile = getYoutubeDlpConfig().getAudioFilePath() + title + ".m4a";

        String[] command = {
            youtubeDlPath,
            "-f", "bestaudio",
            "--audio-format", "m4a",
            "--no-playlist",
            "-o", audioFile,
            videoUrl
        };

        processAudio(command);
    }
}
