package fr.antoine.fraudify.models.id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistMusicId implements Serializable {
    private Integer playlist;
    private String track;
}
