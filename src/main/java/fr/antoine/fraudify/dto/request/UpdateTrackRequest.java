package fr.antoine.fraudify.dto.request;

import java.time.LocalDateTime;

public record UpdateTrackRequest (
        String trackId,
        String title,
        String artist
) {}
