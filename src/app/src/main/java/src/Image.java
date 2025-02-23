package src;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Image extends JPanel 
{
    private static final int CELL_SIZE = 40;
    private static final int CIRCLE_PADDING = 4;
    private static final int JOINT_WIDTH = 12;
    private static final int JOINT_HEIGHT = 6;
    private static final int DIAGONAL_JOINT_WIDTH = 8; // Width of diagonal joint
    private static final Color EMPTY_CELL_COLOR = new Color(40, 40, 40);
    private Board board;
    
    public Image(Board board) 
    {
        this.board = board;
        int width = board.getWidth() * CELL_SIZE;
        int height = board.getHeight() * CELL_SIZE;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
    }
    
    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        paintBoard((Graphics2D) g);
    }

    private void paintBoard(Graphics2D g2d) 
    {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // First pass: Draw base circles
        for (int i = 0; i < board.getHeight(); i++) 
        {
            for (int j = 0; j < board.getWidth(); j++) 
            {
                char cell = board.getElement(i, j);
                drawBaseCell(g2d, i, j, cell);
            }
        }
        
        // Second pass: Draw connecting joints (horizontal, vertical, and diagonal)
        for (int i = 0; i < board.getHeight(); i++) 
        {
            for (int j = 0; j < board.getWidth(); j++) 
            {
                char cell = board.getElement(i, j);
                if (cell != ' ') 
                {
                    drawJoints(g2d, i, j, cell);
                }
            }
        }
        
        // Third pass: Draw letters
        for (int i = 0; i < board.getHeight(); i++) 
        {
            for (int j = 0; j < board.getWidth(); j++) 
            {
                char cell = board.getElement(i, j);
                if (cell != ' ') 
                {
                    drawLetter(g2d, i, j, cell);
                }
            }
        }
    }
    
    private void drawBaseCell(Graphics2D g2d, int row, int col, char letter) 
    {
        int x = col * CELL_SIZE;
        int y = row * CELL_SIZE;
        
        Ellipse2D.Double circle = new Ellipse2D.Double(
            x + CIRCLE_PADDING,
            y + CIRCLE_PADDING,
            CELL_SIZE - (2 * CIRCLE_PADDING),
            CELL_SIZE - (2 * CIRCLE_PADDING)
        );
        
        if (letter != '*') 
        {
            g2d.setColor(getColorForLetter(letter));
            g2d.fill(circle);
        } else 
        {
            g2d.setColor(EMPTY_CELL_COLOR);
            g2d.setStroke(new BasicStroke(1.5f));
            g2d.draw(circle);
        }
    }
    
    private void drawJoints(Graphics2D g2d, int row, int col, char letter) 
    {
        Color pieceColor = getColorForLetter(letter);
        g2d.setColor(pieceColor);
        
        boolean hasHorizontalOrVerticalJoint = false;
        
        // Check and draw horizontal joints
        if (col < board.getWidth() - 1 && board.getElement(row, col + 1) == letter) 
        {
            drawHorizontalJoint(g2d, row, col);
            hasHorizontalOrVerticalJoint = true;
        }
        
        // Check and draw vertical joints
        if (row < board.getHeight() - 1 && board.getElement(row + 1, col) == letter) 
        {
            drawVerticalJoint(g2d, row, col);
            hasHorizontalOrVerticalJoint = true;
        }
        
        // Only check for diagonal joints if there are no horizontal or vertical joints
        if (!hasHorizontalOrVerticalJoint) {
            // Check diagonal connections
            boolean canConnectDiagonally = false;
            
            // Check bottom-right diagonal
            if (row < board.getHeight() - 1 && col < board.getWidth() - 1) 
            {
                if (board.getElement(row + 1, col + 1) == letter &&
                    board.getElement(row + 1, col) != letter &&
                    board.getElement(row, col + 1) != letter) 
                {
                    drawDiagonalJoint(g2d, row, col, true);
                    canConnectDiagonally = true;
                }
            }
            
            // Check bottom-left diagonal
            if (row < board.getHeight() - 1 && col > 0) 
            {
                if (board.getElement(row + 1, col - 1) == letter &&
                    board.getElement(row + 1, col) != letter &&
                    board.getElement(row, col - 1) != letter) 
                {
                    drawDiagonalJoint(g2d, row, col, false);
                    canConnectDiagonally = true;
                }
            }
            
            // Check top-right diagonal
            if (row > 0 && col < board.getWidth() - 1) 
            {
                if (board.getElement(row - 1, col + 1) == letter &&
                    board.getElement(row - 1, col) != letter &&
                    board.getElement(row, col + 1) != letter) 
                {
                    drawDiagonalJoint(g2d, row - 1, col, false);
                    canConnectDiagonally = true;
                }
            }
            
            // Check top-left diagonal
            if (row > 0 && col > 0) 
            {
                if (board.getElement(row - 1, col - 1) == letter &&
                    board.getElement(row - 1, col) != letter &&
                    board.getElement(row, col - 1) != letter) 
                {
                    drawDiagonalJoint(g2d, row - 1, col, true);
                    canConnectDiagonally = true;
                }
            }
        }
    }
    
    private void drawHorizontalJoint(Graphics2D g2d, int row, int col) 
    {
        int x = col * CELL_SIZE;
        int y = row * CELL_SIZE;
        Rectangle2D.Double horizontalJoint = new Rectangle2D.Double(
            x + CELL_SIZE - CIRCLE_PADDING,
            y + (CELL_SIZE - JOINT_HEIGHT) / 2,
            JOINT_WIDTH,
            JOINT_HEIGHT
        );
        g2d.fill(horizontalJoint);
    }
    
    private void drawVerticalJoint(Graphics2D g2d, int row, int col) 
    {
        int x = col * CELL_SIZE;
        int y = row * CELL_SIZE;
        Rectangle2D.Double verticalJoint = new Rectangle2D.Double(
            x + (CELL_SIZE - JOINT_HEIGHT) / 2,
            y + CELL_SIZE - CIRCLE_PADDING,
            JOINT_HEIGHT,
            JOINT_WIDTH
        );
        g2d.fill(verticalJoint);
    }
    
    private void drawDiagonalJoint(Graphics2D g2d, int row, int col, boolean isRightDiagonal) 
    {
        int x = col * CELL_SIZE;
        int y = row * CELL_SIZE;
        
        // Save the current transform
        AffineTransform oldTransform = g2d.getTransform();
        
        // Move to the center of the joint location
        g2d.translate(x + CELL_SIZE / 2, y + CELL_SIZE / 2);
        
        // Rotate 45 or -45 degrees depending on the diagonal direction
        double angle = isRightDiagonal ? Math.PI / 4 : -Math.PI / 4;
        g2d.rotate(angle);
        
        // Draw the diagonal joint
        Rectangle2D.Double diagonalJoint = new Rectangle2D.Double(
            -DIAGONAL_JOINT_WIDTH / 2,
            -JOINT_HEIGHT / 2,
            DIAGONAL_JOINT_WIDTH,
            JOINT_HEIGHT
        );
        g2d.fill(diagonalJoint);
        
        // Restore the original transform
        g2d.setTransform(oldTransform);
    }
    
    private void drawLetter(Graphics2D g2d, int row, int col, char letter) 
    {
        int x = col * CELL_SIZE;
        int y = row * CELL_SIZE;
        
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics metrics = g2d.getFontMetrics();
        String text = String.valueOf(letter);
        int textX = x + (CELL_SIZE - metrics.stringWidth(text)) / 2;
        int textY = y + ((CELL_SIZE - metrics.getHeight()) / 2) + metrics.getAscent();
        g2d.drawString(text, textX, textY);
    }
    
    private Color getColorForLetter(char letter) 
    {
        String[] palette = board.getPalette();
        if (letter >= 'A' && letter <= 'Z') 
        {
            String ansiCode = palette[letter - 'A'];
            String[] parts = ansiCode.split(";");
            int r = Integer.parseInt(parts[2]);
            int g = Integer.parseInt(parts[3]);
            int b = Integer.parseInt(parts[4].substring(0, parts[4].length() - 1));
            return new Color(r, g, b);
        }
        return EMPTY_CELL_COLOR;
    }
    
    public static void displayBoard(Board board) 
    {
        JFrame frame = new JFrame("Puzzle Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Image(board));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public void saveToImage(String filename)
    {
        int width = board.getWidth() * CELL_SIZE;
        int height = board.getHeight() * CELL_SIZE;
        
        java.awt.image.BufferedImage bi = new java.awt.image.BufferedImage(
            width, height, java.awt.image.BufferedImage.TYPE_INT_ARGB
        );
        
        Graphics2D g2d = bi.createGraphics();
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, width, height);
        
        paintBoard(g2d);
        g2d.dispose();
        
        File currentDir = new File(System.getProperty("user.dir"));
        File testDir = currentDir.getParentFile().getParentFile();
        File pngFile = new File(testDir + "/test/" + filename + "-output.png");
        String pngPath = pngFile.getAbsolutePath();

        java.io.File file = new java.io.File(pngPath);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        if (file.exists())
        {
            System.out.println("[?] '" + filename + "-output.png' already exists. Overwrite? (Y/N)");
            Scanner scanner = new Scanner(System.in);
            boolean valid = false;

            while (!valid) 
            {
                String overwrite = scanner.nextLine();
                if (overwrite.equalsIgnoreCase("Y")) 
                {
                    try 
                    {
                        javax.imageio.ImageIO.write(bi, "PNG", file);
                    } 
                    catch (IOException e) 
                    {
                        e.printStackTrace();
                    }
                    valid = true;
                    System.out.println("\n[~] Successfully overwritten '" + filename + "-output.png'.\n");
                } 
                else if (overwrite.equalsIgnoreCase("N")) 
                {
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
            try 
            {
                javax.imageio.ImageIO.write(bi, "PNG", file);
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
            return;
        }
        

    }
}