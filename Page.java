// TITLE: 					Assignment3
// COURSE: 					COMP2240
// AUTHOR: 					Moosa Hassan
// STUDENT NUMBER: 			3331532
// DATE: 					08/11/2020 
// DESCRIPTION: 			helper class for process class - used to create an arraylist of pages depending on user input

// importing java library
import java.util.ArrayList;

public class Page
{
    // attributes
    private final ArrayList<Integer> history;
    private final int pageNum;
    private Page next;

    // constructor
    public Page() 
    {
        this.pageNum = 0;
        this.history = new ArrayList<Integer>();
    }

    public Page(final int p) 
    {
        this.pageNum = p;
        this.history = new ArrayList<Integer>();
    }

    // accessor
    public Page getNext() 
    {
        return this.next;
    }

    public Page getPrevious() 
    {
        return this.getPrevious();
    }

    public int getPageNum() 
    {
        return this.pageNum;
    }

    // mutator
    public void setNext(final Page n) 
    {
        this.next = n;
    }

    public void setPrevious(final Page p) 
    {
    }

    // methods
    public boolean inMemory(final int compareInt) 
    {
        for (final Integer integer : history) 
        {
            if(integer == compareInt)
            {
                return true;
            }
        }

        return false;
    }
}