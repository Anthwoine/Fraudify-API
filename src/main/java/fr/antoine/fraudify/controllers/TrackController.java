package fr.antoine.fraudify.controllers;

import fr.antoine.fraudify.dto.TrackDTO;
import fr.antoine.fraudify.dto.mapper.TrackMapper;
import fr.antoine.fraudify.dto.request.UpdateTrackRequest;
import fr.antoine.fraudify.exceptions.NotFoundException;
import fr.antoine.fraudify.models.Track;
import fr.antoine.fraudify.services.TrackService;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/track")
public class TrackController {
    private final TrackService trackService;
    private final TrackMapper trackMapper;


    @GetMapping("/")
    public ResponseEntity<List<Track>> getAllTracks() {
        return ResponseEntity.ok(trackService.getAllTracks());
    }

    @GetMapping("/{trackId}")
    public ResponseEntity<TrackDTO> getTrackById(
            @NotNull @NotBlank @PathVariable String trackId
    ) throws NotFoundException {
        Track track = trackService.getTrackById(trackId);
        return ResponseEntity.ok(trackMapper.trackDTOMapper(track));
    }

    @PutMapping("/")
    public ResponseEntity<TrackDTO> updateTrack(
            @RequestBody UpdateTrackRequest updateTrackRequest
    ) throws NotFoundException, NoSuchFieldException {
        Track updatedTrack = trackService.updateTrack(updateTrackRequest);
        return ResponseEntity.ok(trackMapper.trackDTOMapper(updatedTrack));
    }

    @PatchMapping("/")
    public ResponseEntity<TrackDTO> partialUpdateTrack(
            @RequestBody UpdateTrackRequest updateTrackRequest
    ) throws NotFoundException, NoSuchFieldException {
        Track patchedTrack = trackService.updateTrack(updateTrackRequest);
        return ResponseEntity.ok(trackMapper.trackDTOMapper(patchedTrack));
    }

    @DeleteMapping("/{trackId}")
    public ResponseEntity<String> deleteTrack(
            @NotNull @NotBlank @PathVariable String trackId
    ) {
        trackService.deleteTrack(trackId);
        return ResponseEntity.ok("Track deleted");
    }

    @GetMapping("/search-title")
    public ResponseEntity<List<TrackDTO>> searchTrack(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue= "0") int pageNumber
    ) {
        List<Track> tracks = trackService.searchTracksByTitle(query, pageSize, pageNumber);
        return ResponseEntity.ok(tracks.stream()
                .map(trackMapper::trackDTOMapper)
                .collect(java.util.stream.Collectors.toList())
        );
    }


}
