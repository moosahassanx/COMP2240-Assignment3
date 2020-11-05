// TITLE: 					Assignment3
// COURSE: 					COMP2240
// AUTHOR: 					Moosa Hassan
// STUDENT NUMBER: 			3331532
// DATE: 					08/11/2020 
// DESCRIPTION: 			Least Recently Used algorithm class

// importing java libraries
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class LeastRecentlyUsed
{
    // attributes
    private final ArrayList<Process> LRUList;
    private final Queue<Process> readyQueue;            // ready queue
    private final ArrayDeque<Process> bQueue;           // blocked queue
    private final ArrayList<Process> resultingList;     // final list to print

    // constructor
    public LeastRecentlyUsed() 
    {
        new ArrayList<Integer>();
        this.LRUList = new ArrayList<Process>();
        this.readyQueue = new LinkedList<Process>();
        this.bQueue = new ArrayDeque<Process>();
        this.resultingList = new ArrayList<Process>();
    }

    // mutators
    public void setList(final ArrayList<Process> feedList, final int frames) 
    {
        // deep element arraylist cloning
        for (final Process process : feedList) 
        {
            final Process inputProcess = new Process(process.getID(), process.getFileName(), process.getPages(), process.getQuantum());
            inputProcess.setFrames(frames);
            this.LRUList.add(inputProcess);
        }
    }

    // methods
    public void RoundRobin() 
    {
        int cpuWatch = 0;

        // add all processes to blocking queue
        for (final Process process : LRUList) 
        {
            // process is in blocking state
            process.setBlocked(cpuWatch);
            bQueue.add(process);
        }

        Process cProcess = null;

        // business logic
        do 
        {
            // there are processes ready for instructions
            if (readyQueue.size() > 0) 
            {
                cProcess = readyQueue.peek();
                Page cPage = cProcess.grabPage();

                // page replacement had just occurred
                if (cProcess.getPageReplaced() == true) 
                {
                    // issue a block
                    cProcess.addToMemory(cPage, cpuWatch);
                    cProcess.setNeverRun(false);
                    cProcess.setBlocked(cpuWatch);
                    cProcess.setPageReplaced(false);
                    bQueue.add(cProcess);
                    readyQueue.poll();

                    // next process
                    if (readyQueue.size() > 0) 
                    {
                        cProcess = readyQueue.peek();
                        cPage = cProcess.grabPage();

                        // work on it normally
                        cProcess.setNeverRun(false);
                        cProcess.addToMemory(cPage, cpuWatch);
                        cProcess.addToPageStream(cPage, cpuWatch);
                        cProcess.pageOver();
                        cProcess.decrementLifespan();

                        if (cProcess.getLifespan() == 0) 
                        {
                            this.resultingList.add(cProcess);
                            cProcess.setTurnAround(cpuWatch);
                            this.readyQueue.poll();
                        } 
                        else 
                        {
                            cProcess.decrementQuantum();
                            // quantum burst is over
                            if (cProcess.getQuantum() == 0) 
                            {
                                // send to back of ready queue
                                cProcess.resetQuantum();
                                readyQueue.add(readyQueue.poll());
                            }
                        }
                    }
                }
                // page is already in the memory AND page is in page stream
                else if (cProcess.inMemory(cPage) == true || cProcess.inPageStream(cPage) == true || cProcess.getNeverRun() == true) 
                {
                    // work on it normally
                    cProcess.setNeverRun(false);
                    cProcess.addToMemory(cPage, cpuWatch);
                    cProcess.addToPageStream(cPage, cpuWatch);
                    cProcess.pageOver();
                    cProcess.decrementLifespan();

                    if (cProcess.getLifespan() == 0) 
                    {
                        this.resultingList.add(cProcess);
                        cProcess.setTurnAround(cpuWatch + 1);
                        this.readyQueue.poll();
                    } 
                    else 
                    {
                        cProcess.decrementQuantum();

                        // quantum burst is over
                        if (cProcess.getQuantum() == 0) 
                        {
                            // send to back of ready queue
                            cProcess.resetQuantum();
                            readyQueue.add(readyQueue.poll());
                        }
                    }
                }
                // page is not in the memory
                else 
                {
                    // issue a block
                    cProcess.addToMemory(cPage, cpuWatch);
                    cProcess.setNeverRun(false);
                    cProcess.setBlocked(cpuWatch);
                    bQueue.add(cProcess);
                    readyQueue.poll();

                    // next process
                    if (readyQueue.size() > 0) 
                    {
                        cProcess = readyQueue.peek();
                        cPage = cProcess.grabPage();

                        // work on it normally
                        cProcess.setNeverRun(false);
                        cProcess.addToMemory(cPage, cpuWatch);
                        cProcess.addToPageStream(cPage, cpuWatch);
                        cProcess.pageOver();
                        cProcess.decrementLifespan();

                        if (cProcess.getLifespan() == 0) 
                        {
                            this.resultingList.add(cProcess);
                            cProcess.setTurnAround(cpuWatch);
                            this.readyQueue.poll();
                        } 
                        else 
                        {
                            cProcess.decrementQuantum();

                            // quantum burst is over
                            if (cProcess.getQuantum() == 0) 
                            {
                                // send to back of ready queue
                                cProcess.resetQuantum();
                                readyQueue.add(readyQueue.poll());
                            }
                        }

                    }
                }
            }

            cpuWatch++;

            // checking valid ready processes from the blocked queue
            for (int i = 0; i < LRUList.size(); i++) 
            {
                if (bQueue.size() != 0) 
                {
                    if (bQueue.peek().canUnblock(cpuWatch) == true) 
                    {
                        // process is ready
                        this.readyQueue.add(bQueue.poll());
                    }
                }
            }

        } 
        while (this.LRUList.size() != this.resultingList.size());
    }

    public void outputResults() 
    {
        // print the data of the list of processes in order of ID
        Collections.sort(resultingList, new sortByProcessID());
        for (final Process process : resultingList) 
        {
            final String strID = Integer.toString(process.getID());
            final String strProcessName = process.getFileName();
            final String strTurnAround = Integer.toString(process.getTurnAround());
            final String strFaults = Integer.toString(process.getNumFaults());
            final String strFaultList = process.generateFaultList();

            System.out.printf(strID + "    " + strProcessName + "      " + strTurnAround + "              " + strFaults + "       " + strFaultList + "\n");
        }
    }

}

// sorting class in accordance to process ID
class sortByProcessID implements Comparator<Process> 
{
    public int compare(final Process o1, final Process o2) 
    {
        return o1.getID() - o2.getID();
    }
    
}