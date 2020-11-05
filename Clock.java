// TITLE: 					Assignment3
// COURSE: 					COMP2240
// AUTHOR: 					Moosa Hassan
// STUDENT NUMBER: 			3331532
// DATE: 					08/11/2020 
// DESCRIPTION: 			helper class for clock algorithm - keeps track of page and its current bit data as an integer

// importing java library
import java.util.ArrayList;

public class Clock 
{
    // attributes
    private final ArrayList<Integer> pageID;
    private final ArrayList<Integer> timeAdded;
    private final ArrayList<Integer> bit;

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
