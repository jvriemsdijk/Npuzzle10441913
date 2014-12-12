package nl.mprog.setup.npuzzle10441913;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

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

        // TODO : check for previous game from shared prefs and load it
        setGameBoard(new GameBoard());


        // set the layout
        GridView gridView = (GridView) this.findViewById(R.id.imageGrid);
        gridView.setNumColumns(2);
        gridView.setAdapter(new ImageSelectionAdapter(this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                // save image id
                gameBoard.setImageId(id);

                // change view to difficulty selection
                goToDifficultySelection();
            }
        });

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.image_selection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Switches to the difficulty selection view, and adds the listeners for the selectors
     */
    public void goToDifficultySelection() {
        setContentView(R.layout.difficulty_selection);
        addDifficultyListeners();
    }


    /**
     * Method to add difficulty listeners to to the difficulty selection buttons.
     */
    public void addDifficultyListeners() {
        Button button = (Button) this.findViewById(R.id.easyButton);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gameBoard.setDifficulty(Constants.EASY);
                        goToGame();
                    }
                }

        );

        button = (Button) this.findViewById(R.id.mediumButton);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gameBoard.setDifficulty(Constants.MEDIUM);
                        goToGame();
                    }
                }
        );

        button = (Button) this.findViewById(R.id.hardButton);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gameBoard.setDifficulty(Constants.HARD);
                        goToGame();
                    }
                }
        );
    }


    /**
     * Method to start the game
     */
    public void goToGame() {

        Intent intent = new Intent(this, GameView.class);
//        intent.putExtra("gameBoard", gameBoard);
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
