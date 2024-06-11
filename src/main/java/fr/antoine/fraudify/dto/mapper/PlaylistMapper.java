package fr.antoine.fraudify.dto.mapper;

import fr.antoine.fraudify.dto.PlaylistDTO;
import fr.antoine.fraudify.dto.PlaylistTrackDTO;
import fr.antoine.fraudify.models.Playlist;
import fr.antoine.fraudify.models.PlaylistTrack;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaylistMapper {

    private final ModelMapper modelMapper;

    public PlaylistDTO playlistDTOMapper(Playlist playlist) {
        return modelMapper.map(playlist, PlaylistDTO.class);
    }

    public Playlist playlistMapper(PlaylistDTO playlistDTO) {
        return modelMapper.map(playlistDTO, Playlist.class);
    }

    public PlaylistTrackDTO playlistTrackDTOMapper(PlaylistTrack playlistTrack) {
        return modelMapper.map(playlistTrack, PlaylistTrackDTO.class);
    }
}
