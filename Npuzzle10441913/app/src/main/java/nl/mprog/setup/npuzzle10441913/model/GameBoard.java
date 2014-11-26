package nl.mprog.setup.npuzzle10441913.model;

import java.util.List;

/**
 * Created by joeyvanriemsdijk on 21/11/14.
 */
public class GameBoard {
    private int moves;
    private List<Integer> scrambledState;
    private long imageId;
    private int difficulty;
    private List<Integer> currentState;

    public GameBoard() {
        this.moves = 0;
    }

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public List<Integer> getScrambledState() {
        return scrambledState;
    }

    public void setScrambledState(List<Integer> scrambledState) {
        this.scrambledState = scrambledState;
    }

    public long getImageId() {
        return imageId;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public List<Integer> getCurrentState() {
        return currentState;
    }

    public void setCurrentState(List<Integer> currentState) {
        this.currentState = currentState;
    }
}
