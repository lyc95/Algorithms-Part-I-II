import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int n;
    private final int[][] board;
    private int posX;
    private int posY;
    private int hVal = -1;
    private int mVal = -1;
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles)
    {
        if (tiles == null)
            throw new IllegalArgumentException("Invalid Input");
        int rows = tiles.length;;
        int cols = tiles[0].length;
        if(rows != cols || rows < 2 || rows > 128)
            throw new IllegalArgumentException("Invalid Input");
        this.n = rows;
        board = new int[rows][cols];
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                this.board[i][j] = tiles[i][j];
                if(tiles[i][j] == 0)
                {
                    posX = i;
                    posY = j;
                }
            }
        }

    }
    // string representation of this board
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        if(board == null || n < 2)
            throw new IllegalArgumentException("");
        s.append(n).append('\n');
        System.out.println(n);
        for (int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                s.append(' ').append(board[i][j]);
            }
            s.append('\n');
        }
        return s.toString();
    }

    // board dimension n
    public int dimension()
    {
        return n;
    }

    // number of tiles out of place
    public int hamming()
    {
        if(hVal > 0)
            return hVal;
        else
        {
            hVal = 0;
            for (int i = 0; i < n; i++)
            {
                for (int j = 0; j < n; j++)
                {
                    int currNum = board[i][j];
                    int[] pos = getPos(currNum);
                    if(pos[0] != i || pos[1] != j)
                    {
                        hVal++;
                    }
                }
            }
            return hVal;
        }

    }

    public int[] getPos(int num)
    {
        return new int[]{(num-1)/n, (num-1)%n};
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan()
    {
        if (mVal < 0)
        {
            mVal = 0;
            for (int i = 0; i < n; i++)
            {
                for (int j = 0; j < n; j++)
                {
                    int currNum = board[i][j];
                    int[] pos = getPos(currNum);
                    mVal += Math.abs(i - pos[0]) + Math.abs(j - pos[1]);
                }
            }
        }
        return mVal;
    }

    // is this board the goal board?
    public boolean isGoal()
    {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y)
    {
        if (y instanceof Board)
        {
            Board board = (Board) y;
            if (board == this)
                return true;
            for (int i = 0; i < n; i++)
            {
                for (int j = 0; j < n; j++)
                {
                    if (this.board[i][j] != board.board[i][j])
                        return false;
                }
            }
            return true;
        }
        else
        {
            return false;
        }
    }

    // all neighboring boards
    public Iterable<Board> neighbors()
    {
        List<Board> res = new ArrayList<>();
        int[][] arr = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for (int i = 0; i < 4; i++)
        {
            int newX = posX + arr[i][0];
            int newY = posY + arr[i][1];
            if(isValid(newX, newY))
            {
                swap(posX, posY, newX, newY);
                res.add(new Board(board));
                swap(posX, posY, newX, newY);
            }
        }
        return res;
    }
    private void swap(int x1, int y1, int x2, int y2)
    {
        int tmp = board[x1][y1];
        board[x1][y1] = board[x2][y2];
        board[x2][y2] = tmp;
    }
    private boolean isValid(int x, int y)
    {
        return x >= 0 && x < n && y >= 0 && y < n;
    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin()
    {
        //change [0][0] and [0][1]
        //or [1][0] and [1][1]
        Board twin;
        if (board[0][0] != 0 && board[0][1] != 0)
        {
            swap(0, 0, 0, 1);
            twin = new Board(this.board);
            swap(0, 0, 0, 1);
        }
        else
        {
            swap(1, 0, 1, 1);
            twin = new Board(this.board);
            swap(1, 0, 1, 1);
        }
        return twin;
    }

    // unit testing (not graded)
    public static void main(String[] args)
    {

    }

}
