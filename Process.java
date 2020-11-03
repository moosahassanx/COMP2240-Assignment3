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
    private PageStream pageStreamer;
    private boolean pageReplaced;
    private Clock clockTracker;
    private int clockPtr;

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
        this.cPage = assignFirstPage();
        this.memory = new ArrayList<Page>();
        this.neverRun = true;
        this.originalQuantum = q;
        this.quantum = q;
        this.lifespan = this.pages.size();
        this.pageStreamer = new PageStream();
        this.pageReplaced = false;
        this.clockTracker = new Clock();
        this.clockPtr = 0;
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

    public boolean getPageReplaced()
    {
        return this.pageReplaced;
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

    public void setPageReplaced(boolean p)
    {
        this.pageReplaced = p;
    }

    // methods
    public void addToClock(Page p)
    {
        // empty spaces
        if(this.clockTracker.getPageIDList().size() < frames)
        {
            this.clockTracker.getPageIDList().add(p.getPageNum());      // add page number
            this.clockTracker.getBitList().add(1);                      // bit to 1
            
            // System.out.println("page(" + p.getPageNum() + ") has been added at index: " + this.clockPtr);

            // clock pointer cannot go beyond number of frames
            this.clockPtr++;
            if(this.clockPtr > this.frames - 1)
            {
                this.clockPtr = 0;
            }
        }
        // spaces are all full (replacement strategy begins here)
        else
        {
            // page number exists in clock
            if(this.clockTracker.getPageIDList().contains(p.getPageNum()))
            {
                // dont move the pointer
                // need a way to temporarily index to that page number in the array
            }
            // page number does not exist in clock
            else
            {
                for(int i = this.clockPtr; i < this.clockTracker.getPageIDList().size(); i++)
                {
                    // scan the page stream to find a frame with a 0-bit
                    if(this.clockTracker.getBitList().get(i) == 1)
                    {
                        // each time it finds a frame with a 1-bit, set it to 0 and carry on
                        this.clockTracker.getBitList().set(i, 0);
                    }
                    else
                    {
                        // set the clock pointer value for replacement
                        this.clockPtr = i;
                        break;
                    }
                }

                // replace the page in the clock pointer
                this.clockTracker.getPageIDList().set(this.clockPtr, p.getPageNum());
                this.clockTracker.getBitList().set(this.clockPtr, 1);

                // clock pointer cannot go beyond number of frames
                this.clockPtr++;
                if(this.clockPtr > this.frames - 1)
                {
                    this.clockPtr = 0;
                }

                // the next page id is in the clock so we wouldnt have to restart the original call method
                if(p.getNext() != null)    // null checker
                {
                    if(this.clockTracker.getPageIDList().contains(p.getNext().getPageNum()))
                    {
                        this.pageReplaced = false;
                    }
                    else
                    {
                        this.pageReplaced = true;
                    }
                }
                else
                {
                    this.pageReplaced = false;
                }
                
            }
        }

    }

    public boolean inClock(Page p)
    {
        if(this.clockTracker.getPageIDList().contains(p.getPageNum()))
        {
            // System.out.println(p.getPageNum() + " is in in clock");
            return true;
        }
        else
        {
            // System.out.println(p.getPageNum() + " is NOT in clock");
            return false;
        }
    }

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

    public boolean inPageStream(Page p)
    {
        if(this.pageStreamer.getPageIDList().contains(p.getPageNum()))
        {
            // System.out.println(p.getPageNum() + " is in the page stream.");
            return true;
        }
        else
        {
            // System.out.println(p.getPageNum() + " is NOT in the page stream.");
            return false;
        }
    }

    public boolean inMemory(Page p)
    {
        for (Page page : memory) 
        {
            if(page.getPageNum() == p.getPageNum())
            {
                // System.out.println(p.getPageNum() + " is in the memory.");
                return true;
            }
        }

        // System.out.println(p.getPageNum() + " is NOT in the memory.");
        return false;
    }

    public void addToMemory(Page p, int cpuWatch)
    {
        memory.add(p);
        // System.out.println(p.getPageNum() + " has been added to the memory.");
    }

    public void addToPageStream(Page p, int cpuWatch)
    {
        // add to page stream
        if(this.pageStreamer.getPageIDList().size() < frames)
        {
            this.pageStreamer.getPageIDList().add(p.getPageNum());
            this.pageStreamer.getTimeAddedList().add(cpuWatch);
        }
        // perform page replacement
        else
        {
            // in the page stream
            if(this.pageStreamer.getPageIDList().contains(p.getPageNum()))
            {
                // update the time for existing page
                for(int i = 0; i < pageStreamer.getPageIDList().size(); i++)
                {
                    if(this.pageStreamer.getPageIDList().get(i) == p.getPageNum())
                    {
                        this.pageStreamer.getTimeAddedList().set(i, cpuWatch);
                        break;
                    }
                }
            }
            // not in the page stream
            else
            {
                int indexer = 0;
                for(int i = 1; i < this.pageStreamer.getPageIDList().size(); i++)
                {
                    // grab the index with the smallest cpuWatch time (LRU)
                    if(this.pageStreamer.getTimeAddedList().get(indexer) > this.pageStreamer.getTimeAddedList().get(i))
                    {
                        indexer = i;
                    }
                }

                // updating page streamer
                this.pageStreamer.getPageIDList().set(indexer, p.getPageNum());
                this.pageStreamer.getTimeAddedList().set(indexer, cpuWatch);
                
                // the next page id is in the page streamer so we wouldnt have to restart the original call method
                if(this.pageStreamer.getPageIDList().contains(p.getNext().getPageNum()))
                {
                    this.pageReplaced = false;
                }
                else
                {
                    this.pageReplaced = true;
                }
            }
        }
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
