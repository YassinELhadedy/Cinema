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


public class CustomListViewAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Review> books;
    private static LayoutInflater inflater = null;
    String photo;


    public CustomListViewAdapter(Context context, ArrayList<Review> data,String photo){

        mContext = context;
        books = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.photo=photo;

    }


    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (convertView == null){

            view = inflater.inflate(R.layout.list_row, null);

            TextView title = (TextView) view.findViewById(R.id.title);
            TextView author = (TextView) view.findViewById(R.id.author);
            TextView pages = (TextView) view.findViewById(R.id.pages);
            ImageView image = (ImageView) view.findViewById(R.id.list_image);



            title.setText(books.get(position).getContent());
          //  author.setText((books.get(position).getAuthor()));
           // pages.setText((books.get(position).getUrl()));
           // image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.blue_swirl));
            Picasso.with(mContext).load(photo).fit().centerCrop()
                    .into(image );




        }


        return view;
    }
}
