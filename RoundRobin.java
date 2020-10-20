import java.util.ArrayList;

public class RoundRobin 
{
    // attributes
    private ArrayList<Process> RRList;

    // constructor
    public RoundRobin()
    {
        this.RRList = new ArrayList<Process>();
    }

    // accessors

    // mutators
    public void setList(ArrayList<Process> r)
    {
        // deep element arraylist cloning
        for(int i = 0; i < r.size(); i++)
        {
            this.RRList.add(r.get(i));
        }
    }

    // methods
}
