import java.util.ArrayList;

public class Page
{
    // attributes
    private ArrayList<Integer> history;
    private int pageNum;
    private Page next;
    
    // constructor
    public Page() 
    {
        this.pageNum = 0;
        this.history = new ArrayList<Integer>();
    }

    public Page(int p) 
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
    public void setNext(Page n) 
    {
        this.next = n;
    }

    public void setPrevious(Page p) 
    {
    }
    
    // methods
    public boolean inMemory(int compareInt)
    {
        for (Integer integer : history) 
        {
            if(integer == compareInt)
            {
                return true;
            }
        }

        return false;
    }
}