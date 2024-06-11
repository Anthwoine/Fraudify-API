package fr.antoine.fraudify.services;

import fr.antoine.fraudify.dto.request.CreatePlaylistRequest;
import fr.antoine.fraudify.exceptions.NotFoundException;
import fr.antoine.fraudify.models.Playlist;
import fr.antoine.fraudify.models.User;
import fr.antoine.fraudify.repository.PlaylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;


    public Playlist savePlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    public Playlist createPlaylist(CreatePlaylistRequest createPlaylistRequest, User user) {
        Playlist playlist = Playlist.builder()
                .playlistName(createPlaylistRequest.playlistName())
                .playlistDescription(createPlaylistRequest.playlistDescription())
                .playlistOwner(user)
                .isPrivate(createPlaylistRequest.isPrivate())
                .build();

        return savePlaylist(playlist);
    }

    public Playlist getPlaylistById(Integer playlistId) throws NotFoundException {
        return playlistRepository.findById(playlistId)
                .orElseThrow(() -> new NotFoundException("Playlist not found"));
    }

    public void deletePlaylist(Integer playlistId) {
        playlistRepository.deleteById(playlistId);
    }

    public List<Playlist> getPlaylistByOwner(User owner) {
        return playlistRepository.findByPlaylistOwner(owner).orElse(null);
    }
}
