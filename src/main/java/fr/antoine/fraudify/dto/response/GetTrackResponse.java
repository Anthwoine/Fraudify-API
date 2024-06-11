package fr.antoine.fraudify.dto.response;

import fr.antoine.fraudify.dto.TrackDTO;
import lombok.Builder;

@Builder
public record GetTrackResponse (
        TrackDTO track
) {

}
