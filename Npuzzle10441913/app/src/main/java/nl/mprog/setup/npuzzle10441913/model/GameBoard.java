package nl.mprog.setup.npuzzle10441913.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import nl.mprog.setup.npuzzle10441913.util.Constants;

import static java.lang.Math.abs;

/**
 * Created by joeyvanriemsdijk on 21/11/14.
 */
public class GameBoard implements Serializable {

    private int moves;
    private List<Integer> scrambledState;
    private long imageId;
    private int difficulty;
    private List<Integer> currentState;
    private List<Integer> solvedState;
    private Integer whiteTile;
    private Integer previousMove;


    /* Getters & setters */


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

    public List<Integer> getSolvedState() {
        return solvedState;
    }

    public void setSolvedState(List<Integer> solvedState) {
        this.solvedState = solvedState;
    }

    public Integer getWhiteTile() {
        return whiteTile;
    }

    public void setWhiteTile(Integer whiteTile) {
        this.whiteTile = whiteTile;
    }

    public Integer getPreviousMove() {
        return previousMove;
    }

    public void setPreviousMove(Integer previousMove) {
        this.previousMove = previousMove;
    }

    /**
     * Method that moves the tapped block if it is a legal move. Also returns a boolean that
     * indicating if the game is won. If the move is illegal, do nothing
     *
     * @param position
     * @return boolean if game is won
     */
    public boolean moveBlock(int position) {

        // only if the given position is within the board
        if (position >= 0 && position < currentState.size()) {
            int whiteBlock = difficulty * difficulty;
            int whiteBlockPosition = currentState.indexOf(whiteBlock);

            if (isLegalMove(position, whiteBlockPosition)) {
                int clickedValue = currentState.get(position);
                currentState.set(whiteBlockPosition, clickedValue);
                currentState.set(position, whiteBlock);
                moves += 1;
                return isGameWon();
            }
        }
        // if the move is illegal, do nothing
        return false;
    }

    /**
     * Method to determine if the given position is next to the white block position based on the
     * known difficulty of the game. Note: this method assumes that both given positions are
     * within the gameboard.
     *
     * @param position
     * @param whiteBlockPosition
     * @return boolean if the move is legal
     */
    private boolean isLegalMove(int position, int whiteBlockPosition) {

        int deltaPos = abs(whiteBlockPosition - position);

        if (deltaPos == this.difficulty) {
            return true;
        } else if (deltaPos == 1) {
            if (position > whiteBlockPosition) {
                return position % difficulty != 0;
            } else {
                return whiteBlockPosition % difficulty != 0;
            }
        } else {
            return false;
        }
    }

    /**
     * This method checks if the game is won.
     *
     * @return boolean if game is won
     */
    public boolean isGameWon() {
        for (Integer value : currentState) {

            if (value != currentState.indexOf(value) + 1) {

                return false;
            }
        }
        return true;
    }



    public void startGame() {

        setCurrentState(scrambledState);

    }

    public void setupBoard() {
        // check for image and difficulty
        if (imageId != 0 && difficulty != 0) {

            setWhiteTile(difficulty * difficulty);


            solvedState = new ArrayList<Integer>(whiteTile);

            for (int i = 0; i < (whiteTile); i++) {
                solvedState.add(i, (i + 1));
            }

            setPreviousMove(-1);

            setCurrentState(solvedState);
            setScrambledState(solvedState);

        }
    }


    public void scrambleMove() {

        Integer whiteTilePos;
        List<Integer> possibleMoves;
        Integer tileToMove;
        int tileToMovePos;

        whiteTilePos = scrambledState.indexOf(whiteTile);
        possibleMoves = new ArrayList<Integer>();

        for (int i = 0; i < whiteTile; i++) {
            if (isLegalMove(i, whiteTilePos)) {
                possibleMoves.add(i);
            }
        }

        // selecteer van opties - vorige zet
        if (possibleMoves.contains(previousMove)) {

            possibleMoves.remove(previousMove);
        }

        tileToMovePos = possibleMoves.get((int) Math.floor(Math.random() * possibleMoves.size()));
        tileToMove = scrambledState.get(tileToMovePos);

        // wissel, en sla vorige zet op
        scrambledState.set(whiteTilePos, tileToMove);
        scrambledState.set(tileToMovePos, whiteTile);

        previousMove = whiteTilePos;

    }


}
