import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class mysimulator {
    public static void main(String args[]) throws FileNotFoundException
    {
        int numFrames = 0;
        int quantumSize = 0;
        ArrayList<Process> pList = new ArrayList<Process>();

        // getting data from user
        for(int i = 0; i < args.length; i++)
        {
            // first value will be number of frames
            if(i == 0)
            {
                numFrames = Integer.parseInt(args[i]);
                System.out.println("number of frames: " + numFrames);   // TODO: TESTING PURPOSES
            }
            // second value will be quantum size
            else if(i == 1)
            {
                quantumSize = Integer.parseInt(args[i]);
                System.out.println("quantum size: " + quantumSize);     // TODO: TESTING PURPOSES
            }
            // other arguments are text file names
            else
            {
                // fetching data from file
                Scanner file = new Scanner(new File(args[i]));

                try
                {
                    ArrayList<Integer> pages = new ArrayList<Integer>();
                    String newText = "";

                    // reading the file
                    while(file.hasNext())
                    {
                        newText = file.nextLine();

                        // file has more information
                        if(!newText.equals("end"))
                        {
                            if(!newText.equals("begin"))
                            {
                                // managing and assigning data
                                int pageNum = Integer.parseInt(newText);
                                pages.add(pageNum);
                            }
                        }
                    }

                    // create object with data and add to list
                    Process process = new Process(pages);
                    pList.add(process);
                }
                catch(Exception e)
                {
                    System.out.println("ERROR: " + e);
                }
            }
        }

        for (Process p : pList)     // TODO: TESTING PURPOSES
        {
            System.out.println(p.getPageArray());
        }

    }
}