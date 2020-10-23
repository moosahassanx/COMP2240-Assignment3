import java.util.ArrayList;

public class Process 
{
    // attributes
    private ArrayList<Page> pages;
    private ArrayList<Page> memory;
    private int ID;
    private String fileName;
    private int turnAround;
    ArrayList<Integer> faultTimeSheet;
    private int frames;
    private int timeToUnblock;
    Page cPage;
    private boolean neverRun;
    private int quantum;
    private int originalQuantum;
    private int lifespan;

    // constructor
    public Process(int i, String f, ArrayList<Page> p, int q) 
    {
        this.ID = i;
        this.fileName = f;
        this.pages = p;
        this.turnAround = 0;
        this.faultTimeSheet = new ArrayList<Integer>();
        this.frames = 0;
        this.timeToUnblock = 0;
        cPage = assignFirstPage();
        memory = new ArrayList<Page>();
        this.neverRun = true;
        this.originalQuantum = q;
        this.quantum = q;
        this.lifespan = this.pages.size();
        System.out.println(this.fileName + " has lifespan: " + this.lifespan);
    }

    // acessors
    public String getFileName() 
    {
        return this.fileName;
    }

    public int getTurnAround() 
    {
        return this.turnAround;
    }

    public int getNumFaults() 
    {
        return this.faultTimeSheet.size();
    }

    public int getFrames() 
    {
        return this.frames;
    }

    public int getID() 
    {
        return this.ID;
    }

    public ArrayList<Page> getPages() 
    {
        return this.pages;
    }

    public int getQuantum() 
    {
        return this.quantum;
    }

    public int getLifespan() 
    {
        return this.lifespan;
    }

    public String generateFaultList() 
    {
        String output = "{";

        if (faultTimeSheet.size() != 0) 
        {
            output += faultTimeSheet.get(0);

            for (int i = 1; i < faultTimeSheet.size(); i++) 
            {
                output += ", " + faultTimeSheet.get(i);
            }
        }

        output += "}";

        return output;
    }

    public boolean getNeverRun() 
    {
        return this.neverRun;
    }

    // mutators
    public void decrementLifespan() 
    {
        this.lifespan--;
    }

    public void setTurnAround(int t) 
    {
        this.turnAround = t;
    }

    public void setNumFaults(int n) 
    {
    }

    public void setFrames(int f)
    {
        this.frames = f;
    }

    public void setBlocked(int cpuWatch)
    {
        this.faultTimeSheet.add(cpuWatch);
        timeToUnblock = cpuWatch + 6;
    }

    public void setNeverRun(boolean n)
    {
        this.neverRun = n;
    }

    // methods
    public void faultTimeStamp(int faultTime)
    {
        this.faultTimeSheet.add(faultTime);
    }

    public void decrementQuantum()
    {
        this.quantum--;
    }

    public void resetQuantum()
    {
        this.quantum = this.originalQuantum;
    }

    public boolean inMemory(Page p)
    {
        for (Page page : memory) 
        {
            if(page.getPageNum() == p.getPageNum())
            {
                return true;
            }
        }

        return false;
    }

    public void addToMemory(Page p)
    {
        memory.add(p);
    }

    public void pageOver()
    {
        if(this.cPage.getNext() != null)
        {
            this.cPage = this.cPage.getNext();
        }
    }

    public Page assignFirstPage()
    {
        Page tempPage = pages.get(0);

        // setNext() linking
        for(int i = 0; i < pages.size() - 1; i++)
        {
            pages.get(i).setNext(pages.get(i+1));
        }
        
        // setPrevious() linking
        for(int i = pages.size() - 1; i > 0; i--)
        {
            pages.get(i).setPrevious(pages.get(i-1));
        }

        return tempPage;
    }


    public Page grabPage()
    {
        return this.cPage;
    }

    public Page grabNextPage()
    {
        return this.cPage.getNext();
    }

    public boolean canUnblock(int cpuWatch)
    {
        if(cpuWatch == this.timeToUnblock)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void printData()
    {
        String strTurnaround = String.format("%7d", this.turnAround);
        String strFListSize = String.format("%17d", this.faultTimeSheet.size());
        System.out.println(this.ID + "    " + this.fileName + strTurnaround + strFListSize + "\t\t" + generateFaultList());
    }

    public void printer()
    {
        System.out.println(this.fileName + " " + this.frames);
    }
}
