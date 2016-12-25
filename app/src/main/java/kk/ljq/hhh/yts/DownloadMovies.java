package kk.ljq.hhh.yts;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Elhadedy on 11/23/2016.
 */

public  class DownloadMovies extends AsyncTask<String,Void,String> {
    private Context mContext;

    DownloadMovies(Context c) {
        mContext = c;
    }

   static ProgressDialog progressBar;
    public interface FetchMovie {
        public void onPostExecute(String mov);
    }
    private FetchMovie fetchMovie;
    public void setFetchMoviesTaskCallbacks(FetchMovie fetchMovie) {
        this.fetchMovie = fetchMovie;
    }

    @Override
    protected void onPostExecute(String s) {

        if (fetchMovie != null) fetchMovie.onPostExecute(s);

    }

    @Override
    protected String doInBackground(String... strings) {
        String res="";
        URL url;
        HttpURLConnection urlConnection=null;
        try {

            url=new URL(strings[0]);
            urlConnection =(HttpURLConnection)url.openConnection();
            InputStream in =urlConnection.getInputStream();
            InputStreamReader reader=new InputStreamReader(in);
            int data = reader.read();
            while (data!=-1){
                char curr=(char) data;
                res+=curr;
                data=reader.read();
            }
            Log.i("sxx",res);
            return  res;
        }catch (Exception e){}
        return null;
    }

    @Override
    protected void onPreExecute() {
        progressBar=new ProgressDialog(mContext);
        progressBar.setMessage("download movies....");
        progressBar.setProgressStyle(progressBar.STYLE_SPINNER);
        progressBar.show();
    }
}