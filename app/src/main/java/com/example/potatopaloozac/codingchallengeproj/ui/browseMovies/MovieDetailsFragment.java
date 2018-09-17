package com.example.potatopaloozac.codingchallengeproj.ui.browseMovies;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.potatopaloozac.codingchallengeproj.R;
import com.example.potatopaloozac.codingchallengeproj.data.database.DbHelper;
import com.example.potatopaloozac.codingchallengeproj.ui.MainActivity;

/**
 * A fragment that shows the details of that movie when it's clicked in BrowseMovieFragment's listview
 */
public class MovieDetailsFragment extends Fragment {

    private TextView title_TV, year_TV, director_TV, genre_TV;
    private DbHelper movieDbHelper;
    private SQLiteDatabase movieDatabase = MainActivity.movieDatabase;
    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.movie_details_fragment, container, false);

        Bundle bundle = getArguments();
        String s = bundle.getString("clickkey");

        title_TV = v.findViewById(R.id.title_TV);
        year_TV = v.findViewById(R.id.year_TV);
        director_TV = v.findViewById(R.id.director_TV);
        genre_TV = v.findViewById(R.id.genre_TV);

        movieDbHelper = new DbHelper(context);

        Cursor cursor = movieDatabase.rawQuery("SELECT * FROM " + movieDbHelper.MOVIES + " WHERE "
                + movieDbHelper.TITLE + " = " + "'" + s + "'", null);

        String title, year, director, genre;

        cursor.moveToFirst();

        title = cursor.getString(cursor.getColumnIndex(movieDbHelper.TITLE));
        year = cursor.getString(cursor.getColumnIndex(movieDbHelper.YEAR));
        director = cursor.getString(cursor.getColumnIndex(movieDbHelper.DIRECTOR));
        genre = cursor.getString(cursor.getColumnIndex(movieDbHelper.GENRE));

        title_TV.setText(title);
        year_TV.setText(year);
        director_TV.setText(director);
        genre_TV.setText(genre);

        return v;
    }
}
