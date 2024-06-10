package fr.antoine.fraudify.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import fr.antoine.fraudify.models.Track;
import lombok.Builder;


@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record DownloadAudioResponse(
        Track music,
        String message
) {}
