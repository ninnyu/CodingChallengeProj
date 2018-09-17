package com.example.potatopaloozac.codingchallengeproj.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.potatopaloozac.codingchallengeproj.R;
import com.example.potatopaloozac.codingchallengeproj.data.database.DbHelper;
import com.example.potatopaloozac.codingchallengeproj.ui.browseMovies.BrowseMovieFragment;
import com.example.potatopaloozac.codingchallengeproj.ui.browseMovies.BrowsePresenter;
import com.example.potatopaloozac.codingchallengeproj.ui.browseMovies.MovieDetailsFragment;
import com.example.potatopaloozac.codingchallengeproj.ui.browseMovies.BrowseClickInterface;
import com.example.potatopaloozac.codingchallengeproj.ui.searchMovies.SearchMovieActivity;
import com.example.potatopaloozac.codingchallengeproj.ui.searchMovies.SearchPresenter;

/**
 * The MainActivity. The welcome screen. Shows an introduction of the app and gives user options on
 * how to interact with the app
 */
public class MainActivity extends AppCompatActivity implements IView, BrowseClickInterface {

    private BrowsePresenter bp;
    private SearchPresenter sp;
    private MainPresenter mp;
    private DbHelper movieDbHelper;
    public static SQLiteDatabase movieDatabase;
    private Button search_BT, browse_BT, playMusic_BT, stopMusic_BT;
    private MusicBroadcastReceiver musicReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mp = new MainPresenter();

        movieDbHelper = new DbHelper(MainActivity.this);
        movieDatabase = movieDbHelper.getReadableDatabase();

        search_BT = findViewById(R.id.search_BT);
        browse_BT = findViewById(R.id.browse_BT);
        playMusic_BT = findViewById(R.id.playMusic_BT);
        stopMusic_BT = findViewById(R.id.stopMusic_BT);

        search_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SearchMovieActivity.class);
                startActivity(i);
            }
        });

        browse_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseMovieFragment browseMovieFragment = new BrowseMovieFragment();
                getFragmentManager().beginTransaction().add(R.id.main_layout, browseMovieFragment).addToBackStack(null).commit();
            }
        });

        playMusic_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MusicIntentService.class);
                startService(i);
            }
        });

        stopMusic_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MusicIntentService.class);
                stopService(i);
            }
        });
    }

    @Override
    public void getData(String s) {
        MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();

        Bundle bundle = new Bundle();
        bundle.putString("clickkey", s);
        movieDetailsFragment.setArguments(bundle);

        getFragmentManager().beginTransaction().add(R.id.main_layout, movieDetailsFragment).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    protected void onStart() {
        musicReceiver = new MusicBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("music");

        LocalBroadcastManager.getInstance(this).registerReceiver(musicReceiver, intentFilter);
        super.onStart();
    }

    class MusicBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String s = intent.getStringExtra("broadcastMessage");
            Toast.makeText(context, s, Toast.LENGTH_LONG).show();
        }
    }
}
