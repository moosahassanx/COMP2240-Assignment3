import java.util.ArrayList;

public class LRU 
{
    // attributes
    private String name;
    private ArrayList<Process> LRUList;
    
    // constructor
    public LRU(String n)
    {
        this.name = n;
    }

    // accessors
    public String getName() 
    {
        return name;
    }

    // mutators
    public void setName(String name) 
    {
        this.name = name;
    }

    public void setList(ArrayList<Process> r)
    {
        // deep element arraylist cloning
        for(int i = 0; i < r.size(); i++)
        {
            this.LRUList.add(r.get(i));
        }
    }

    // methods
    public void RoundRobin()
    {
        //
    }
    
}
