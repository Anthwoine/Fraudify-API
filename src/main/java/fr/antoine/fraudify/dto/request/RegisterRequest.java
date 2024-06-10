package fr.antoine.fraudify.dto.request;

public record RegisterRequest(
        String username,
        String password
) {
}
