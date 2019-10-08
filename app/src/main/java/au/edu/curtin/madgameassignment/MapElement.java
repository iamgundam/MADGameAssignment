package au.edu.curtin.madgameassignment;



public class MapElement
{
    private Structure structure;
    private String ownerName;

    public MapElement(Structure structure, String owner)
    {
        this.structure = structure;
        this.ownerName = owner;
    }

    public Structure getStructure()
    {
        return structure;
    }

    public String getOwnerName()
    {
        return ownerName;
    }

    public void setStructure(Structure structure)
    {
        this.structure = structure;
    }

    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }
}
