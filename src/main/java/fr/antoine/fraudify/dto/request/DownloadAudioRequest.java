package fr.antoine.fraudify.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DownloadAudioRequest (
        @NotNull(message = "videoUrl is required")
        @NotBlank(message = "videoUrl cannot be blank")
        String videoUrl,

        @NotNull(message = "audioTitle is required")
        @NotBlank(message = "audioTitle cannot be blank")
        String audioTitle,

        @NotNull(message = "videoArtist is required")
        @NotBlank(message = "videoArtist cannot be blank")
        String videoArtist
){}
