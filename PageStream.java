// TITLE: 					Assignment3
// COURSE: 					COMP2240
// AUTHOR: 					Moosa Hassan
// STUDENT NUMBER: 			3331532
// DATE: 					08/11/2020 
// DESCRIPTION: 			helper class for LRU algorithm - keeps track of time of page and when it was added

// importing java library
import java.util.ArrayList;

public class PageStream 
{
    // attributes
    private final ArrayList<Integer> pageID;
    private final ArrayList<Integer> timeAdded;

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
