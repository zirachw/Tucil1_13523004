package src;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Output 
{
    private String fileName;
    private Board board;
    private long time;
    private int attempts;

    /**
     * Constructs an Output object with the provided parameters.
     *
     * @param board Board object
     * @param time Time taken to solve the puzzle
     * @param attempts Number of attempts to solve the puzzle
     */
    public Output(String fileName, Board board, long time, int attempts)
    {
        this.fileName = fileName;
        this.board = board;
        this.time = time;
        this.attempts = attempts;
    }

    public String getFilename() {return this.fileName;}
    public Board getBoard() {return this.board;}
    public long getTime() {return this.time;}
    public int getAttempts() {return this.attempts;}

    public static void confirmCLI(String filename, Board board, long time, int attempts)
    {
        Scanner scanner = new Scanner(System.in);
        boolean valid = false;
        while (!valid) 
        {
            String save = scanner.nextLine();
            if (save.equalsIgnoreCase("Y")) 
            {
                if (board.getType() == "Filename cannot be empty.")
                {
                    Output output = new Output("invalid-file-1", board, 0, 0);
                    output.saveToFileCLI();
                    valid = true;
                }
                // Use regex to check if board.getType() contains "does not exist in the ~/test directory."
                else if (board.getType().matches(".*does not exist in the ~/test directory.*"))
                {
                    Output output = new Output("invalid-file-2", board, 0, 0);
                    output.saveToFileCLI();
                    valid = true;
                }

                else
                {
                    Output output = new Output(filename, board, time, attempts);
                    output.saveToFileCLI();
                    valid = true;
                }
            } 
            else if (save.equalsIgnoreCase("N")) 
            {
                System.out.println("\nThank you for using the IQ Puzzle Pro Solver!\n");
                valid = true;
            } 
            else 
            {
                System.out.println("\nInvalid input. Please enter Y or N.");
            }
        }
        scanner.close();
        return;
    }

    public void saveToFileCLI()
    {
        // get the current working directory
        File currentDir = new File(System.getProperty("user.dir"));
        File testDir = currentDir.getParentFile().getParentFile();
        File file = new File(testDir + "/test/" + getFilename() + "-output.txt");
        String path = file.getAbsolutePath();

        // Check if already a file with the same name exists
        File fileCheck = new File(path);
        if (getFilename() == "invalid-0")
        {
            writeFileCLI(path);
            System.out.println("\nFile saved successfully as 'invalid-0.txt', thank you for using the IQ Puzzle Pro Solver!\n");
            return;
        }

        else if (fileCheck.exists())
        {
            System.out.println("\nAn output file with the same name already exists. Do you want to overwrite it? (Y/N)");
            Scanner scanner = new Scanner(System.in);
            boolean valid = false;

            while (!valid) 
            {
                String overwrite = scanner.nextLine();
                if (overwrite.equalsIgnoreCase("Y")) 
                {
                    writeFileCLI(path);
                    valid = true;
                    System.out.println("\nFile overwritten successfully, thank you for using the IQ Puzzle Pro Solver!\n");
                } 
                else if (overwrite.equalsIgnoreCase("N")) 
                {
                    System.out.println("\nThank you for using the IQ Puzzle Pro Solver!\n");
                    valid = true;
                } 
                else 
                {
                    System.out.println("\nInvalid input. Please enter Y or N.");
                }
            }
            scanner.close();
            return;
        }
        else
        {
            writeFileCLI(path);
            System.out.println("\nFile saved successfully, thank you for using the IQ Puzzle Pro Solver!\n");
            return;
        }
    }

    public void writeFileCLI(String path)
    {
        try
        {
            File file = new File(path);
            file.createNewFile();
        }
        catch (IOException e)
        {
            System.out.println("An error occurred while creating the file. Please try again.");
            return;
        }

        try
        {
            java.io.FileWriter writer = new java.io.FileWriter(path);

            if (board.getP() == 0)
            {
                writer.write(getBoard().getType());
                writer.close();
                return;
            }
            else
            {
                writer.write("Solution:\n");
                for (int i = 0; i < getBoard().getHeight(); i++)
                {
                    for (int j = 0; j < getBoard().getWidth(); j++)
                    {
                        writer.write(getBoard().getElement(i, j));
                    }
                    writer.write("\n");
                }
    
                writer.write("\n");
                writer.write("Searching Time: " + getTime() + "ms\n");
                writer.write("\n");
                writer.write("Number of Cases: " + getAttempts());
                writer.close();
                return;
            }
        }
        catch (IOException e)
        {
            System.out.println("An error occurred while writing to the file. Please try again.");
            return;
        }
    }
    
}
