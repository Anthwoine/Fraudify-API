package fr.antoine.fraudify.filters;


import com.fasterxml.jackson.databind.ObjectMapper;
import fr.antoine.fraudify.dto.response.exceptions.InvalidTokenResponse;
import fr.antoine.fraudify.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger LOGGER  = Logger.getLogger(JwtAuthenticationFilter.class.getName());
    private static final String INVALID_TOKEN_MESSAGE = "invalid_token";
    private static final String NO_TOKEN_MESSAGE = "no_token_found";

    private  final ObjectMapper objectMapper;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        LOGGER.info("Request path : " + request.getServletPath());

        final String header;
        final String jwtToken;
        final String username;
        final UserDetails userDetails;
        boolean isTokenValid;

        if(request.getServletPath().contains("/api/auth") || request.getServletPath().contains("api/hello")) {
            filterChain.doFilter(request, response);
            return;
        }

        header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer ")) {
            LOGGER.warning("No token found");
            setResponseInvalidError(response, request, NO_TOKEN_MESSAGE);
            return;
        }

        jwtToken = header.substring(7);
        username = jwtService.extractUsername(jwtToken);
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            userDetails = userDetailsService.loadUserByUsername(username);
            isTokenValid = jwtService.validateToken(jwtToken, userDetails);
            if(isTokenValid) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                LOGGER.warning("Invalid token");
                setResponseInvalidError(response, request, INVALID_TOKEN_MESSAGE);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }


    private void setResponseInvalidError(HttpServletResponse response, HttpServletRequest request, String message) throws IOException {
        response.setStatus(403);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getOutputStream().print(
                objectMapper.writeValueAsString(
                    new InvalidTokenResponse(
                            request.getServletPath(),
                            message,
                            LocalDateTime.now()
                    )
                )
        );
    }
}
