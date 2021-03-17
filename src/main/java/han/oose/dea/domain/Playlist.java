package han.oose.dea.domain;

import java.util.List;

public class Playlist {
    private int id;
    private String name;
    private String owner;
    private List<Track> tracks;
    private int length;

    public int getId(){return id;}

    public void setId(int id){this.id = id;}

    public String getName(){return name;}

    public void setName(String name){this.name = name;}

    public String getOwner(){return owner;}

    public void setOwner(String owner){this.owner = owner;}

    public List<Track> getTracks(){return tracks;}

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
