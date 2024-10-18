package fr.antoine.fraudify.utils.osstrategie;

import fr.antoine.fraudify.configs.YoutubeDlpConfig;
import fr.antoine.fraudify.dto.VideoMetadata;
import fr.antoine.fraudify.exceptions.TrackDownloadException;

public class LinuxStrategy extends OsStrategy {

    public LinuxStrategy(YoutubeDlpConfig youtubeDlpConfig) {
        super(youtubeDlpConfig);
    }

    public VideoMetadata downloadMetadate(String videoUrl) {
        String userHome = System.getProperty("user.home");
        String metadataFile = userHome + "/Documents/fraudify/metadata/metadata.json";

        String[] command = {
                "yt-dlp",
                "--dump-single-json",
                "--no-playlist",
                videoUrl
        };

        return processMetadata(command, metadataFile);
    }



    public void downloadAudio(String videoUrl, String title) throws TrackDownloadException {
        String userHome = System.getProperty("user.home");
        String audioFile = userHome + "/Documents/fraudify/audio/" + title + ".m4a";

        String[] command = {
            "yt-dlp",
            "-f", "bestaudio",
            "--audio-format", "m4a",
            "--no-playlist",
            "-o", audioFile,
            videoUrl
        };

        processAudio(command);
    }
}
