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
            System.out.println("[#] Welcome to the IQ Puzzle PRO Solver!");
            System.out.println();
            System.out.println("[?] Specify the filename (without extension) of the puzzle input file:");
            String fileName = scanner.nextLine();
            
            System.out.println();
            if (Input.validateFilename(fileName) != null) 
            {
                String errorMsg = Input.validateFilename(fileName);
                System.out.println(errorMsg);

                Board board = new Board(0, 0, 0, errorMsg, null);
                System.out.println("\n[?] Save the output to a file? (Y/N)");
                Output.confirmError(fileName, board);
                System.out.println("[#] Thank you for using the IQ Puzzle Pro Solver!\n");
                scanner.close();
                return;
            }

            File currentDir = new File(System.getProperty("user.dir"));
            File parentDir = currentDir.getParentFile().getParentFile();
            File file = new File(parentDir + "/test/" + fileName + ".txt");
            String absolutePath = file.getAbsolutePath();

            if (Input.validateFile(file) != null) 
            {
                String errorMsg = Input.validateFile(file);
                System.out.println(errorMsg);

                Board board = new Board(0, 0, 0, errorMsg, null);
                System.out.println("\n[?] Save the output to a file? (Y/N)");
                Output.confirmError(fileName, board);
                System.out.println("[#] Thank you for using the IQ Puzzle Pro Solver!\n");
                scanner.close();
                return;
            }

            Input puzzleInput = Input.readInput(absolutePath);

            if (puzzleInput.getErrorMsg() != null) 
            {  
                String errorMsg = puzzleInput.getErrorMsg();
                System.out.println(errorMsg);

                Board board = new Board(0, 0, 0, errorMsg, null);
                System.out.println("\n[?] Save the output to a file? (Y/N)");
                Output.confirmError(fileName, board);
                System.out.println("[#] Thank you for using the IQ Puzzle Pro Solver!\n");
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
                    System.out.println("\n[?] Save the output to a file? (Y/N)");
                    Output.confirmError(fileName, board);
                    System.out.println("[#] Thank you for using the IQ Puzzle Pro Solver!\n");
                    scanner.close();
                    return;
                }
                
                if (board.initialFreeCells() != Piece.sumOfCells(pieces))
                {
                    String errorMsg = "Area of free cells is not equal to the area of the pieces. \n~\nFound area of free cells: " + board.initialFreeCells() + " units \nFound area of the pieces: " + Piece.sumOfCells(pieces) + " units";
                    System.out.println(errorMsg);

                    board = new Board(0, 0, 0, errorMsg, null);
                    System.out.println("\n[?] Save the output to a file? (Y/N)");
                    Output.confirmError(fileName, board);
                    System.out.println("[#] Thank you for using the IQ Puzzle Pro Solver!\n");
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
                    Output.confirmOptionCLI(fileName, board, endTime - startTime, bf.getAttempts());
                    scanner.close();
                    System.out.println("[#] Thank you for using the IQ Puzzle Pro Solver!\n");
                } 
                else 
                {
                    System.out.println("\nNo solution found.");
                    Output output = new Output(fileName, board, 0, 0);
                    output.saveToTextCLI();
                    System.out.println("[#] Thank you for using the IQ Puzzle Pro Solver!\n");
                    scanner.close();
                }
            }
        } 
        catch (IOException e) 
        {
            System.out.println("[!] An error occurred while reading the file. Please try again.");
            scanner.close();
            return;
        }
    }

}
