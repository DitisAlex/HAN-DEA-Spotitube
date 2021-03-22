package han.oose.dea.domain;

public class Playlist {
    private int id;
    private String name;
    private String owner;
    private int length;

    public int getId(){return id;}

    public void setId(int id){this.id = id;}

    public String getName(){return name;}

    public void setName(String name){this.name = name;}

    public String getOwner(){return owner;}

    public void setOwner(String owner){this.owner = owner;}

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
