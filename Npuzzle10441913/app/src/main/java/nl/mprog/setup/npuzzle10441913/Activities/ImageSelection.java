package nl.mprog.setup.npuzzle10441913.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;

import nl.mprog.setup.npuzzle10441913.R;
import nl.mprog.setup.npuzzle10441913.adapters.ImageSelectionAdapter;
import nl.mprog.setup.npuzzle10441913.model.GameBoard;
import nl.mprog.setup.npuzzle10441913.util.Constants;


/**
 * @author Joey van Riemsdijk
 */

public class ImageSelection extends Activity {

    private GameBoard gameBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.image_selection);

        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        String json = sharedPreferences.getString("gameboard", "");

        Gson gson = new Gson();
        setGameBoard(gson.fromJson(json, GameBoard.class));

        // check if there is a known gameboard and difficulty, if not, make one and set it to medium
        if (gameBoard == null || (gameBoard.getDifficulty() != Constants.EASY
                && gameBoard.getDifficulty() != Constants.MEDIUM
                && gameBoard.getDifficulty() != Constants.HARD)) {

            setGameBoard(new GameBoard());
            gameBoard.setDifficulty(Constants.MEDIUM);
        } else {
            // make sure that the previous game is closed
            gameBoard.quitGame();
        }


        // set the layout
        GridView gridView = (GridView) this.findViewById(R.id.imageGrid);
        gridView.setNumColumns(2);
        gridView.setAdapter(new ImageSelectionAdapter(this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                // save image id
                gameBoard.setImageId(id);

                // change view to difficulty selection
                goToGame();
            }
        });

    }

    @Override
    public void onBackPressed() {
        // to prevent the game from returning to the game won overview
    }

    @Override
    protected void onPause() {

        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(gameBoard);
        editor.putString("gameboard", json);
        editor.commit();

        super.onPause();
    }


    /**
     * Method to start the game, switches over to the gameview activity
     */
    public void goToGame() {

        Intent intent = new Intent(this, GameView.class);
        startActivity(intent);
    }


/* ------------------ Getters & Setters ------------------- */

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }
}
