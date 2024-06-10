package fr.antoine.fraudify.dto.request;

public record LoginRequest(
        String username,
        String password
) {}