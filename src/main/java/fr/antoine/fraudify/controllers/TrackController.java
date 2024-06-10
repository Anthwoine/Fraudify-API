package fr.antoine.fraudify.controllers;

import fr.antoine.fraudify.exceptions.NotFoundException;
import fr.antoine.fraudify.models.Track;
import fr.antoine.fraudify.services.TrackService;

import fr.antoine.fraudify.utils.Patcher;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/track")
public class TrackController {
    private final TrackService trackService;



    @GetMapping("/")
    public ResponseEntity<List<Track>> getAllTracks() {
        return ResponseEntity.ok(trackService.getAllTracks());
    }

    @GetMapping("/{trackId}")
    public ResponseEntity<Track> getTrackById(@PathVariable String trackId) {
        return ResponseEntity.ok(trackService.getTrackById(trackId));
    }

    @PutMapping("/{trackId}")
    public ResponseEntity<Track> updateTrack(@PathVariable String trackId, @RequestBody Track track) {
        track.setTrackId(trackId);
        trackService.saveTrack(track);
        return ResponseEntity.ok(track);
    }

    @PatchMapping("/{trackId}")
    public ResponseEntity<Track> partialUpdateTrack(
            @PathVariable String trackId,
            @RequestBody Track track
    ) throws NotFoundException, NoSuchFieldException {
        Track existingTrack = trackService.getTrackById("trackId");
        if (existingTrack == null) {
            throw new NotFoundException("Track not found");
        }

        Patcher.patchObject(existingTrack, track);
        trackService.saveTrack(existingTrack);
        return ResponseEntity.ok(existingTrack);
    }

    @DeleteMapping("/{trackId}")
    public ResponseEntity<String> deleteTrack(@PathVariable String trackId) {
        trackService.deleteTrack(trackId);
        return ResponseEntity.ok("Track deleted");
    }

}
