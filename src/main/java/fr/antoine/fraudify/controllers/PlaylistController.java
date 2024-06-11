package fr.antoine.fraudify.controllers;

import fr.antoine.fraudify.dto.PlaylistDTO;
import fr.antoine.fraudify.dto.mapper.PlaylistMapper;
import fr.antoine.fraudify.dto.response.PlaylistTracksReponse;
import fr.antoine.fraudify.exceptions.NotFoundException;
import fr.antoine.fraudify.exceptions.TrackAlreadyInPlaylistException;
import fr.antoine.fraudify.models.Track;
import fr.antoine.fraudify.models.Playlist;
import fr.antoine.fraudify.models.PlaylistTrack;
import fr.antoine.fraudify.models.User;
import fr.antoine.fraudify.dto.request.CreatePlaylistRequest;
import fr.antoine.fraudify.services.TrackService;
import fr.antoine.fraudify.services.PlaylistAudioFileService;
import fr.antoine.fraudify.services.PlaylistService;
import fr.antoine.fraudify.services.UserService;
import fr.antoine.fraudify.utils.AuthenticatedUser;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/playlist")
@RequiredArgsConstructor
public class PlaylistController {
    private final PlaylistService playlistService;
    private final PlaylistAudioFileService playlistTrackService;
    private final TrackService trackService;
    private final UserService userService;
    private final PlaylistMapper playlistMapper;
    private final AuthenticatedUser authenticatedUser;

    @PostMapping("/")
    public ResponseEntity<PlaylistDTO> createPlaylist(
            @Valid @RequestBody CreatePlaylistRequest request
    ) {
        User user = authenticatedUser.getAuthenticatedUser();
        Playlist createdPlaylist = playlistService.createPlaylist(request, user);
        return ResponseEntity.ok(playlistMapper.playlistDTOMapper(createdPlaylist));
    }

    @GetMapping("/{playlistId}")
    public ResponseEntity<PlaylistDTO> getPlaylistById(
            @NotNull @PathVariable Integer playlistId
    ) throws NotFoundException {
        Playlist playlist = playlistService.getPlaylistById(playlistId);
        return ResponseEntity.ok(playlistMapper.playlistDTOMapper(playlist));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PlaylistDTO>> getPlaylistsByUser(
            @NotNull @PathVariable Integer userId
    ) {
        User user = userService.getUserById(userId);
        List<Playlist> playlists = playlistService.getPlaylistByOwner(user);
        return ResponseEntity.ok(playlists.stream()
                .map(playlistMapper::playlistDTOMapper)
                .collect(Collectors.toList())
        );
    }

    @PostMapping("/{playlistId}/audio/{trackId}")
    public ResponseEntity<String> addAudioToPlaylist(
            @NotNull @PathVariable Integer playlistId,
            @NotNull @NotBlank @PathVariable String trackId
    ) throws NotFoundException {
        Playlist playlist = playlistService.getPlaylistById(playlistId);
        Track track = trackService.getTrackById(trackId);
        playlistTrackService.addAudioToPlaylist(playlist, track);
        return ResponseEntity.ok("Audio added to playlist");
    }

    @DeleteMapping("/{playlistId}/audio/{trackId}")
    public ResponseEntity<String> removeAudioFromPlaylist(
            @NotNull @PathVariable Integer playlistId,
            @NotNull @NotBlank @PathVariable String trackId
    ) throws NotFoundException {
        Playlist playlist = playlistService.getPlaylistById(playlistId);
        Track track = trackService.getTrackById(trackId);
        PlaylistTrack playlistTrack = playlistTrackService.getPlaylistAudioFileById(trackId, playlistId);
        playlistTrackService.removeAudioFromPlaylist(playlistTrack);
        return ResponseEntity.ok("Audio removed from playlist");
    }

    @DeleteMapping("/{playlistId}")
    public ResponseEntity<String> deletePlaylist(
            @NotNull @PathVariable Integer playlistId
    ) throws NotFoundException {
        playlistService.getPlaylistById(playlistId);
        playlistService.deletePlaylist(playlistId);
        return ResponseEntity.ok("Playlist deleted");
    }

    @GetMapping("/{playlistId}/tracks")
    public ResponseEntity<PlaylistTracksReponse> getPlaylistTracks(
            @NotNull @PathVariable Integer playlistId
    ) throws NotFoundException {
        Playlist playlist = playlistService.getPlaylistById(playlistId);
        List<PlaylistTrack> tracks = playlist.getTracks();
        return ResponseEntity.ok(PlaylistTracksReponse.builder()
                .playlist(playlistMapper.playlistDTOMapper(playlist))
                .playlistTracks(tracks.stream()
                        .map(playlistMapper::playlistTrackDTOMapper)
                        .collect(Collectors.toList())
                )
                .build()
        );
    }
}
