package kk.ljq.hhh.yts;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.NOTIFICATION_SERVICE;
import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by Elhadedy on 11/27/2016.
 */

public class DetailsFragments extends Fragment {
    TextView textView;
    TextView t1;
    TextView t2;
    RatingBar rb;
    // private CustomListViewAdapter customListViewAdapter;
    String link;
    CheckBox btn;
    String add;
    String title;
    String date;
    String id_movie;
    int position;
    String overv;
    float Rat;
    String count;
  ArrayList<Trailer> tra_list=new ArrayList<Trailer>();
   ArrayList<Review>rev_list=new ArrayList<Review>();
    Button btr;
    Button brev;

    ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();
    private long fileSize = 0;
    public View view;
String data;





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.detailsfragment,container,false);
     //   tra_list=getTrailer();
       // rev_list=getREview();
        setConten();
        starButton();
       downloadtrailres();
        downloadReviews();
        handleButton();
//Toast.makeText(getActivity(),""+getTrailer().get(0).getKey(),LENGTH_SHORT).show();




        return view;
    }
    // checking how much file is downloaded and updating the filesize
    public int doOperation() {
        //The range of ProgressDialog starts from 0 to 10000
        try {
//---simulate doing some work---
            Thread.sleep(100);
            fileSize=fileSize+25*fileSize;
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        return (int) ++fileSize;
    }//end of doOperation

    public void saveData(){

        // MAKE A FAVORITE
        DatabaseHandler dba=new DatabaseHandler(getActivity());
        movie moviestore=new movie();
        moviestore.setId(position);
        moviestore.setTitle(title);
        moviestore.setPoster(add);
        moviestore.setIdm(id_movie);
        moviestore.setRd(date);
        moviestore.setOv(overv);
        Log.i("over",moviestore.getOv());
        moviestore.setVa(String.valueOf(Rat));
        moviestore.setVc(count);

        dba.addMovies(moviestore);
        dba.close();

    }

    public void delete(String name){
        DatabaseHandler dba=new DatabaseHandler(getActivity());
        dba.deleteMovie(name);

    }
    public boolean isExist (String id){

        DatabaseHandler dba=new DatabaseHandler(getActivity());
        boolean b=dba.getName(id);
        return b;

    }

    private void setConten(){
    textView=(TextView) view.findViewById(R.id.t);
    t1=(TextView) view.findViewById(R.id.t1);
    t2=(TextView)view. findViewById(R.id.t2);
    rb=(RatingBar)view.findViewById(R.id.myRatingBar);

    Bundle getsbunle=getArguments();
    position = getsbunle.getInt("id");
    add =          getsbunle.getString("address");
    title =        getsbunle.getString("title");
    overv = getsbunle.getString("overview");
    date=          getsbunle.getString("date");
    Rat= Float.valueOf(getsbunle.getString("average"));
    count=  getsbunle.getString("count");
    id_movie=      getsbunle.getString("id_movie");
    Log.i("id",id_movie);




    ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(title);


    textView.setText(title);
    t1.setText(date+'\n'+count);
    t2.setText(overv);
    rb.setRating(Rat);


    final ImageView imageView = (ImageView) view.findViewById(R.id.full_image_view);
    Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185/"+add).into(imageView );
    link="http://image.tmdb.org/t/p/w185/"+add;

    // imageView.setImageBitmap(MainActivity.ImageAdapter.bm.get(position)) ;
    //animate
    imageView.animate().rotationXBy(1440f).setDuration(2500);
}
    private void starButton(){
        btn=(CheckBox) view.findViewById(R.id.bt);



        if(isExist(title)==true){btn.setChecked(true);}




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                     data=tra_list.get(0).getKey();
                }catch (Exception e){data="";}

                if (((CheckBox)view).isChecked()) {
                    final Context context = getActivity();
                    final NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);
                    final Notification notification = new NotificationCompat.Builder(context)
                            .setSmallIcon(R.mipmap.ic_launcher) // Needed for the notification to work/show!!
                            .setContentTitle(title)
                            .setContentText(date + '\n' + "Mark as Love")
                            .setPriority(NotificationCompat.PRIORITY_MAX)
                            .setAutoCancel(true)
                            .setDefaults(Notification.DEFAULT_VIBRATE)
                            .setDefaults(Notification.DEFAULT_SOUND)
                            .setContentIntent(
                                    PendingIntent.getActivity(
                                            context,
                                            0,
                                            new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v="+data)),
                                            PendingIntent.FLAG_UPDATE_CURRENT)
                            )

                            .build();
                    final int notifId = 1337;
                    notificationManager.notify(0, notification);
                    final RemoteViews contentView = notification.contentView;
                    final int iconId = android.R.id.icon;
                    Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185/" + add).into(contentView, iconId, 0, notification);
                    Toast.makeText(getActivity(),"Like movie",
                            LENGTH_SHORT).show();
                    Toast.makeText(getActivity(),"Like movie",
                            LENGTH_SHORT).show();

                    //save data
                    saveData();
                   try {
                       checkFAV(t);
                   }catch (Exception E){}

                    Toast.makeText(getActivity(),"Movie saved",
                            LENGTH_SHORT).show();


                }
                else{Toast.makeText(getActivity(),"Unlike movie",
                        LENGTH_SHORT).show();
                    NotificationManager nm=(NotificationManager)getContext().getSystemService(NOTIFICATION_SERVICE);
                    nm.cancel(0);


                    delete(title);
                    try {
                        checkFAV(t);
                    }catch (Exception E){}

                }



            }
        });
    }

    private void downloadtrailres(){
        // download trailers
        DownloadTrailers downloadTrailers=new DownloadTrailers();
        downloadTrailers.execute("http://api.themoviedb.org/3/movie/"+id_movie+"/videos?api_key=d84728f2cfb003c8298b599386b39e34");
        downloadTrailers.setFetchTrarlelTaskCallbacks(new DownloadTrailers.FetchTrailer() {
            @Override
            public void onPostExecute(String tra) {tra_list.clear();
                try {

                    JSONObject jsonObject=new JSONObject(tra);
                    String Trainfo=jsonObject.getString("results");
                    JSONArray jsonArray=new JSONArray(Trainfo);
                    for(int i=0;i<jsonArray.length();i++) {

                        Trailer trailer=new Trailer();
                        JSONObject jsonpart = jsonArray.getJSONObject(i);


                        trailer.setKey(jsonpart.getString("key"));
                        trailer.setName(jsonpart.getString("name"));

                        tra_list.add(trailer);




                    }

                    //  ImageAdapter imageAdapter = new ImageAdapter(MainActivity.this,mov_list);
                    //  grid.setAdapter(imageAdapter);





                }catch (Exception e){}

                //progressBar.dismiss();



            }

        });
    }
    private void downloadReviews(){

        DownloadReviews downloadReviews=new DownloadReviews();
        downloadReviews.execute("http://api.themoviedb.org/3/movie/"+id_movie+"/reviews?api_key=d84728f2cfb003c8298b599386b39e34");
        downloadReviews.setFetchReviewTaskCallbacks(new DownloadReviews.FetchReview() {
            @Override
            public void onPostExecute(String rev) {rev_list.clear();
                try {

                    JSONObject jsonObject=new JSONObject(rev);
                    String Revnfo=jsonObject.getString("results");
                    JSONArray jsonArray=new JSONArray(Revnfo);
                    for(int i=0;i<jsonArray.length();i++) {

                        Review review=new Review();
                        JSONObject jsonpart = jsonArray.getJSONObject(i);


                        review.setAuthor(jsonpart.getString( "author"));
                        review.setContent(jsonpart.getString("content"));
                        review.setUrl(jsonpart.getString("url"));

                        rev_list.add(review);




                    }
                    //  ImageAdapter imageAdapter = new ImageAdapter(MainActivity.this,mov_list);
                    //  grid.setAdapter(imageAdapter);





                }catch (Exception e){}
                //progressBar.dismiss();


            }
        });
    }
    private void handleButton(){
        // handle buttons
        btr=(Button)view.findViewById(R.id.btr);
        brev=(Button)view.findViewById(R.id.brev);
        btr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // creating progress bar dialog
                progressBar = new ProgressDialog(view.getContext());
                progressBar.setCancelable(true);
                progressBar.setMessage("File downloading ...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressBar.setProgress(0);
                progressBar.setMax(100);
                progressBar.show();
                //reset progress bar and filesize status
                progressBarStatus = 0;
                fileSize = 0;

                new Thread(new Runnable() {
                    public void run() {
                        while (progressBarStatus < 100) {
                            // performing operation
                            progressBarStatus = doOperation();
                            try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
                            // Updating the progress bar
                            progressBarHandler.post(new Runnable() {
                                public void run() {
                                    progressBar.setProgress(progressBarStatus);
                                }
                            });
                        }
                        // performing operation if file is downloaded,
                        if (progressBarStatus >= 100) {
                            // sleeping for 1 second after operation completed
                            try {Thread.sleep(200);} catch (InterruptedException e) {e.printStackTrace();}
                            // close the progress bar dialog
                            progressBar.dismiss();
                            //when finish progress
                            Intent i = new Intent(getActivity(), ThridActivity.class);
                            i.putExtra("Trailers", tra_list);
                            i.putExtra("image", link);
                            // i.putExtra("idmovie",id_movie);
                            startActivity(i);
                        }
                    }
                }).start();
            }
        });

        brev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // creating progress bar dialog
                progressBar = new ProgressDialog(view.getContext());
                progressBar.setCancelable(true);
                progressBar.setMessage("File downloading ...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressBar.setProgress(0);
                progressBar.setMax(100);
                progressBar.show();
                //reset progress bar and filesize status
                progressBarStatus = 0;
                fileSize = 0;

                new Thread(new Runnable() {
                    public void run() {
                        while (progressBarStatus < 100) {
                            // performing operation
                            progressBarStatus = doOperation();
                            try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
                            // Updating the progress bar
                            progressBarHandler.post(new Runnable() {
                                public void run() {
                                    progressBar.setProgress(progressBarStatus);
                                }
                            });
                        }
                        // performing operation if file is downloaded,
                        if (progressBarStatus >= 100) {
                            // sleeping for 1 second after operation completed
                            try {Thread.sleep(200);} catch (InterruptedException e) {e.printStackTrace();}
                            // close the progress bar dialog
                            progressBar.dismiss();
                            //when finish progress
                            Intent i=new Intent(getActivity(),ThridActivity.class);
                            i.putExtra("Review",rev_list);
                            i.putExtra("photo",link);
                            i.putExtra("idfilms",id_movie);
                            startActivity(i);
                        }
                    }
                }).start();



            }
        });
    }
   private void checkFAV(boolean t){ if(t==true){

       MainActivity mainActivity = (MainActivity) getActivity();
     mainActivity.func();
}else return;

}
    public static boolean t=false;

}
