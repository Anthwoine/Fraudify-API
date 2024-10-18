package fr.antoine.fraudify.repository;

import fr.antoine.fraudify.models.Track;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;

public interface TrackRepository extends JpaRepository<Track, String> {

    Page<Track> findAllByOrderByTitle(PageRequest pageRequest);

    Page<Track> findAllByOrderByArtist(PageRequest pageRequest);

    Page<Track> findByTitleContainingOrderByTitle(String title, PageRequest pageRequest);

    Page<Track> findByArtistContainingOrderByArtist(String artist, PageRequest pageRequest);

}
