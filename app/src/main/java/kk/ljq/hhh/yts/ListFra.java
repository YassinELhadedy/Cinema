package kk.ljq.hhh.yts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Elhadedy on 11/27/2016.
 */

public class ListFra extends Fragment {
    ListView lv;
    ArrayList<Trailer> tra=null;
    ArrayList<Review>rev=null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.listlayour,container,false);

        //made



        Bundle getsbunle=getArguments();
        tra=getsbunle.getParcelableArrayList("Trailers");
        String image=getsbunle.getString("image");
      //  String idmovie=getsbunle.getString("idmovie");

        Bundle getsbunle2=getArguments();
        rev=getsbunle2.getParcelableArrayList("Review");
        String photo=getsbunle2.getString("photo");
        final String idfilms=getsbunle2.getString("idfilms");

        lv=(ListView)view.findViewById(R.id.list_item);


        if(tra==null){CustomListViewAdapter customListViewAdapter=new CustomListViewAdapter(getActivity(),rev,photo);
            lv.setAdapter(customListViewAdapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.themoviedb.org/movie/"+idfilms+"/reviews"));
                    startActivity(intent);

                    Toast.makeText(getActivity(),rev.get(i).getAuthor(),Toast.LENGTH_SHORT).show();
                }
            });



        }
        else {TrailerAdapter trailerAdapter=new TrailerAdapter(getActivity(),tra,image);
            lv.setAdapter(trailerAdapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v="+tra.get(i).getKey()));
                startActivity(intent);
                Toast.makeText(getActivity(),tra.get(i).getName(),Toast.LENGTH_SHORT).show();
            }
        });

        }


        return view;
    }


}
