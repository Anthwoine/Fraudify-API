package fr.antoine.fraudify.dto.request;


public record CreatePlaylistRequest (
        String playlistName,
        String playlistDescription,
        Integer ownerId,
        Boolean isPrivate
) {}
