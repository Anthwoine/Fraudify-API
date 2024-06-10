package fr.antoine.fraudify.models;

import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "tracks")
public class Track {

    @Id
    @Column(
        name = "track_id",
        nullable = false,
        unique = true,
        length = 50
    )
    private String trackId;


    @Column(
        name = "title",
        nullable = false,
        length = 100
    )
    private String title;


    @Column(
        name = "artist",
        nullable = false,
        length = 50
    )
    private String artist;


    @Column(
        name = "duration",
        nullable = false
    )
    private int duration;


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
