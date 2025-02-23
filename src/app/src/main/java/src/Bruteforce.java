package src;

/**
 * Bruteforce main logic.
 */
public class Bruteforce 
{
    private int attempts;
    private Board board;
    private Piece[] pieces;
    private Piece[][] permutations;

    public int getAttempts() {return this.attempts;}

    /**
     * Constructs a Bruteforce object with the provided parameters.
     *
     * @param board Board object
     * @param pieces Array of Piece objects
     */
    public Bruteforce(Board board, Piece[] pieces) 
    {
        this.attempts = 0;
        this.board = board;
        this.pieces = pieces;
        this.permutations = Piece.uniquePermutations(pieces);
    }

    /**
     * Searches for a solution to the puzzle, using a recursive brute-force approach starting from the first piece.
     *
     * @param pieceIndex Index of the current piece
     * @return true if a solution is found, false otherwise
     */
    public boolean search(int pieceIndex) 
    {
        attempts++;
    
        // Base case: If all pieces have been placed, return true.
        if (pieceIndex == pieces.length) 
        {
            return true;
        }
    
        // Try to place the current piece at every board cell.
        for (int i = 0; i < board.getHeight(); i++) 
        {
            for (int j = 0; j < board.getWidth(); j++) 
            {
                // Get all unique transformations (permutations) of the current piece.
                Piece[] permutations = this.permutations[pieceIndex];
                for (Piece permuation : permutations) 
                {
                    // Attempt to place the piece at (i, j)
                    if (board.fitPiece(i, j, permuation)) 
                    {
                        if (search(pieceIndex + 1)) 
                        {
                            return true;
                        }
                        board.removePiece(i, j, permuation);
                    }
                }
            }
        
        }

        // Base case: If no piece can be placed, return false.
        return false;
    }
    
}
