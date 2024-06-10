package fr.antoine.fraudify.services;

import fr.antoine.fraudify.models.Track;
import fr.antoine.fraudify.repository.TrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackService {
    private final TrackRepository trackRepository;

    public void saveTrack(Track track) {
        trackRepository.save(track);
    }

    public Track getTrackById(String trackId) {
        return trackRepository.findById(trackId)
                .orElse(null);
    }

    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }

    public void deleteTrack(String trackId) {
        trackRepository.deleteById(trackId);
    }
}
