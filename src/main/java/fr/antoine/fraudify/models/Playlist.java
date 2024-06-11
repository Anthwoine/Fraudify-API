package fr.antoine.fraudify.models;

import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "playlists")
public class Playlist {

    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    @Column(
        name = "playlist_id"
    )
    private Integer playlistId;


    @Column(
        name = "playlist_name",
        nullable = false
    )
    private String playlistName;


    @ManyToOne
    @JoinColumn(
        name = "owner_id",
        referencedColumnName="user_id"
    )
    private User playlistOwner;


    @MapsId("PlaylistTrackId")
    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Column(
        name = "tracks"
    )
    private List<PlaylistTrack> tracks;


    @Column(
        name = "private",
        nullable = false
    )
    private Boolean isPrivate;


    @Column(
        name = "playlist_description"
    )
    private String playlistDescription;

    @Column(
        name = "created_at",
        nullable = false,
        updatable = false
    )
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
