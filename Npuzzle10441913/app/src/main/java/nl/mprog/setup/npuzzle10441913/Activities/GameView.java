package nl.mprog.setup.npuzzle10441913.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.google.gson.Gson;

import nl.mprog.setup.npuzzle10441913.R;
import nl.mprog.setup.npuzzle10441913.adapters.ImageTileAdapter;
import nl.mprog.setup.npuzzle10441913.model.GameBoard;
import nl.mprog.setup.npuzzle10441913.util.Constants;


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

        new CountDownTimer(3000, 1000) {

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

        TextView textView = (TextView) findViewById(R.id.movesText);
        textView.setText("Number of moves: " + gameBoard.getMoves());

    }


    private void changeDifficulty(int difficulty) {
        gameBoard.quitGame();
        gameBoard.setDifficulty(difficulty);
        gameBoard.setupBoard();
        imageTileAdapter = new ImageTileAdapter(getApplicationContext(), gameBoard);
        GridView gridview = (GridView) findViewById(R.id.gameGridView);
        gridview.setNumColumns(difficulty);
        gridview.setAdapter(imageTileAdapter);
        startGame();
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

        if (id == R.id.restart) {
            gameBoard.startGame();
            updateGrid();
            return true;
        }else if (id == R.id.reshuffle) {

            startGame();
            return true;
        } else if (id == R.id.imageSelect) {
            gameBoard.quitGame();
            goToImageSelection();
            return true;
        } else if (id == R.id.easy) {
            if (gameBoard.getDifficulty() != Constants.EASY) {
                changeDifficulty(Constants.EASY);
            }
            return true;
        } else if (id == R.id.medium) {
            if (gameBoard.getDifficulty() != Constants.MEDIUM) {
                changeDifficulty(Constants.MEDIUM);
            }
            return true;
        } else if (id == R.id.hard) {
            if (gameBoard.getDifficulty() != Constants.HARD) {
                changeDifficulty(Constants.HARD);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToGameWon() {
        Intent intent = new Intent(this, GameWon.class);
        startActivity(intent);
    }

    private void goToImageSelection() {
        Intent intent = new Intent(this, ImageSelection.class);
        startActivity(intent);

    }

}
