package kk.ljq.hhh.yts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Elhadedy on 12/1/2016.
 */

public class OfflineFragment extends Fragment {
    GridView grid;
    private MovieLisnter movieLisnter;
    void setOfflineLisnter(MovieLisnter movieLisnter){this.movieLisnter=movieLisnter;}
    // ArrayList<movie> mov_list=new ArrayList<movie>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view=inflater.inflate(R.layout.grid,container,false);
        grid = (GridView) view.findViewById(R.id.gridlayout);

        ImageAdapter imageAdapter = new ImageAdapter(view.getContext(),getsOffline());

        grid.setAdapter(imageAdapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getActivity(), "("+getsOffline().get(position).getTitle()+" "+"Movie"+")", Toast.LENGTH_SHORT).show();



               /* // Sending image id to Fullmovie Activity
                Intent i = new Intent(getActivity(), SecondActivity.class);
                //passing array index
                i.putExtra("id", position);
                i.putExtra("address",getsOffline().get(position).getPoster());
                i.putExtra("title",getsOffline().get(position).getTitle());
                i.putExtra("overview",getsOffline().get(position).getOv());
                i.putExtra("date",getsOffline().get(position).getRd());
                i.putExtra("average",getsOffline().get(position).getVa());
                i.putExtra("count",getsOffline().get(position).getVc());
                i.putExtra("id_movie",getsOffline().get(position).getIdm());


                startActivity(i);
                // Toast.makeText(getActivity(),get_move(),Toast.LENGTH_SHORT).show();
*/                movieLisnter.SetMovieSelected(getsOffline().get(position));


            }
        });

        return view;
    }

    public ArrayList<movie> getsOffline (){
        DatabaseHandler c=new DatabaseHandler(getActivity());

        ArrayList<movie> z=new ArrayList<movie>();

        z=c.getMoviesOffline();

        c.close();
        return z;

    }
}
