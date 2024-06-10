package fr.antoine.fraudify.repository;

import fr.antoine.fraudify.models.Track;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<Track, String> {
}
