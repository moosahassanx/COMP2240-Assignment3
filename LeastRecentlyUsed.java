import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class LeastRecentlyUsed
{
    // attributes
    private ArrayList<Process> LRUList;
    private int blockingTime;
    private Queue<Process> readyQueue;                // ready queue
    private ArrayDeque<Process> bQueue;            // blocked queue
    private ArrayList<Process> resultingList;         // final list to print
    
    // constructor
    public LeastRecentlyUsed()
    {
        this.LRUList = new ArrayList<Process>();
        this.blockingTime = 6;
        this.readyQueue = new LinkedList<Process>();
        this.bQueue = new ArrayDeque<Process>();
        this.resultingList = new ArrayList<Process>();
    }

    // accessors

    // mutators
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
        for (Process p : LRUList)
        {
            p.printer();
        }

        int cpuWatch = 0;

        // add all processes to blocking queue
        for (Process process : LRUList)
        {
            // process is in blocking state
            process.setBlocked(cpuWatch);
            bQueue.add(process);
        }

        // business logic
        while(this.LRUList.size() != this.resultingList.size())
        {
            // checking unblocked processes
            for (Process process : bQueue)
            {
                if(bQueue.peek().canUnblock(cpuWatch) == true)
                {
                    System.out.println(bQueue.peek().getFileName() + ": is can be unblocked");

                    // process is ready
                    bQueue.peek().setReady();
                    System.out.println(bQueue.peek().getFileName() + ": is in ready state");
                    this.readyQueue.add(bQueue.poll());
                }
            }

            for (Process process : readyQueue) 
            {
                // 
            }

            cpuWatch++;
        }
    }

    public void outputResults()
    {
        for (Process p : LRUList)
        {
            p.printData();
        }
    }
    
}
