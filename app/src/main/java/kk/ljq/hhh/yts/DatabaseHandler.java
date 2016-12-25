package kk.ljq.hhh.yts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import static kk.ljq.hhh.yts.Constants.KEY_AUTHOR;
import static kk.ljq.hhh.yts.Constants.KEY_ID;
import static kk.ljq.hhh.yts.Constants.KEY_KEY;
import static kk.ljq.hhh.yts.Constants.KEY_NAME;
import static kk.ljq.hhh.yts.Constants.KEY_NAME2;
import static kk.ljq.hhh.yts.Constants.KEY_NAMET;
import static kk.ljq.hhh.yts.Constants.KEY_TRA_ID2;
import static kk.ljq.hhh.yts.Constants.TABLE_NAME;
import static kk.ljq.hhh.yts.Constants.TABLE_NAME2;
import static kk.ljq.hhh.yts.Constants.TABLE_NAME3;
import static kk.ljq.hhh.yts.Constants.TABLE_NAME4;


public class DatabaseHandler extends SQLiteOpenHelper {

    private final ArrayList<movie> movieList = new ArrayList<>();
    private final ArrayList<movie> movoffline = new ArrayList<>();

    private final ArrayList<Trailer> TrraList = new ArrayList<>();
    private final ArrayList<Review> ReviewList = new ArrayList<>();




    public DatabaseHandler(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create our offline
        String CREATE_MOVIES_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME +
                " TEXT, " + Constants.KEY_MOV_IMAGE + " TEXT, "+Constants.KEY_MOV_OV+" TEXT, " +Constants.KEY_MOV_RAT+" TEXT, "+Constants.KEY_V_C+"  TEXT, "+Constants.KEY_TRA_ID+"  TEXT, "+ Constants.KEY_R_D + " TEXT  );";

        db.execSQL(CREATE_MOVIES_TABLE);
        String CREATE_FAV_TABLE = "CREATE TABLE " + TABLE_NAME2 + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME2 +
                " TEXT, " + Constants.KEY_MOV_IMAGE2 + " TEXT, " + Constants.KEY_MOV_OV2 + " TEXT, " + Constants.KEY_MOV_RAT2 + " TEXT, " + Constants.KEY_V_C2 + "  TEXT, " + KEY_TRA_ID2 + "  TEXT, " + Constants.KEY_R_D2 + " TEXT  );";

        db.execSQL(CREATE_FAV_TABLE);

        //create our table
        String CREATE_TRAILER_TABLE = "CREATE TABLE " + TABLE_NAME3 + "("
                + KEY_KEY + " INTEGER PRIMARY KEY, " + KEY_NAMET +
                  " TEXT  );";

        db.execSQL(CREATE_TRAILER_TABLE);
        String CREATE_Review_TABLE = "CREATE TABLE " + TABLE_NAME4 + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_AUTHOR +
                " TEXT, " + Constants.KEY_CONTENT + "  TEXT, " + Constants.KEY_URL +  " TEXT  );";

        db.execSQL(CREATE_Review_TABLE);




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        Log.v("ONUPGRADE", "DROPING THE TABLE AND CREATING A NEW ONE!");

        //create a new one
        onCreate(db);


    }


public void deleteTable(){
    SQLiteDatabase db = this.getWritableDatabase();
    db.delete(Constants.TABLE_NAME, null,
            null);

    db.close();
}

    //add content to table
    public void addOffline(ArrayList<movie>movie){
        for (int i = 0; i < movie.size(); i++) {

            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(Constants.KEY_NAME, movie.get(i).getTitle());
            values.put(Constants.KEY_MOV_IMAGE, movie.get(i).getPoster());
            values.put(Constants.KEY_MOV_OV, movie.get(i).getOv());

            values.put(Constants.KEY_MOV_RAT, movie.get(i).getVa());
            values.put(Constants.KEY_V_C, movie.get(i).getVc());
            values.put(Constants.KEY_TRA_ID, movie.get(i).getIdm());
            values.put(Constants.KEY_R_D, movie.get(i).getRd());


            db.insert(Constants.TABLE_NAME, null, values);
            //db.insert(Constants.TABLE_NAME, null, values);

            Log.i("Wish successfully!", "yeah!!");

            db.close();

        }
    }
    public void addMovies(movie movie) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_NAME2, movie.getTitle());
        values.put(Constants.KEY_MOV_IMAGE2, movie.getPoster());
        values.put(Constants.KEY_MOV_OV2, movie.getOv());

        values.put(Constants.KEY_MOV_RAT2, movie.getVa());
        values.put(Constants.KEY_V_C2, movie.getVc());
        values.put(KEY_TRA_ID2, movie.getIdm());
        values.put(Constants.KEY_R_D2, movie.getRd());




        db.insert(TABLE_NAME2, null, values);
        //db.insert(Constants.TABLE_NAME, null, values);

        Log.i("Wish successfully!", "yeah!!");

        db.close();


    }

    public void addtrailers(ArrayList<Trailer> tra){
        for (int i = 0; i < tra.size(); i++) {

            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(Constants.KEY_KEY, tra.get(i).getName());

            values.put(Constants.KEY_NAMET, tra.get(i).getKey());


            db.insert(Constants.TABLE_NAME3, null, values);
            //db.insert(Constants.TABLE_NAME, null, values);

            Log.i("Wish successfully!", "yeah!!");

            db.close();
    }}
    public void addReviews(ArrayList<Review>review){

        for (int i = 0; i < review.size(); i++) {

            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(Constants.KEY_AUTHOR, review.get(i).getAuthor());



            values.put(Constants.KEY_CONTENT, review.get(i).getContent());
            values.put(Constants.KEY_URL, review.get(i).getUrl());


            db.insert(Constants.TABLE_NAME4, null, values);
            //db.insert(Constants.TABLE_NAME, null, values);

            Log.i("Wish successfully!", "yeah!!");

            db.close();

        }
    }

    //Get DATA
    public ArrayList<movie> getMoviesOffline() {

        movoffline.clear();

        // String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        // Cursor cursor = db.rawQuery(selectQuery, null);

        Cursor cursor = db.query(TABLE_NAME, new String[]{Constants.KEY_ID, Constants.KEY_NAME, Constants.KEY_MOV_IMAGE, Constants.KEY_MOV_OV,Constants.KEY_MOV_RAT,Constants.KEY_V_C,Constants.KEY_TRA_ID,Constants.KEY_R_D},null,null, null,null,null);

        //loop through cursor
        if (cursor.moveToFirst()) {

            do {
                Log.i("first line in do","nn");

                movie movie = new movie();
                movie.setTitle(cursor.getString(cursor.getColumnIndex(Constants.KEY_NAME)));
                movie.setPoster(cursor.getString(cursor.getColumnIndex(Constants.KEY_MOV_IMAGE)));
                movie.setOv(cursor.getString(cursor.getColumnIndex(Constants.KEY_MOV_OV)));
                movie.setVa(cursor.getString(cursor.getColumnIndex(Constants.KEY_MOV_RAT)));
                movie.setVc(cursor.getString(cursor.getColumnIndex(Constants.KEY_V_C)));
                movie.setIdm(cursor.getString(cursor.getColumnIndex(Constants.KEY_TRA_ID)));
                movie.setRd(cursor.getString(cursor.getColumnIndex(Constants.KEY_R_D)));








                movoffline.add(movie);
                Log.i("last in do","ss");

            } while (cursor.moveToNext());
        }


        cursor.close();
        db.close();

        return movoffline;

    }

    public ArrayList<movie> getMovies() {

        movieList.clear();

       // String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
       // Cursor cursor = db.rawQuery(selectQuery, null);

        Cursor cursor = db.query(TABLE_NAME2, new String[]{Constants.KEY_ID, Constants.KEY_NAME2, Constants.KEY_MOV_IMAGE2, Constants.KEY_MOV_OV2,Constants.KEY_MOV_RAT2,Constants.KEY_V_C2, KEY_TRA_ID2,Constants.KEY_R_D2},null,null, null,null,null);

        //loop through cursor
        if (cursor.moveToFirst()) {

            do {
                Log.i("first line in do","nn");

                movie movie = new movie();
                movie.setTitle(cursor.getString(cursor.getColumnIndex(Constants.KEY_NAME2)));
                movie.setPoster(cursor.getString(cursor.getColumnIndex(Constants.KEY_MOV_IMAGE2)));
                movie.setOv(cursor.getString(cursor.getColumnIndex(Constants.KEY_MOV_OV2)));
                movie.setVa(cursor.getString(cursor.getColumnIndex(Constants.KEY_MOV_RAT2)));
                movie.setVc(cursor.getString(cursor.getColumnIndex(Constants.KEY_V_C2)));
                movie.setIdm(cursor.getString(cursor.getColumnIndex(KEY_TRA_ID2)));
                movie.setRd(cursor.getString(cursor.getColumnIndex(Constants.KEY_R_D2)));








                movieList.add(movie);
                Log.i("last in do","ss");

            } while (cursor.moveToNext());
        }


        cursor.close();
        db.close();

        return movieList;

    }

    public ArrayList<Trailer> getTrailers() {

        TrraList.clear();

        // String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        // Cursor cursor = db.rawQuery(selectQuery, null);

        Cursor cursor = db.query(TABLE_NAME3, new String[]{Constants.KEY_ID, Constants.KEY_KEY, Constants.KEY_NAMET},null,null, null,null,null);

        //loop through cursor
        if (cursor.moveToFirst()) {

            do {
                Log.i("first line in do","nn");

                Trailer trailer = new Trailer();
                trailer.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_KEY)));

                trailer.setKey(cursor.getString(cursor.getColumnIndex(Constants.KEY_NAMET)));








                TrraList.add(trailer);
                Log.i("last in do","ss");

            } while (cursor.moveToNext());
        }


        cursor.close();
        db.close();

        return TrraList;

    }

    public ArrayList<Review> getReviews() {

        ReviewList.clear();

        // String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        // Cursor cursor = db.rawQuery(selectQuery, null);

        Cursor cursor = db.query(TABLE_NAME4, new String[]{Constants.KEY_ID, Constants.KEY_AUTHOR, Constants.KEY_CONTENT,Constants.KEY_URL},null,null, null,null,null);

        //loop through cursor
        if (cursor.moveToFirst()) {

            do {
                Log.i("first line in do","nn");

                Review movie = new Review();
                movie.setAuthor(cursor.getString(cursor.getColumnIndex(Constants.KEY_AUTHOR)));

                movie.setContent(cursor.getString(cursor.getColumnIndex(Constants.KEY_CONTENT)));
                movie.setUrl(cursor.getString(cursor.getColumnIndex(Constants.KEY_URL)));









                ReviewList.add(movie);
                Log.i("last in do","ss");

            } while (cursor.moveToNext());
        }


        cursor.close();
        db.close();

        return ReviewList;

    }













    public void deleteMovie(String name) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME2, Constants.KEY_NAME2 + " = ? ",
                new String[]{ String.valueOf(name)});

        db.close();

    }


    public boolean isExist(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.TABLE_NAME + " WHERE "+ Constants.KEY_ID+" = " + id, null);
        if (cursor.getCount() !=0) {db.close();
            return true;}
        else {db.close();

            return false;

        }


          /*  Cursor cursor=db.rawQuery("select "+Constants.KEY_NAME+" from "+ TABLE_NAME+" where "+Constants.KEY_NAME+"="+name,null);
        if(cursor.getCount()>=0){db.close();
            return true;}
        else {db.close();

            return false;

        }
*/

    }
    // Getting single contact
    boolean getName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME2, new String[] { KEY_ID,
                        KEY_NAME2, KEY_TRA_ID2 }, KEY_NAME2 + "=?",
                new String[] { name }, null, null, null, null);
        if (cursor.getCount() !=0) {db.close();
            return true;}
        else {db.close();

            return false;

        }}




}
