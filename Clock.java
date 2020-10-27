import java.util.ArrayList;

public class Clock 
{
    // attributes
    private ArrayList<Integer> pageID;
    private ArrayList<Integer> timeAdded;
    private ArrayList<Integer> bit;

    // constructor
    public Clock()
    {
        this.pageID = new ArrayList<Integer>();
        this.timeAdded = new ArrayList<Integer>();
        this.bit = new ArrayList<Integer>();
    }

    // accessors
    public ArrayList<Integer> getPageIDList()
    {
        return this.pageID;
    }

    public ArrayList<Integer> getTimeAddedList()
    {
        return this.timeAdded;
    }

    public ArrayList<Integer> getBitList()
    {
        return this.bit;
    }
}
