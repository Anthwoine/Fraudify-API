package fr.antoine.fraudify.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.antoine.fraudify.models.id.PlaylistMusicId;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@IdClass(PlaylistMusicId.class)
@Table(name = "playlists_musics")
public class PlaylistTrack implements Serializable {

    @Id
    @ManyToOne
    @JsonIgnore
    @JoinColumn(
        name = "playlist_id",
        referencedColumnName = "playlist_id"
    )
    private Playlist playlist;

    @Id
    @ManyToOne
    @JoinColumn(
        name = "track_id",
        referencedColumnName = "track_id"
    )
    private Track track;

    @Column(
        name = "added_at",
        nullable = false,
        updatable = false
    )
    private LocalDateTime addedAt;

    @PrePersist
    protected void onCreate() {
        addedAt = LocalDateTime.now();
    }
}
