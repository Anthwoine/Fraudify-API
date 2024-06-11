package fr.antoine.fraudify.services;

import fr.antoine.fraudify.dto.TrackDTO;
import fr.antoine.fraudify.dto.request.UpdateTrackRequest;
import fr.antoine.fraudify.exceptions.NotFoundException;
import fr.antoine.fraudify.models.Track;
import fr.antoine.fraudify.repository.TrackRepository;
import fr.antoine.fraudify.utils.Patcher;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackService {

    private final TrackRepository trackRepository;



    public Track saveTrack(Track track) {
        return trackRepository.save(track);
    }

    public boolean trackExists(String trackId) {
        return trackRepository.existsById(trackId);
    }


    public Track updateTrack(UpdateTrackRequest updateTrackRequest) throws NotFoundException, NoSuchFieldException {
        Track track = trackRepository.findById(updateTrackRequest.trackId())
                .orElseThrow(() -> new NotFoundException("Track not found"));

        Patcher.patchObject(track, updateTrackRequest);
        return trackRepository.save(track);
    }

    public Track getTrackById(String trackId) throws NotFoundException {
        return trackRepository.findById(trackId)
                .orElseThrow(() -> new NotFoundException("Track not found"));
    }


    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }

    public void deleteTrack(String trackId) {
        trackRepository.deleteById(trackId);
    }

    public List<Track> searchTracksByTitle(String query, int pageSize, int pageNumber) {
        List<Track> tracks = new ArrayList<>();
        if(query == null) {
            tracks = trackRepository.findAllByOrderByTitle(PageRequest.of(pageNumber, pageSize)).getContent();
        } else {
            tracks = trackRepository.findByTitleContainingOrderByTitle(query, PageRequest.of(pageNumber, pageSize)).getContent();

        }

        return tracks;
    }
}
