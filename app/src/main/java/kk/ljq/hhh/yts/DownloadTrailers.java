package kk.ljq.hhh.yts;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Elhadedy on 11/25/2016.
 */

public class DownloadTrailers extends AsyncTask<String,Void,String> {
    private Context mContext;

    DownloadTrailers(Context c) {
        mContext = c;
    }
    DownloadTrailers(){}
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

    public interface FetchTrailer {
        public void onPostExecute(String tra);
    }

    private FetchTrailer fetchTrailer;
    public void setFetchTrarlelTaskCallbacks(FetchTrailer fetchTrailer) {
        this.fetchTrailer = fetchTrailer;
    }

    @Override
    protected void onPostExecute(String s) {
        if (fetchTrailer != null) fetchTrailer.onPostExecute(s);
    }

}
