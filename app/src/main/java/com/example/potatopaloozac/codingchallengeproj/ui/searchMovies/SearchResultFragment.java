package com.example.potatopaloozac.codingchallengeproj.ui.searchMovies;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.potatopaloozac.codingchallengeproj.R;
import com.example.potatopaloozac.codingchallengeproj.data.database.DbHelper;
import com.example.potatopaloozac.codingchallengeproj.ui.MainActivity;

import java.util.ArrayList;

/**
 * A fragment that shows the results of the movie search from SearchMovieActivity
 */
public class SearchResultFragment extends Fragment {

    private ListView searchResult_LV;
    private ArrayList<String> movieResults;
    private ArrayAdapter<String> resultArrayAdapter;
    private String[] searchQuery;
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
        View v = inflater.inflate(R.layout.search_result_fragment, container, false);

        searchResult_LV = v.findViewById(R.id.searchResult_LV);
        movieDbHelper = new DbHelper(context);
        movieResults = new ArrayList<String>();

        return v;
    }

    public void getSearchQuery(String[] s) {
        searchQuery = s;
        SearchingDbAsyncTask dbAsyncTask = new SearchingDbAsyncTask();
        dbAsyncTask.execute(s);

        movieResults.clear();
    }

    public void showResults(Cursor cursor) {
        movieResults.clear();

        if (cursor.moveToFirst()) {
            do {
                movieResults.add(cursor.getString(cursor.getColumnIndex(movieDbHelper.TITLE)));
            } while (cursor.moveToNext());

            resultArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, movieResults);
            searchResult_LV.setAdapter(resultArrayAdapter);
        } else {
            String[] result = {"Nothing found..."};

            resultArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, result);
            searchResult_LV.setAdapter(resultArrayAdapter);
        }
    }

    class SearchingDbAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            for (int i = 0; i < 100; i++) {
                int value = i;

                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                publishProgress(value);
            }

            String result = strings[0];
            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            movieResults.clear();
            movieResults.add("Searching database... " + values[0] + "%");
            resultArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, movieResults);
            searchResult_LV.setAdapter(resultArrayAdapter);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (searchQuery[1].equals("title")) {
                Cursor cursor = movieDatabase.rawQuery("SELECT title FROM " + movieDbHelper.MOVIES + " WHERE "
                        + movieDbHelper.TITLE + " LIKE " + "'%" + searchQuery[0] + "%'", null);

                showResults(cursor);
            }

            if (searchQuery[1].equals("year")) {
                Cursor cursor = movieDatabase.rawQuery("SELECT title FROM " + movieDbHelper.MOVIES + " WHERE "
                        + movieDbHelper.YEAR + " = " + "'" + searchQuery[0] + "'", null);

                showResults(cursor);
            }
            if (searchQuery[1].equals("director")) {
                Cursor cursor = movieDatabase.rawQuery("SELECT title FROM " + movieDbHelper.MOVIES + " WHERE "
                        + movieDbHelper.DIRECTOR + " LIKE " + "'%" + searchQuery[0] + "%'", null);

                showResults(cursor);

            }
            if (searchQuery[1].equals("genre")) {
                Cursor cursor = movieDatabase.rawQuery("SELECT title FROM " + movieDbHelper.MOVIES + " WHERE "
                        + movieDbHelper.GENRE + " LIKE " + "'%" + searchQuery[0] + "%'", null);

                showResults(cursor);
            }
        }
    }
}
