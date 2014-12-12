package nl.mprog.setup.npuzzle10441913.adapters;

/**
 * Created by joeyvanriemsdijk on 02/12/14.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nl.mprog.setup.npuzzle10441913.util.BitmapUtil;

/**
 * Custom image adapter for the gridview. Scans the drawable dir for up to 10 images with the
 * naming: 'npuzzleX' where X is a number (single digit).
 */
public class ImageSelectionAdapter extends BaseAdapter {

    private Context context;
    private List<Bitmap> iconList;
    private List<Integer> iconIdList;
    private int iconSize;


    public ImageSelectionAdapter(Context c) {
        context = c;
        iconSize = context.getResources().getDisplayMetrics().widthPixels / 3;

        iconIdList = BitmapUtil.createImageSelectionIcons(context.getResources(), iconSize);
        loadImageCashe();
    }

    private void loadImageCashe() {
        iconList = new ArrayList<Bitmap>();
        for (Integer id : iconIdList) {
            iconList.add(BitmapUtil.decodeSampledBitmapFromResource(context.getResources(), id, iconSize, iconSize));
        }
    }


    public void clearImageCashe() {
        for (Bitmap icon : iconList) {
            icon.recycle();
        }
    }

    @Override
    public int getCount() {

        return iconIdList.size();
    }
    @Override
    public Object getItem(int position) {
        return iconList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return iconIdList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(iconSize, iconSize));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageBitmap(iconList.get(position));
        return imageView;

    }

}
