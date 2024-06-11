package fr.antoine.fraudify.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistTrackDTO {
        private Integer playlistId;
        private Integer trackId;

}
