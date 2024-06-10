package fr.antoine.fraudify.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "history"
)
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
        name = "history_id"
    )
    private Integer historyId;

    @ManyToOne
    @JoinColumn(
        name = "user_id",
        referencedColumnName = "user_id"
    )
    private User user;

    @ManyToOne
    @JoinColumn(
        name = "track_id",
        referencedColumnName = "track_id"
    )
    private Track track;


    @Column(
        name = "listened_at",
        nullable = false,
        updatable = false
    )
    private LocalDateTime listenedAt;


    @PrePersist
    protected void onCreate() {
        listenedAt = LocalDateTime.now();
    }
}
