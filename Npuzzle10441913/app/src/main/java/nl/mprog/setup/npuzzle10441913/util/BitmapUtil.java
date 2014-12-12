package nl.mprog.setup.npuzzle10441913.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.mprog.setup.npuzzle10441913.R;

/**
 * Created by joeyvanriemsdijk on 06/12/14.
 */
public class BitmapUtil {

    /**
     * Method taken from android dev site to efficiently load large bitmaps.
     *
     * @param res
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }


    /**
     * Method taken from android dev site to calculate the correct in sample size to efficiently
     * load large bitmaps.
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    /**
     * Method to create a list of tiles from the given image for the game board. The number of
     * tile made is equal to the difficuty squared.
     *
     * @param resources
     * @param imageId
     * @param difficulty
     * @return List of image tiles
     */
    public static List<Bitmap> createGameTileListFromBitmap(Resources resources, long imageId, int difficulty) {
        List<Bitmap> tileList = new ArrayList<Bitmap>();

        int width = resources.getDisplayMetrics().widthPixels;
        int height = resources.getDisplayMetrics().heightPixels;
        Bitmap image = BitmapUtil.decodeSampledBitmapFromResource(resources, (int) imageId, width, height);
        Bitmap scaledImage;
        int totalTiles = difficulty * difficulty;
        tileList = new ArrayList<Bitmap>(totalTiles);

        // determine aspect ratios
        double screenAspectRatio = (double) height / (double) width;
        double imageHeight = image.getHeight();
        double imageWidth = image.getWidth();
        double imageAspectRatio = imageHeight / imageWidth;

        // scale the image, and recycle the original
        if (screenAspectRatio > imageAspectRatio) {
            scaledImage = Bitmap.createScaledBitmap(image, (int) (imageAspectRatio * height), width, false);
        } else {
            scaledImage = Bitmap.createScaledBitmap(image, height, (int) (imageAspectRatio * width), false);
        }
        image.recycle();

        // calculate the tile sizes
        int tileHeight = scaledImage.getHeight() / difficulty;
        int tileWidth = scaledImage.getWidth() / difficulty;

        // creating the
        int col = 0;
        int row = 0;
        for (int i = 0; i < (totalTiles); i++) {

            if (totalTiles - i == 1) {
                tileList.add(Bitmap.createBitmap(tileWidth, tileHeight, Bitmap.Config.ARGB_8888));
            } else {
                tileList.add(Bitmap.createBitmap(scaledImage, row * tileWidth, col * tileHeight, tileWidth, tileHeight));
            }
            if ((row + 1) == difficulty) {
                // end of col
                row = 0;
                col += 1;
            } else {
                // still in col
                row += 1;
            }
        }

        scaledImage.recycle();


        return tileList;
    }

    /**
     * Method to create a list of icons to be used in the image selection.
     *
     * @param resources
     * @return List of icon sized bitmaps
     */
    public static List<Integer> createImageSelectionIcons(Resources resources, int iconSize) {
        List<Integer> iconIdList = new ArrayList<Integer>();

        for (int i = 0; i < 10; i++) {

            int imageId = resources.getIdentifier("puzzle_" + i, "drawable", "nl.mprog.setup.npuzzle10441913");
            // if there are less than 10 images we will get 0
            if (imageId == 0) {
                break;
            } else {
                iconIdList.add(imageId);
            }
        }

        return iconIdList;
    }




}
