package com.example.potatopaloozac.codingchallengeproj.ui.browseMovies;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.potatopaloozac.codingchallengeproj.R;
import com.example.potatopaloozac.codingchallengeproj.data.database.DbHelper;
import com.example.potatopaloozac.codingchallengeproj.ui.MainActivity;

import java.util.ArrayList;

/**
 * A fragment that shows a listview of all the movies in the database
 */
public class BrowseMovieFragment extends Fragment {

    private ListView movieList_LV;
    private ArrayList<String> title;
    private ArrayAdapter<String> movieArrayAdapter;
    private DbHelper movieDbHelper;
    private SQLiteDatabase movieDatabase = MainActivity.movieDatabase;
    private Context context;
    private BrowseClickInterface browseClickInterface;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        browseClickInterface = (BrowseClickInterface) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.movie_list_fragment, container, false);

        movieList_LV = v.findViewById(R.id.movieList_LV);
        title = new ArrayList<String>();

        LoadingDbAsyncTask dbAsyncTask = new LoadingDbAsyncTask();
        dbAsyncTask.execute("");

        movieDbHelper = new DbHelper(context);

        return v;
    }

    class LoadingDbAsyncTask extends AsyncTask<String, Integer, String> {

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

            title.clear();
            title.add("Loading database... " + values[0] + "%");
            movieArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, title);
            movieList_LV.setAdapter(movieArrayAdapter);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            title.clear();

            Cursor cursor = movieDatabase.rawQuery("SELECT * FROM " + movieDbHelper.MOVIES, null);

            if (cursor.moveToFirst()) {
                do {
                    title.add(cursor.getString(cursor.getColumnIndex(movieDbHelper.TITLE)));
                } while (cursor.moveToNext());

                movieArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, title);
                movieList_LV.setAdapter(movieArrayAdapter);
            } else {
                String[] noData = {"No data stored..."};

                movieArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, noData);
                movieList_LV.setAdapter(movieArrayAdapter);
            }

            movieList_LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    browseClickInterface.getData(title.get(position));
                }
            });
        }
    }
}
