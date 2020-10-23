import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class ClockAlg
{
    private ArrayList<Process> LRUList;
    private Queue<Process> readyQueue;              // ready queue
    private ArrayDeque<Process> bQueue;             // blocked queue
    private ArrayList<Process> resultingList;       // final list to print

    // constructor
    public ClockAlg() {
        new ArrayList<Integer>();
        this.LRUList = new ArrayList<Process>();
        this.readyQueue = new LinkedList<Process>();
        this.bQueue = new ArrayDeque<Process>();
        this.resultingList = new ArrayList<Process>();
    }

    // accessors

    // mutators
    public void setList(ArrayList<Process> feedList)
    {
        // deep element arraylist cloning
        for (Process process : feedList) 
        {
            Process inputProcess = new Process(process.getID(), process.getFileName(), process.getPages(), process.getQuantum());
            this.LRUList.add(inputProcess);
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

        Process cProcess = null;

        // business logic
        do
        {
            System.out.println("========= TIME: " + cpuWatch + " =========");

            // there are processes ready for instructions
            if(readyQueue.size() > 0)
            {
                cProcess = readyQueue.peek();
                Page cPage = cProcess.grabPage();

                // page is already in the memory
                if(cProcess.inMemory(cPage) == true || cProcess.getNeverRun() == true)
                {
                    // work on it normally
                    cProcess.setNeverRun(false);
                    cProcess.addToMemory(cPage);
                    cProcess.pageOver();
                    cProcess.decrementLifespan();

                    if(cProcess.getLifespan() == 0)
                    {
                        this.resultingList.add(cProcess);
                        cProcess.setTurnAround(cpuWatch + 1);
                        this.readyQueue.poll();

                        System.out.println(cProcess.getFileName() + " has finished on time: " + cpuWatch);
                    }
                    else
                    {
                        cProcess.decrementQuantum();
                        System.out.println(cProcess.getFileName() + " has ran a piece of instruction (" + cProcess.getLifespan() + ")");

                        // quantum burst is over
                        if(cProcess.getQuantum() == 0)
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
                    cProcess.addToMemory(cPage);
                    cProcess.setBlocked(cpuWatch);
                    bQueue.add(cProcess);
                    readyQueue.poll();

                    System.out.println(cProcess.getFileName() + " is now in blocked state.");

                    // next process
                    if(readyQueue.size() > 0)
                    {
                        cProcess = readyQueue.peek();
                        cPage = cProcess.grabPage();

                        // work on it normally
                        cProcess.setNeverRun(false);
                        cProcess.addToMemory(cPage);
                        cProcess.pageOver();
                        cProcess.decrementLifespan();

                        if(cProcess.getLifespan() == 0)
                        {
                            this.resultingList.add(cProcess);
                            cProcess.setTurnAround(cpuWatch);
                            this.readyQueue.poll();

                            System.out.println(cProcess.getFileName() + " has finished on time: " + cpuWatch);
                        }
                        else
                        {
                            cProcess.decrementQuantum();
                            System.out.println(cProcess.getFileName() + " has ran a piece of instruction (" + cProcess.getLifespan() + ")");

                            // quantum burst is over
                            if(cProcess.getQuantum() == 0)
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
            for(int i = 0; i < LRUList.size(); i++)
            {
                if(bQueue.size() != 0)
                {
                    if(bQueue.peek().canUnblock(cpuWatch) == true)
                    {
                        // process is ready
                        this.readyQueue.add(bQueue.poll());
                    }
                }
            }

        } while (this.LRUList.size() != this.resultingList.size());
    }

    public void outputResults()
    {
        Collections.sort(resultingList, new sortByProcessID());
        for (Process process : resultingList) 
        {
            System.out.println(process.getID() + "\t" + process.getFileName() + "\t" + process.getTurnAround() + "\t\t" + process.getNumFaults() + "\t" + process.generateFaultList());
        }
    }
    
}