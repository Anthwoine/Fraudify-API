package fr.antoine.fraudify.controllers;

import fr.antoine.fraudify.dto.VideoMetadata;
import fr.antoine.fraudify.exceptions.AlreadyExistTrackException;
import fr.antoine.fraudify.exceptions.TrackDownloadException;
import fr.antoine.fraudify.models.Track;
import fr.antoine.fraudify.dto.request.DownloadAudioRequest;
import fr.antoine.fraudify.dto.request.DownloadMetadataRequest;
import fr.antoine.fraudify.dto.response.DownloadAudioResponse;
import fr.antoine.fraudify.dto.response.DownloadMetadataResponse;
import fr.antoine.fraudify.services.TrackService;
import fr.antoine.fraudify.utils.YoutubeDlpUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/youtube-dl")
@RequiredArgsConstructor
public class YoutubeDlController {

    private final YoutubeDlpUtil youtubeDlpUtil;

    private final TrackService trackService;

    @PostMapping("/download-metadata")
    public ResponseEntity<DownloadMetadataResponse> downloadMetadata(@Valid @RequestBody DownloadMetadataRequest request) {
        VideoMetadata metadata = youtubeDlpUtil.downloadVideoMetadata(request.videoUrl());
        return ResponseEntity.ok(DownloadMetadataResponse.builder()
                .metadata(metadata)
                .downloadUrl(request.videoUrl())
                .message("Metadata downloaded")
                .build());
    }

    @PostMapping("/download-audio")
    public ResponseEntity<DownloadAudioResponse> downloadAudio(
            @Valid @RequestBody DownloadAudioRequest request
    ) throws AlreadyExistTrackException, TrackDownloadException {
        VideoMetadata metadata = youtubeDlpUtil.downloadVideoMetadata(request.videoUrl());

        System.out.println(request);
        System.out.println(metadata);

        if(trackService.getTrackById(metadata.id()) != null) {
            throw new AlreadyExistTrackException("Track already exist");
        }

        youtubeDlpUtil.downloadAudio(request.videoUrl(), metadata.id());

        Track music = Track.builder()
            .title(request.audioTitle())
            .artist(request.videoArtist())
            .duration(metadata.duration())
            .trackId(metadata.id())
            .build();

        trackService.saveTrack(music);
        return ResponseEntity.ok(DownloadAudioResponse.builder()
            .music(music)
            .message("Audio downloaded")
            .build());
    }
}
