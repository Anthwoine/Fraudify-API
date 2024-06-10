package fr.antoine.fraudify.services;

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

    public Playlist getPlaylistById(Integer playlistId) {
        return playlistRepository.findById(playlistId).orElse(null);
    }

    public List<Playlist> getPlaylistByOwner(User owner) {
        return playlistRepository.findByPlaylistOwner(owner).orElse(null);
    }
}
