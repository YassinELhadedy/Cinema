package kk.ljq.hhh.yts;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MovieLisnter {
    GridView grid;
    boolean mIstwo=false;
  //  boolean mIsfav=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //made

       if(isOnline()==true) {//deleteDB();

          DatabaseHandler databaseHandler=new DatabaseHandler(MainActivity.this);
           databaseHandler.deleteTable();


           MainFragment mainFragment = new MainFragment();
           //set the Activity to be listener
           mainFragment.setMovieLisnter(this);
           getSupportFragmentManager().beginTransaction().add(R.id.fragment1, mainFragment, "").commit();
       }
        else if(isOnline()==false) {
          // DetailsFragments.tra_list=getTrailer();
           //DetailsFragments.rev_list=getREview();

            Toast.makeText(MainActivity.this,"no connection",Toast.LENGTH_SHORT).show();
           OfflineFragment OFFFragment=new OfflineFragment();
           OFFFragment.setOfflineLisnter(this);

           getSupportFragmentManager().beginTransaction().add(R.id.fragment1,OFFFragment,"").commit();
       }
        //two bin
        if (null!=findViewById(R.id.fragment2)){mIstwo=true;}
       // if(null!=findViewById(R.id.fragment2)){mIsfav=true;}




    }

        //grid = (GridView) findViewById(R.id.gridlayout);



    @Override
    public void onBackPressed() {


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this,PrefsActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id==R.id.refresh_settings){
//new DownloadMovies().execute("http://api.themoviedb.org/3/movie/popular?api_key=d84728f2cfb003c8298b599386b39e34");
            Intent refresh = new Intent(this, MainActivity.class);
            startActivity(refresh);//Start the same Activity
            finish(); //finish Activity.


            return true;
        }
        else if (id==R.id.favorite){
//new DownloadMovies().execute("http://api.themoviedb.org/3/movie/popular?api_key=d84728f2cfb003c8298b599386b39e34");
            FavoriteFragment FAVFragment=new FavoriteFragment();
            //set the Activity to be listener
            FAVFragment.setFavLisnter(this);

            getSupportFragmentManager().beginTransaction().add(R.id.fragment1,FAVFragment,"").commit();


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public boolean isOnline() {

        Runtime runtime = Runtime.getRuntime();
        try {

            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);

        } catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }

    @Override
    public void SetMovieSelected(movie  movie) {
        //one pane case
        if (!mIstwo){
              Toast.makeText(this, "("+movie.getTitle()+" "+"Movie"+")", Toast.LENGTH_SHORT).show();



            // Sending image id to Fullmovie Activity
                Intent i = new Intent(this, SecondActivity.class);
                //passing array index
              //  i.putExtra("id", position);
                i.putExtra("address",movie.getPoster());
                i.putExtra("title",movie.getTitle());
                i.putExtra("overview",movie.getOv());
                i.putExtra("date",movie.getRd());
                i.putExtra("average",movie.getVa());
                i.putExtra("count",movie.getVc());
                i.putExtra("id_movie",movie.getIdm());


                startActivity(i);



        }else {
            //two pane case
            DetailsFragments detailsFragments=new DetailsFragments();

            Bundle i=new Bundle();
            i.putString("address",movie.getPoster());
            i.putString("title",movie.getTitle());
            i.putString("overview",movie.getOv());
            i.putString("date",movie.getRd());
            i.putString("average",movie.getVa());
            i.putString("count",movie.getVc());
            i.putString("id_movie",movie.getIdm());
            detailsFragments.setArguments(i);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment2,detailsFragments,"").commit();



        }


    }

    public void func(){

        FavoriteFragment FAVFragment=new FavoriteFragment();
        //set the Activity to be listener
        FAVFragment.setFavLisnter(this);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment1,FAVFragment,"").commit();

    }
}



