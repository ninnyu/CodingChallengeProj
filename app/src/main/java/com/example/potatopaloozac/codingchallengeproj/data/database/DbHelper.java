package com.example.potatopaloozac.codingchallengeproj.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Creates database of movies
 */
public class DbHelper extends SQLiteOpenHelper implements IDbHelper {

    public static final String MOVIEDATABASE = "MovieDatabase";
    public static final String MOVIES = "Movies";
    public static final String KEY_ID = "key_id";
    public static final String TITLE = "title";
    public static final String YEAR = "year";
    public static final String DIRECTOR = "director";
    public static final String GENRE = "genre";

    public static final int VERSION = 1;

    public DbHelper(Context context) {
        super(context, MOVIEDATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

         String CREATE_TABLE = "CREATE TABLE " + MOVIES + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE + " TEXT, " + YEAR + " INT, " + DIRECTOR + " TEXT, "
                + GENRE + " TEXT " + ")";
        db.execSQL(CREATE_TABLE);

        String INSERT_ROWS = "INSERT INTO " + MOVIES + "(" + TITLE + " , " + YEAR + " , " + DIRECTOR + " , "
                + GENRE + ")" +
                "VALUES " + "('The NoteBook', '2004', 'Nick Cassavetes',' Drama and Romance'),"
                + "('The Best of Me', '2014', 'Michael Hoffman', 'Drama and Romance'),"
                + "('A Walk to Remember', '2002', 'Adam Shankman', 'Coming of Age and Drama'),"
                + "('The Choice', '2016', 'Ross Katz', 'Drama and Romance'),"
                + "('Safe Haven', '2013', 'Lasse Hallstrom', 'Drama and Thriller'),"
                + "('The Longest Ride', '2015', 'George Tillman Jr', 'Drama and Romance'),"
                + "('Dear John', '2010', 'Lasse Hallstrom', 'Film Adaptation and Drama'),"
                + "('The Lucky One', '2012', 'Scott Hicks', 'Drama and Romance'),"
                + "('The Last Song', '2010', 'Julie Anne Robinson', 'Drama and Romance'),"
                + "('Nights In Rodanthe', '2008', 'George C Wolfe', 'Film Adaptation and Romance'),"
                + "('Message In a Bottle', '1999', 'Luis Mandoki', 'Drama and Romance'),"
                + "('Harry Potter and the Philosphers Stone', '2001', 'Chris Columbus', 'Fantasy and Fiction'),"
                + "('Harry Potter and the Chamber of Secrets', '2002', 'Chris Columbus', 'Fantasy and Mystery'),"
                + "('Harry Potter and the Prisoner of Azkaban', '2004', 'Alfonso Cuaron', 'Fantasy and Mystery'),"
                + "('Harry Potter and the Goblet of Fire', '2005', 'Mike Newell', 'Fantasy and Mystery'),"
                + "('Harry Potter and the Order of the Phoenix', '2007', 'David Yates', 'Drama and Fantasy'),"
                + "('Harry Potter and the Half-Blood Prince', '2009', 'David Yates', 'Fantasy and Fantasy'),"
                + "('Harry Potter and the Deathly Hallows – Part 1', '2010', 'David Yates', 'Fantasy and Fantasy'),"
                + "('Harry Potter and the Deathly Hallows – Part 2', '2011', 'David Yates', 'Fantasy and Fantasy')";
        db.execSQL(INSERT_ROWS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void readRow() {

    }
}
