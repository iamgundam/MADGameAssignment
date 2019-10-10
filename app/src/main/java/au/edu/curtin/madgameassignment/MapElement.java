package au.edu.curtin.madgameassignment;


import android.graphics.Bitmap;

public class MapElement
{
    private Structure structure;
    private String ownerName;
    private Bitmap customImage;

    public MapElement(Structure structure, String owner)
    {
        this.structure = structure;
        this.ownerName = owner;
        this.customImage = null;
    }

    public Structure getStructure()
    {
        return structure;
    }

    public String getOwnerName()
    {
        return ownerName;
    }

    public Bitmap getCustomImage()
    {
        return customImage;
    }

    public void setStructure(Structure structure)
    {
        this.structure = structure;
    }

    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }

    public void setCustomImage(Bitmap customImage)
    {
        this.customImage = customImage;
    }
}
