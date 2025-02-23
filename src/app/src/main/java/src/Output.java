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

    public static void confirmError(String filename, Board board)
    {
        Scanner scanner = new Scanner(System.in);
        boolean valid = false;
        while (!valid) 
        {
            String save = scanner.nextLine();
            System.out.println();
            if (save.equalsIgnoreCase("Y")) 
            {
                if (board.getType() == "Filename cannot be empty.")
                {
                    Output output = new Output("invalid-file-1", board, 0, 0);
                    output.saveToTextCLI();
                    valid = true;
                }
                // Use regex to check if board.getType() contains "does not exist in the ~/test directory."
                else if (board.getType().matches(".*does not exist in the ~/test directory.*"))
                {
                    Output output = new Output("invalid-file-2", board, 0, 0);
                    output.saveToTextCLI();
                    valid = true;
                }

                else
                {
                    Output output = new Output(filename, board, 0, 0);
                    output.saveToTextCLI();
                    valid = true;
                }
            } 
            else if (save.equalsIgnoreCase("N")) 
            {
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

    public static void confirmOptionCLI(String filename, Board board, long time, int attempts)
    {
        System.out.println("\nSave the output to a file?");
        System.out.println("[1] .txt file");
        System.out.println("[2] .png file");
        System.out.println("[3] Both");
        System.out.println("[4] None");   
        System.out.println();

        Scanner scanner = new Scanner(System.in);
        boolean valid = false;

        while (!valid) 
        {
            String save = scanner.nextLine();
            System.out.println();
            int option;

            if (save.split("\\s+").length > 1)
            {
                System.out.println("[!] Invalid input. Please enter a single integer.");
                continue;
            }
            
            if (save.matches("\\d+"))
            {
                option = Integer.parseInt(save);
                if (option == 1)
                {
                    Output output = new Output(filename, board, time, attempts);
                    output.saveToTextCLI();
                    System.out.println("[~] Successfully saved as '" + filename + "-output.txt'.\n");
                    valid = true;
                }
                else if (option == 2)
                {
                    Image image = new Image(board);
                    image.saveToImage(filename);
                    System.out.println("[~] Successfully saved as '" + filename + "-output.png'.\n");
                    valid = true;
                }
                else if (option == 3)
                {
                    Output output = new Output(filename, board, time, attempts);
                    output.saveToTextImageCLI();
                    System.out.println("[~] Successfully saved as '" + filename + "-output.txt' and '" + filename + "-output.png'.\n");
                    valid = true;
                }
                else if (option == 4) 
                {
                    valid = true;
                }
                else
                {
                    System.out.println("\n[!] Invalid input. Please enter 1 to 4.");
                }
            }
            else
            {
                System.out.println("\n[!] Invalid input. Please enter a single integer.");
            }
        }
        scanner.close();
        return;
    }

    public void saveToTextCLI()
    {
        File currentDir = new File(System.getProperty("user.dir"));
        File testDir = currentDir.getParentFile().getParentFile();

        File txtFile = new File(testDir + "/test/" + getFilename() + "-output.txt");
        String txtPath = txtFile.getAbsolutePath();

        File fileCheck = new File(txtPath);
        if (getFilename() == "invalid-file-1")
        {
            writeTextCLI(txtPath);
            return;
        }

        else if (fileCheck.exists())
        {
            System.out.println("[?] '" + getFilename() + "-output.txt' already exists. Overwrite? (Y/N)");
            Scanner scanner = new Scanner(System.in);
            boolean valid = false;

            while (!valid) 
            {
                String overwrite = scanner.nextLine();
                if (overwrite.equalsIgnoreCase("Y")) 
                {
                    writeTextCLI(txtPath);
                    valid = true;
                    System.out.println("\n[~] Successfully overwritten '" + getFilename() + "-output.txt'.\n");
                } 
                else if (overwrite.equalsIgnoreCase("N")) 
                {
                    System.err.println();
                    valid = true;
                } 
                else 
                {
                    System.out.println("\n[!] Invalid input. Please enter Y or N.");
                }
            }
            scanner.close();
            return;
        }
        else
        {
            writeTextCLI(txtPath);
            return;
        }
    }

    public void writeTextCLI(String path)
    {
        File currentDir = new File(System.getProperty("user.dir"));
        File testDir = currentDir.getParentFile().getParentFile();

        File txtFile = new File(testDir + "/test/" + getFilename() + "-output.txt");
        String txtPath = txtFile.getAbsolutePath();

        try
        {
            File file = new File(txtPath);
            file.createNewFile();
        }
        catch (IOException e)
        {
            System.out.println("[!] An error occurred while creating the file. Please try again.");
            return;
        }

        try
        {
            java.io.FileWriter writer = new java.io.FileWriter(txtPath);

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
            System.out.println("[!] An error occurred while writing to the file. Please try again.");
            return;
        }
    }

    public void saveToTextImageCLI()
    {
        File currentDir = new File(System.getProperty("user.dir"));
        File testDir = currentDir.getParentFile().getParentFile();

        File txtFile = new File(testDir + "/test/" + getFilename() + "-output.txt");
        String txtPath = txtFile.getAbsolutePath();

        File fileCheck = new File(txtPath);
        if (getFilename() == "invalid-file-1")
        {
            writeTextCLI(txtPath);
            return;
        }

        else if (fileCheck.exists())
        {
            System.out.println("[?] '" + getFilename() + "-output.txt' already exists. Overwrite? (Y/N)");
            Scanner scanner = new Scanner(System.in);
            boolean valid = false;

            while (!valid) 
            {
                String overwrite = scanner.nextLine();
                if (overwrite.equalsIgnoreCase("Y")) 
                {
                    writeTextCLI(txtPath);
                    valid = true;
                    System.out.println("\n[~] Successfully overwritten '" + getFilename() + "-output.txt'.\n");
                } 
                else if (overwrite.equalsIgnoreCase("N")) 
                {
                    System.err.println();
                    valid = true;
                } 
                else 
                {
                    System.out.println("\n[!] Invalid input. Please enter Y or N.");
                }
            }

            Image image = new Image(getBoard());
            image.saveToImage(getFilename());
            scanner.close();
            return;
        }
        else
        {
            writeTextCLI(txtPath);
            Image image = new Image(getBoard());
            image.saveToImage(getFilename());
            return;
        }
    }
}
