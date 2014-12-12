package nl.mprog.setup.npuzzle10441913;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import nl.mprog.setup.npuzzle10441913.adapters.ImageTileAdapter;
import nl.mprog.setup.npuzzle10441913.model.GameBoard;


public class GameView extends Activity {

    private GameBoard gameBoard;
    private ImageTileAdapter imageTileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void onResume() {
        super.onResume();

        setContentView(R.layout.game_view);

        // load the gameboard from the shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        String json = sharedPreferences.getString("gameboard", "");
        Gson gson = new Gson();
        gameBoard = gson.fromJson(json, GameBoard.class);

        // check to see if the game has been started yet
        if (gameBoard.getCurrentState() == null) {
            gameBoard.setupBoard();
            startGame();
        }

        GridView gridview = (GridView) findViewById(R.id.gameGridView);
        gridview.setNumColumns(gameBoard.getDifficulty());
        imageTileAdapter = new ImageTileAdapter(this, gameBoard);
        gridview.setAdapter(imageTileAdapter);


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                boolean isGameWon = gameBoard.moveBlock(position);
                updateGrid();
                if (isGameWon) {
                    goToGameWon();
                }
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(gameBoard);
        editor.putString("gameboard", json);
        editor.commit();

    }


    /**
     * Method which starts the game by scrambling the gameboard in the background while the
     * preview is shown. And sets the gameboard such that the game can start after the 3 second
     * delay.
     */
    private void startGame() {

        new CountDownTimer(3000, 50) {

            public void onTick(long millisUntilFinished) {
                // make one move to scramble the board
                gameBoard.scrambleMove();
            }

            public void onFinish() {
                // start the game
                gameBoard.startGame();
                updateGrid();
            }

        }.start();
    }


    /**
     * Method which refreshes the gridview of the gameboard to show the changed gamestate.
     */
    private void updateGrid() {
        GridView gridview = (GridView) findViewById(R.id.gameGridView);
        gridview.invalidateViews();
        gridview.setAdapter(imageTileAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game_view, menu);
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

    public void goToGameWon() {
        Intent intent = new Intent(this, GameWon.class);
        startActivity(intent);
    }

}
