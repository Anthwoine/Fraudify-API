package fr.antoine.fraudify.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistDTO {
    private Integer playlistId;
    private String playlistName;
    private String playlistDescription;
    private Boolean isPrivate;
}
