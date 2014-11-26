package nl.mprog.setup.npuzzle10441913;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

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
        setContentView(R.layout.image_selection);

        setGameBoard(new GameBoard());

        // TODO : check for previous game and load it


        // set the layout
        GridView gridView = (GridView) this.findViewById(R.id.imageGrid);
        gridView.setNumColumns(2);
        gridView.setAdapter(new ImageAdapter(this));
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
     * Custom image adapter for the gridview. Scans the drawable dir for up to 10 images with the
     * naming: 'npuzzleX' where X is a number (single digit).
     */
    public class ImageAdapter extends BaseAdapter {

        private Context context;
        private List<Integer> iconIdList;
        private int iconSize;


        public ImageAdapter(Context c) {
            context = c;
            iconSize = context.getResources().getDisplayMetrics().widthPixels / 3;
            iconIdList = new ArrayList<Integer>();

            // as per the requirements we anticipate up to 10 images
            for (int i = 0; i < 10; i++) {

                int imageId = context.getResources().getIdentifier("npuzzle" + i, "drawable", "nl.mprog.setup.npuzzle10441913");
                // if there are less than 10 images we will get 0
                if (imageId == 0) {
                    break;
                } else {
                    iconIdList.add(imageId);
                }
            }
        }

        public int getCount() {

            return iconIdList.size();
        }

        public Object getItem(int position) {
            return iconIdList.get(position);
        }

        public long getItemId(int position) {
            return (long) iconIdList.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(iconSize, iconSize));
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(iconIdList.get(position));
            return imageView;
        }

    }

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


    public void goToGame() {
        Intent intent = new Intent(this, GameView.class);
        startActivity(intent);
    }

    // TODO : passing gameboard to game view


//    ------------------ Getters & Setters -------------------

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }
}
