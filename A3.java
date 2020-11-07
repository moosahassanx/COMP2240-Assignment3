// TITLE: 					Assignment3
// COURSE: 					COMP2240
// AUTHOR: 					Moosa Hassan
// STUDENT NUMBER: 			3331532
// DATE: 					08/11/2020 
// DESCRIPTION: 			Main file - reads data text files and sends each information to its relative classes

// importing java libraries
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class A3 {
    public static void main(final String args[]) throws FileNotFoundException {
        // initialising some input fields from user
        int numFrames = 0;
        int quantumSize = 0;
        final ArrayList<Process> pList = new ArrayList<Process>();
        int processId = 1;

        // getting data from user
        for (int i = 0; i < args.length; i++) {
            // first value will be number of frames
            if (i == 0) {
                numFrames = Integer.parseInt(args[i]);
            }
            // second value will be quantum size
            else if (i == 1) {
                quantumSize = Integer.parseInt(args[i]);
            }
            // other arguments are text file names
            else {
                // fetching data from file
                final Scanner file = new Scanner(new File(args[i]));
                final String fileName = args[i];

                try {
                    final ArrayList<Page> pages = new ArrayList<Page>();
                    String newText = "";

                    // reading the file
                    while (file.hasNext()) {
                        newText = file.nextLine();

                        // file has more information
                        if (!newText.equals("end")) {
                            if (!newText.equals("begin")) {
                                // managing and assigning data
                                final Page pageNum = new Page(Integer.parseInt(newText));
                                pages.add(pageNum);
                            }
                        }
                    }

                    // create object with data and add to list
                    final Process process = new Process(processId, fileName, pages, quantumSize);
                    pList.add(process);
                    processId++;
                } catch (final Exception e) {
                    System.out.println("ERROR: " + e);
                }

                file.close();
            }
        }

        // creating algorithms
        final LeastRecentlyUsed LRU = new LeastRecentlyUsed();
        final ClockAlg clockObj = new ClockAlg();

        // allocated frames for each process
        final int dividedFrames = numFrames / pList.size();
        for (final Process p : pList) {
            p.setFrames(dividedFrames);
        }

        // feeding process lists and allocating frames into each algorithm
        LRU.setList(pList, dividedFrames);
        clockObj.setList(pList, dividedFrames);

        // running algorithms
        LRU.RoundRobin();
        clockObj.RoundRobin();

        // output
        System.out.println("LRU - Fixed:");
        System.out.println("PID  Process Name      Turnaround Time  #Faults  Fault Times");
        LRU.outputResults();

        System.out.println();
        System.out.println("------------------------------------------------------------ \n");

        System.out.println("Clock - Fixed:");
        System.out.println("PID  Process Name      Turnaround Time  #Faults  Fault Times");
        clockObj.outputResults();
    }
}