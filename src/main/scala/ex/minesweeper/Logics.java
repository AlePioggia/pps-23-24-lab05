package ex.minesweeper;

public interface Logics {

    /**
     * attempt to move Knight on position row,col, if possible
     * 
     * @param row
     * @param col
     * @return whether the pawn has been hit
     */
    boolean hit(int row, int col);

    /**
     * @param row
     * @param col
     * @return whether position row,col has the knight
     */
    boolean hasKnight(int row, int col);

    /**
     * @param row
     * @param col
     * @return whether position row,col has the pawn
     */
    boolean hasPawn(int row, int col);

    /**
     * 
     * @return pawn's position
     */
    Pair<Integer, Integer> getPawn();

    /**
     * 
     * @return Knight's position
     */
    Pair<Integer, Integer> getKnight();

}
