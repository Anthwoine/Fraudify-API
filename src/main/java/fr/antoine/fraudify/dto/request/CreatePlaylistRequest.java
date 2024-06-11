package fr.antoine.fraudify.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreatePlaylistRequest (
        @NotNull(message = "playlistName is required")
        @NotBlank(message = "playlistName cannot be blank")
        @Size(max=75, message = "playlistName cannot be longer than 255 characters")
        String playlistName,

        @NotNull(message = "playlistDescription is required")
        @Size(max=255, message = "playlistDescription cannot be longer than 1000 characters")
        String playlistDescription,

        @NotNull(message = "isPrivate is required")
        Boolean isPrivate
) {}
