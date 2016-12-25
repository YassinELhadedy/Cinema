package kk.ljq.hhh.yts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Elhadedy on 11/23/2016.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    ImageView imageView;
ArrayList<movie>poster;



    public ImageAdapter(Context c, ArrayList<movie>a) {
        mContext = c;
        poster=a;
    }


    @Override
    public int getCount() {
        return poster.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        grid = inflater.inflate(R.layout.imagelayout, null);
        TextView textView = (TextView) grid.findViewById(R.id.grid_text);
        ImageView imageView = (ImageView) grid.findViewById(R.id.grid_image);

        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185/"+poster.get(position).getPoster()).into(imageView);
        textView.setText(poster.get(position).getTitle());
        // imageView.setImageBitmap(bm.get(position));
        //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);







        return grid;
    }

}