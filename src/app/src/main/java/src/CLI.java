package src;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class CLI {

    /**
     * Main method for the CLI application.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);

        try 
        {
            System.out.print("\033[H\033[2J");
            System.out.println();
            System.out.println("Welcome to the Puzzle Solver!");
            System.out.println();
            System.out.println("Specify the filename (without extension) of the puzzle input file:");
            String fileName = scanner.nextLine();
            
            System.out.println();
            if (Input.validateFilename(fileName) != null) 
            {
                String errorMsg = Input.validateFilename(fileName);
                System.out.println(errorMsg);

                Board board = new Board(0, 0, 0, errorMsg, null);
                System.out.println("\nSave the output to a file? (Y/N)");
                Output.confirmCLI(fileName, board, 0, 0);

                scanner.close();
                return;
            }

            File currentDir = new File(System.getProperty("user.dir"));
            File testDir = currentDir.getParentFile().getParentFile();
            File file = new File(testDir + "/test/" + fileName + ".txt");
            String absolutePath = file.getAbsolutePath();

            if (Input.validateFile(file) != null) 
            {
                String errorMsg = Input.validateFile(file);
                System.out.println(errorMsg);

                Board board = new Board(0, 0, 0, errorMsg, null);
                System.out.println("\nSave the output to a file? (Y/N)");
                Output.confirmCLI(fileName, board, 0, 0);
                
                scanner.close();
                return;
            }

            Input puzzleInput = Input.readInput(absolutePath);

            if (puzzleInput.getErrorMsg() != null) 
            {  
                String errorMsg = puzzleInput.getErrorMsg();
                System.out.println(errorMsg);

                Board board = new Board(0, 0, 0, errorMsg, null);
                System.out.println("\nSave the output to a file? (Y/N)");
                Output.confirmCLI(fileName, board, 0, 0);
                
                scanner.close();
                scanner.close();
            }
            else
            {
                Board board = new Board(puzzleInput.getN(), puzzleInput.getM(), puzzleInput.getP(), puzzleInput.getS(), puzzleInput.getCustom());
                Piece[] pieces = Piece.createPieces(puzzleInput.getP(), puzzleInput.getPieces());

                if (pieces.length == 1 && pieces[0].getErrorMsg() != null) 
                {
                    String errorMsg = pieces[0].getErrorMsg();
                    System.out.println(errorMsg);

                    board = new Board(0, 0, 0, errorMsg, null);
                    System.out.println("\nSave the output to a file? (Y/N)");
                    Output.confirmCLI(fileName, board, 0, 0);
                    
                    scanner.close();
                    return;
                }
                
                if (board.initialFreeCells() != Piece.sumOfCells(pieces))
                {
                    String errorMsg = "Area of free cells is not equal to the area of the pieces. \n~\nFound area of free cells: " + board.initialFreeCells() + " units \nFound area of the pieces: " + Piece.sumOfCells(pieces) + " units";
                    System.out.println(errorMsg);

                    board = new Board(0, 0, 0, errorMsg, null);
                    System.out.println("\nSave the output to a file? (Y/N)");
                    Output.confirmCLI(fileName, board, 0, 0);
                    
                    scanner.close();
                    return;
                }

                Bruteforce bf = new Bruteforce(board, pieces);
                long startTime = System.currentTimeMillis();
                boolean found = bf.search(0);
                long endTime = System.currentTimeMillis();

                if (found) 
                {
                    System.out.println("Solution:");
                    board.print();

                    System.out.println();
                    System.out.println("Searching Time: " + (endTime - startTime) + " ms");
                    System.out.println();
                    System.out.println("Number of Cases: " + bf.getAttempts());

                    System.out.println("\nSave the output to a file? (Y/N)");
                    Output.confirmCLI(fileName, board, endTime - startTime, bf.getAttempts());
                    
                    scanner.close();
                } 
                else 
                {
                    System.out.println("\nNo solution found.");
                    scanner.close();
                }
            }
        } 
        catch (IOException e) 
        {
            System.out.println("An error occurred while reading the file. Please try again.");
            scanner.close();
            return;
        }
    }

}
