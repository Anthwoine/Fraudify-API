package fr.antoine.fraudify.dto.request;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DownloadMetadataRequest (
        @NotNull(message = "videoUrl is required")
        @NotBlank(message = "videoUrl cannot be blank")
        String videoUrl
) {}
