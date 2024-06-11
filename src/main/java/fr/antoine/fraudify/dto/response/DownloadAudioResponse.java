package fr.antoine.fraudify.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import fr.antoine.fraudify.dto.TrackDTO;
import lombok.Builder;


@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record DownloadAudioResponse(
        TrackDTO track,
        String message
) {}
