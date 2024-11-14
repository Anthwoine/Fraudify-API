package fr.antoine.fraudify.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record VideoMetadata(
        String id,
        String title,
        int duration,
        String channel,
        String upload_date,
        String thumbnail
) {}

