package fr.antoine.fraudify.dto.response;

import fr.antoine.fraudify.dto.PlaylistDTO;
import fr.antoine.fraudify.dto.PlaylistTrackDTO;
import lombok.Builder;

import java.util.List;

@Builder
public record PlaylistTracksReponse (
       PlaylistDTO playlist,
       List<PlaylistTrackDTO> playlistTracks
){
}
