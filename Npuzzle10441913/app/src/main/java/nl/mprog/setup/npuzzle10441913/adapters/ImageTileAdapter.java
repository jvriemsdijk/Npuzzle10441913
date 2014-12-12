package nl.mprog.setup.npuzzle10441913.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import nl.mprog.setup.npuzzle10441913.model.GameBoard;
import nl.mprog.setup.npuzzle10441913.util.BitmapUtil;

/**
 * Created by joeyvanriemsdijk on 02/12/14.
 */
public class ImageTileAdapter extends BaseAdapter {

    private Context context;
    private GameBoard gameBoard;
    private List<Bitmap> imageTiles;

    public ImageTileAdapter(Context c, GameBoard gb) {

        context = c;
        gameBoard = gb;
        imageTiles = BitmapUtil.createGameTileListFromBitmap(context.getResources(), gameBoard.getImageId(), gameBoard.getDifficulty());

    }


    @Override
    public int getCount() {
        return imageTiles.size();
    }

    @Override
    public Object getItem(int position) {
        return imageTiles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        } else {
            imageView = (ImageView) convertView;
        }
        int tilePos = gameBoard.getCurrentState().get(position) - 1;
        imageView.setImageBitmap(imageTiles.get(tilePos));
        return imageView;
    }

    /**
     * Method to be used when the loaded images are no longer needed. Will flush the loaded
     * BitMaps from memory.
     */
    public void recycleImages() {
        for (Bitmap imageTile : imageTiles) {
            imageTile.recycle();
        }
    }


}
