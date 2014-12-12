package nl.mprog.setup.npuzzle10441913.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import nl.mprog.setup.npuzzle10441913.R;
import nl.mprog.setup.npuzzle10441913.model.GameBoard;


public class GameWon extends ActionBarActivity {

    private GameBoard gameBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_won);

        Button button = (Button) this.findViewById(R.id.backToMenuButton);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToMenu();
                    }
                }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        String json = sharedPreferences.getString("gameboard", "");
        Gson gson = new Gson();
        setGameBoard(gson.fromJson(json, GameBoard.class));


        TextView textView = (TextView) findViewById(R.id.gameWonText);
        textView.setText("You have won the game in " + gameBoard.getMoves() + " moves!");
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    /**
     * method to quit the game and return back to the imageselection
     */
    public void goToMenu() {

        // quit the game
        gameBoard.quitGame();

        // return to the image menu
        Intent intent = new Intent(this, ImageSelection.class);
        startActivity(intent);
    }


    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }
}
