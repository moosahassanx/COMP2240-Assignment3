import java.util.ArrayList;

public class Process {
    // attributes
    private ArrayList<Integer> page;

    // constructor
    public Process(ArrayList<Integer> p)
    {
        this.page = p;
    }

    public String getPageArray()        // TODO: TESTING PURPOSES
    {
        String output = "[";

        for (Integer i : page) {
            output += i + " ";
        }

        output += "]";

        return output;
    }
    
    // acessors

    // mutators

    // methods
}
