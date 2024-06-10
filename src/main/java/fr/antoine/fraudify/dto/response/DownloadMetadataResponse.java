package fr.antoine.fraudify.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import fr.antoine.fraudify.dto.VideoMetadata;
import lombok.Builder;
import lombok.Data;



@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record DownloadMetadataResponse (
        VideoMetadata metadata,
        String downloadUrl,
        String message
) {}
