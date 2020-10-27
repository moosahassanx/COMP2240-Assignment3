import java.util.ArrayList;

public class PageStream 
{
    // attributes
    private ArrayList<Integer> pageID;
    private ArrayList<Integer> timeAdded;

    // constructor
    public PageStream()
    {
        this.pageID = new ArrayList<Integer>();
        this.timeAdded = new ArrayList<Integer>();
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
}
