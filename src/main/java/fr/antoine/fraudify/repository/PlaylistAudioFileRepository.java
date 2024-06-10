package fr.antoine.fraudify.repository;

import fr.antoine.fraudify.models.PlaylistTrack;
import fr.antoine.fraudify.models.id.PlaylistMusicId;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlaylistAudioFileRepository extends JpaRepository<PlaylistTrack, PlaylistMusicId> {
    @Transactional
    @Modifying
    @Query("DELETE FROM PlaylistTrack pt WHERE pt.playlist.playlistId = :playlistId AND pt.track.trackId = :trackId")
    void deleteByPlaylistIdAndAudioFileId(@Param("playlistId") Integer playlistId, @Param("trackId") String trackId);
}
