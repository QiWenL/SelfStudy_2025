package game2048;

import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @QL
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }
    /** Helper function to check if the given row, col are valid */
    public static boolean Valid_index(int size, int col, int row){
        if (row <0 || col<0){
            return false;
        }
        if (row>=size || col>=size){
            return false;
        }
        return true;
    }
    /** Helper method to check if the tile has any neighboring tile that is equivalent */
    public static boolean check_tile (Board b, int current_tile, int col, int row) {
        if (Valid_index(b.size(), col, row)) {
            int neighbor_tile = b.tile(col, row).value();
            return neighbor_tile == current_tile;
        }
        return false;
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */

    public int NumberNullSpacesInColumn(Board b, int column, int row){
        // returns the number of null space above the current tile
        int number_of_null = 0;
        for (int i = 1; i < b.size()-row; i +=1) {
            if (Valid_index(b.size(), column, i)){
                Tile tile_to_check = b.tile(column, row+i);
                if (tile_to_check == null) {
                    number_of_null += 1;
                }
                else {
                    break;
                }
            }
        }
        return number_of_null;
    }
    //up no merge for 1 column
    // check if column has any empty value
    // if so, return row +1
    public int upNoMergeIndex(Board b,int column, int row){
            int row_index = 0;// count the number of empty space above the current tile
             Tile tile = b.tile(column, row);
            if (tile!=null){
                // get the number of space available for moving up
                int up_space = NumberNullSpacesInColumn(b, column, row);
                //if there is space to move up, check if index is valid
                if (up_space >=1){
                    if (Valid_index(b.size(), column, row+up_space)) {
                        return up_space;
                    }
                }
            }
            return 0;
        }

    public static boolean UpBasicMergeMove(Board b, int column, int row) {
        //check if the tile above has the same value
        int current_tile = b.tile(column, row).value();
        for (int i = row + 1; i < b.size(); i += 1) {
            if (Valid_index(b.size(), column, i)) {
                Tile tile_to_check = b.tile(column, i);
                if (tile_to_check!=null){
                    if (current_tile == tile_to_check.value()) {
                        return true;
                    }
                    }
                }
            }
        return false;
    }

    public boolean tilt(Side side) {
        boolean changed;
        changed = false;
        // TODO: Modify this.board (and perhaps this.score) to account
        // for the tilt to the Side SIDE.
        if (side != Side.NORTH) {
            board.setViewingPerspective(side);
            changed = true;
        }
        //merge up operations
        for (int col = 0; col <= board.size() - 1; col += 1) {
            // initialize an array to show whether each tile has been merged
            boolean[] merged_tiles = new boolean[4];
            // initialize a boolean to show whether a merged operation has happened
            // loop starts at row 3(top row - no need to move because it's already at the top)
            for (int row = board.size() - 1; row >= 0; row -= 1) {
                int merge_move = 0;
                Tile tile = board.tile(col, row);
                if (tile != null) {
                    int move_up_space = upNoMergeIndex(board, col, row);
                    //check if merge has happened and if merge can happen
                    if (UpBasicMergeMove(board, col, row)) {
                        merge_move = 1;
                        // if merged happens to a tile, then save it in the array
                    }
                    int total_move = merge_move + move_up_space;
                    if (total_move > 0) {
                        changed = true;
                        //keeps track of the score
                        if (total_move > move_up_space) {
                            //check if merge has already taken place at the tile to be merged
                            if (!merged_tiles[total_move + row]) {
                                board.move(col, row + total_move, tile);
                                // updates merged tiles
                                merged_tiles[total_move + row] = true;
                                //updates score
                                score += tile.value() * 2;
                            } else {
                                board.move(col, row + move_up_space, tile);
                            }
                        }
                        else {board.move(col, row + move_up_space, tile);}
                    }
                }
            }
        }
            board.setViewingPerspective(Side.NORTH);
            checkGameOver();
            //changed = true;
            //If the board changed, set the changed local variable to true.
            if (changed) {
                setChanged();
            }
            return changed;
    }
    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        for (int row = 0; row <= b.size()-1; row= row+1 ) {
            for (int col = 0; col <= b.size()-1; col = col + 1) {
                if (b.tile(row, col) == null)
                    return true;
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        for (int row = 0; row <= b.size() - 1; row = row + 1) {
            for (int col = 0; col <= b.size() - 1; col = col + 1) {
                Tile current_tile = b.tile(row, col);
                if (current_tile!=null) {
                    int current_val = b.tile(row, col).value();
                    if (current_val == MAX_PIECE) {
                        return true;
                    }
                }
            }
        }
    return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        // case #1 - if there is at least one empty space on the board
        int size = b.size();
        if (emptySpaceExists(b)) {
            return true;
        }
        for (int col = 0; col < size; col += 1) {
            for (int row = 0; row < size; row += 1) {
                int current_tile = b.tile(col, row).value();
                //top
                if (check_tile(b, current_tile, col, row+1)) {
                    return true;
                    //bottom
                    }else if (check_tile(b, current_tile, col, row-1)) {
                    return true;
                    //left
                        } else if (check_tile(b, current_tile, col-1, row)) {
                            return true;
                            //right
                        } else if (check_tile(b, current_tile, col + 1, row)) {
                    return true;
                }
                }
            }
        return false;
    }

    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
