package fr.antoine.fraudify.controllers;

import fr.antoine.fraudify.dto.response.DownloadMetadataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hello")
@Tag(
        name = "Hello World",
        description = "Hello World"
)
public class HelloWorldController {

    @Operation(
            summary = "Hello World",
            description = "Hello World",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success"
                    )
            }
    )
    @GetMapping
    public ResponseEntity<DownloadMetadataResponse> hello() {
        return ResponseEntity.ok(DownloadMetadataResponse.builder()
                .message("Hello World")
                .build());
    }
}
