package fr.antoine.fraudify.controllers;

import fr.antoine.fraudify.exceptions.NotFoundException;
import fr.antoine.fraudify.exceptions.TrackAlreadyInPlaylistException;
import fr.antoine.fraudify.models.Track;
import fr.antoine.fraudify.models.Playlist;
import fr.antoine.fraudify.models.PlaylistTrack;
import fr.antoine.fraudify.models.User;
import fr.antoine.fraudify.models.id.PlaylistMusicId;
import fr.antoine.fraudify.dto.request.CreatePlaylistRequest;
import fr.antoine.fraudify.dto.response.AddAudioToPlaylistResponse;
import fr.antoine.fraudify.dto.response.CreatePlaylistResponse;
import fr.antoine.fraudify.services.TrackService;
import fr.antoine.fraudify.services.PlaylistAudioFileService;
import fr.antoine.fraudify.services.PlaylistService;
import fr.antoine.fraudify.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/playlist")
@RequiredArgsConstructor
public class PlaylistController {
    private final PlaylistService playlistService;
    private final PlaylistAudioFileService playlistTrackService;
    private final TrackService trackService;
    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<CreatePlaylistResponse> createPlaylist(@RequestBody CreatePlaylistRequest request) {
        User owner = userService.getUserById(request.ownerId());
        return ResponseEntity.ok(CreatePlaylistResponse.builder()
            .createdPlaylist(playlistService.savePlaylist(Playlist.builder()
                .playlistName(request.playlistName())
                .playlistDescription(request.playlistDescription())
                .playlistOwner(owner)
                .isPrivate(request.isPrivate())
                .build())
            )
            .message("Playlist created")
            .build());
    }

    @GetMapping("/{playlistId}")
    public ResponseEntity<Playlist> getPlaylistById(@PathVariable Integer playlistId) throws NotFoundException {
        Playlist playlist = playlistService.getPlaylistById(playlistId);
        if (playlist == null) {
            throw new NotFoundException("Playlist not found");
        }
        return ResponseEntity.ok(playlist);
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<?> getPlaylistsByOwner(@PathVariable Integer ownerId) throws NotFoundException {
        User owner = userService.getUserById(ownerId);
        if (owner == null) {
            throw new NotFoundException("User not found");
        }
        return ResponseEntity.ok(playlistService.getPlaylistByOwner(owner));
    }

    @PostMapping("/{playlistId}/audio/{audioId}")
    public ResponseEntity<AddAudioToPlaylistResponse> addAudioToPlaylist(
            @PathVariable Integer playlistId,
            @PathVariable String audioId
    ) throws NotFoundException, TrackAlreadyInPlaylistException {
        Playlist playlist = playlistService.getPlaylistById(playlistId);
        if (playlist == null) {
            throw new NotFoundException("Playlist not found");
        }

        Track music = trackService.getTrackById(audioId);
        if (music == null) {
            throw new NotFoundException("Audio not found");
        }


        if(playlistTrackService.getPlaylistAudioFileById(PlaylistMusicId
                .builder()
                .playlist(playlistId)
                .track(audioId)
                .build()) != null) {
            throw new TrackAlreadyInPlaylistException("Audio already in playlist");
        }

        return ResponseEntity.ok(AddAudioToPlaylistResponse.builder()
                .addedAudio(music)
                .playlistTrack(playlistTrackService.addAudioToPlaylist(playlist, music))
                .message("Audio added to playlist")
                .build());
    }

    @DeleteMapping("/{playlistId}/audio/{audioId}")
    public ResponseEntity<?> removeAudioFromPlaylist(
            @PathVariable Integer playlistId,
            @PathVariable String audioId
    ) throws NotFoundException {
        Playlist playlist = playlistService.getPlaylistById(playlistId);
        if (playlist == null) {
            throw new NotFoundException("Playlist not found");
        }

        Track track = trackService.getTrackById(audioId);
        if (track == null) {
            throw new NotFoundException("Audio not found");
        }

        PlaylistTrack playlistTrack = playlistTrackService.getPlaylistAudioFileById(PlaylistMusicId
                .builder()
                .playlist(playlistId)
                .track(audioId)
                .build());
        if (playlistTrack == null) {
            throw new NotFoundException("Audio not found in playlist");
        }

        playlistTrackService.removeAudioFromPlaylist(playlistTrack);
        return ResponseEntity.ok("Audio removed from playlist");
    }
}
