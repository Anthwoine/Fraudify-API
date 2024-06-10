package fr.antoine.fraudify.models;


import jakarta.persistence.*;

@Entity
@Table(
    name = "listen"
)
public class Listen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
        name = "listen_id"
    )
    private Integer listenId;


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
        name = "is_like",
        nullable = false
    )
    private boolean isLike;


    @Column(
        name = "listen_count",
        nullable = false
    )
    private int listenCount;

    @PrePersist
    protected void onCreate() {
        listenCount = 0;
        isLike = false;
    }
}
