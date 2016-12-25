package kk.ljq.hhh.yts;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Elhadedy on 11/27/2016.
 */

public class MainFragment extends Fragment {
    GridView grid;

    ArrayList<movie> mov_list=new ArrayList<>();
    private MovieLisnter movieLisnter;
    public View view;
    void setMovieLisnter(MovieLisnter movieLisnter){this.movieLisnter=movieLisnter;}
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



         view=inflater.inflate(R.layout.grid,container,false);

        downloadMovies();




        return view;
    }
    private void SaveIntoDb(ArrayList<movie>mov_list){
       /* DataBaseOffline dataBaseOffline=new DataBaseOffline(getActivity());
        dataBaseOffline.addMovies(mov_list);
        dataBaseOffline.close();
    */
        DatabaseHandler dataBaseOffline=new DatabaseHandler(getActivity());

        dataBaseOffline.addOffline(mov_list);
        dataBaseOffline.close();
    }
    public String getPerference(){
        String listPrefs;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        listPrefs = prefs.getString("listpref", "popular");
        Log.i("this sgared ",listPrefs);
        return listPrefs;


    }
    public void downloadMovies(){
        grid = (GridView) view.findViewById(R.id.gridlayout);
        DownloadMovies ab=new DownloadMovies(getActivity());
        ab.setFetchMoviesTaskCallbacks(new DownloadMovies.FetchMovie() {
            @Override
            public void onPostExecute(String s) {

                try {
                    JSONObject jsonObject=new JSONObject(s);
                    String movieinfo=jsonObject.getString("results");
                    JSONArray jsonArray=new JSONArray(movieinfo);
                    for(int i=0;i<jsonArray.length();i++) {

                        movie mov=new movie();
                        JSONObject jsonpart = jsonArray.getJSONObject(i);
                        mov.setPoster(jsonpart.getString("poster_path"));
                        mov.setOv(jsonpart.getString("overview"));
                        mov.setRd(jsonpart.getString("release_date"));
                        mov.setTitle(jsonpart.getString("title"));
                        mov.setVc(jsonpart.getString("vote_count"));
                        mov.setVa(jsonpart.getString("vote_average"));
                        mov.setIdm(jsonpart.getString("id"));
                        mov_list.add(mov);

                    }

                    ImageAdapter imageAdapter = new ImageAdapter(view.getContext(),mov_list);

                    grid.setAdapter(imageAdapter);

                }catch (Exception e){}

                DownloadMovies.  progressBar.dismiss();
                SaveIntoDb(mov_list);


            }
        });
        ab.execute("http://api.themoviedb.org/3/movie/" + getPerference() + "?api_key=d84728f2cfb003c8298b599386b39e34");
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                movieLisnter.SetMovieSelected(mov_list.get(position));
            }
        });
    }


}


