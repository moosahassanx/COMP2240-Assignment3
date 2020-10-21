import java.util.ArrayList;

public class Page
{
    // attributes
    private ArrayList<Integer> history;

    // constructor
    public Page()
    {
        this.history = new ArrayList<Integer>();
    }

    // accessor
    

    // mutator
    
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