package fr.antoine.fraudify.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import fr.antoine.fraudify.models.Track;
import fr.antoine.fraudify.models.PlaylistTrack;
import lombok.Builder;


@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AddAudioToPlaylistResponse(
        Track addedAudio,
        PlaylistTrack playlistTrack,
        String message
) {}
