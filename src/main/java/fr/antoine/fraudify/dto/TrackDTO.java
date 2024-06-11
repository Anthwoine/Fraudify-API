package fr.antoine.fraudify.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrackDTO {
        private String trackId;
        private String title;
        private String artist;
        private int duration;
        private LocalDateTime createdAt;
}
