package fr.antoine.fraudify.services;

import fr.antoine.fraudify.models.Track;
import fr.antoine.fraudify.models.Playlist;
import fr.antoine.fraudify.models.PlaylistTrack;
import fr.antoine.fraudify.models.id.PlaylistMusicId;
import fr.antoine.fraudify.repository.PlaylistAudioFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PlaylistAudioFileService {
    private final PlaylistAudioFileRepository playlistAudioFileRepository;

    public PlaylistTrack addAudioToPlaylist(Playlist playlist, Track track) {
        try {
            return playlistAudioFileRepository.save(PlaylistTrack.builder()
                    .playlist(playlist)
                    .track(track)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Error while adding audio to playlist");
        }
    }

    public void removeAudioFromPlaylist(PlaylistTrack playlistTrack) {
        try {
            playlistAudioFileRepository.deleteByPlaylistIdAndAudioFileId(
                    playlistTrack.getPlaylist().getPlaylistId(),
                    playlistTrack.getTrack().getTrackId());
        } catch (Exception e) {
            throw new RuntimeException("Error while removing audio from playlist");
        }
    }

    public PlaylistTrack getPlaylistAudioFileById(PlaylistMusicId id) {
        return playlistAudioFileRepository.findById(id).orElse(null);
    }

}
