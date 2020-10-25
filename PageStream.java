import java.util.ArrayList;

public class PageStream 
{
    private ArrayList<Integer> pageID;
    private ArrayList<Integer> timeAdded;

    public PageStream()
    {
        this.pageID = new ArrayList<Integer>();
        this.timeAdded = new ArrayList<Integer>();
    }

    public ArrayList<Integer> getPageIDList()
    {
        return this.pageID;
    }

    public ArrayList<Integer> getTimeAddedList()
    {
        return this.timeAdded;
    }
}
