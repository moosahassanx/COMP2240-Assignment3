import java.util.ArrayList;

public class Process {
    // attributes
    private ArrayList<Integer> page;
    private int ID;
    private String fileName;
    private int turnAround;
    private int numFaults;
    ArrayList<Integer> fList;
    private int frames;

    // constructor
    public Process(int i,String f , ArrayList<Integer> p)
    {
        this.ID = i;
        this.fileName = f;
        this.page = p;
        this.turnAround = 0;
        this.numFaults = 0;
        this.fList = new ArrayList<Integer>();
        this.frames = 0;
    }
    
    // acessors
    public int getTurnAround()
    {
        return this.turnAround;
    }

    public int getNumFaults()
    {
        return this.numFaults;
    }

    public int getFrames()
    {
        return this.frames;
    }

    public String generateFaultList()
    {
        String output = "{";
        
        if(fList.size() != 0)
        {
            fList.get(0);

            for(int i = 1; i < fList.size() - 1; i++)
            {
                output += ", " + fList.get(i);
            }
        }

        output += "}";

        return output;
    }

    // mutators
    public void setTurnAround(int t)
    {
        this.turnAround = t;
    }

    public void setNumFaults(int n)
    {
        this.numFaults = n;
    }

    public void setFrames(int f)
    {
        this.frames = f;
    }

    // methods
    public void printData()
    {
        String strTurnaround = String.format("%7d", this.turnAround);
        String strFListSize = String.format("%17d", this.fList.size());
        System.out.println(this.ID + "    " + this.fileName + strTurnaround + strFListSize + "\t\t" + generateFaultList());
    }
}
