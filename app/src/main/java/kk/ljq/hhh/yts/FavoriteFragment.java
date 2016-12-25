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
 * Created by Elhadedy on 11/30/2016.
 */

public class FavoriteFragment extends Fragment  {
    GridView grid;
    private MovieLisnter movieLisnter;
    void setFavLisnter(MovieLisnter movieLisnter){this.movieLisnter=movieLisnter;}
    ImageAdapter imageAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.grid,container,false);
        grid = (GridView) view.findViewById(R.id.gridlayout);

   DetailsFragments.t=true;




        return view;
    }

    private   void loadFavs() {
        imageAdapter = new ImageAdapter(getActivity(),gets());

        grid.setAdapter(imageAdapter);
        //  Toast.makeText(getActivity(),gets().get(0).getVa(),Toast.LENGTH_SHORT).show();
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
               Toast.makeText(getActivity(), "("+gets().get(position).getTitle()+" "+"Movie"+")", Toast.LENGTH_SHORT).show();





                movieLisnter.SetMovieSelected(gets().get(position));




            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFavs();
    }

    public ArrayList<movie> gets (){
        DatabaseHandler c=new DatabaseHandler(getActivity());



        ArrayList<movie> z=new ArrayList<movie>();

        z=c.getMovies();

        c.close();
        return z;

    }


}
