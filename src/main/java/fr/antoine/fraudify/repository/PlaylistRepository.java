package fr.antoine.fraudify.repository;

import fr.antoine.fraudify.models.Playlist;
import fr.antoine.fraudify.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {
    public Optional<List<Playlist>> findByPlaylistOwner(User owner);
}
