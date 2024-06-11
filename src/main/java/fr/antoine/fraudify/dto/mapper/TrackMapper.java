package fr.antoine.fraudify.dto.mapper;

import fr.antoine.fraudify.dto.TrackDTO;
import fr.antoine.fraudify.models.Track;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class TrackMapper  {

    private final ModelMapper modelMapper;


    public TrackDTO trackDTOMapper(Track track) {
        return modelMapper.map(track, TrackDTO.class);
    }

    public Track trackMapper(TrackDTO trackDTO) {
        return modelMapper.map(trackDTO, Track.class);
    }
}
