package fr.antoine.fraudify.dto.response;

import fr.antoine.fraudify.models.User;
import lombok.Builder;

@Builder
public record RegisterResponse(
        User user,
        String accessToken
) {
}
