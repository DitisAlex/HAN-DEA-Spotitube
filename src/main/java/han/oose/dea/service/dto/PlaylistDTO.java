package han.oose.dea.service.dto;

import han.oose.dea.domain.Track;

import java.util.List;

public class PlaylistDTO {
    public int id;
    public String name;
    public boolean owner;
    public List<Track> tracks;
}
