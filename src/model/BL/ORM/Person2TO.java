package model.BL.ORM;

/**
 * Created by Sony on 15/04/2016.
 */
public class Person2TO
{
    private long id;
    private String name;
    private String family;
    private byte[] image;

    public byte[]  getImage() {
        return image;
    }

    public void setImage(byte[]  image) {
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }


}

