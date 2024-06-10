package fr.antoine.fraudify.dto.response;

import lombok.Builder;

@Builder
public record LoginResponse(
    Integer id,
    String username,
    String token
) {}