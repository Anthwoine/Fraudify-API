package fr.antoine.fraudify.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import fr.antoine.fraudify.models.Playlist;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CreatePlaylistResponse(
        Playlist createdPlaylist,
        String message
) {}
