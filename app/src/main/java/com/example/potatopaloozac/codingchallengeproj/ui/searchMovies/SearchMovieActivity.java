package com.example.potatopaloozac.codingchallengeproj.ui.searchMovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.potatopaloozac.codingchallengeproj.R;
import com.example.potatopaloozac.codingchallengeproj.ui.MainActivity;

/**
 * An activity that allows the user the search for a movie based on title, year, director or genre
 */
public class SearchMovieActivity extends AppCompatActivity implements SearchInterface {

    private EditText searchTitle_ET, searchYear_ET, searchDirector_ET, searchGenre_ET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);

        searchTitle_ET = findViewById(R.id.searchTitle_ET);
        searchYear_ET = findViewById(R.id.searchYear_ET);
        searchDirector_ET = findViewById(R.id.searchDirector_ET);
        searchGenre_ET = findViewById(R.id.searchGenre_ET);
    }

    public void onClickHandler(View view) {
        switch (view.getId()) {
            case R.id.searchTitle_BT: {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                if (searchTitle_ET.getText() == null) {
                    Toast.makeText(this, "No search entered...", Toast.LENGTH_SHORT).show();
                } else {
                    String[] s = {searchTitle_ET.getText().toString(), "title"};
                    getData(s);
                    break;
                }
            }
            case R.id.searchYear_BT: {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                if (searchYear_ET.getText() == null) {
                    Toast.makeText(this, "No search entered...", Toast.LENGTH_SHORT).show();
                } else {
                    String[] s = {searchYear_ET.getText().toString(), "year"};
                    getData(s);
                    break;
                }
            }
            case R.id.searchDirector_BT: {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                if (searchDirector_ET.getText() == null) {
                    Toast.makeText(this, "No search entered...", Toast.LENGTH_SHORT).show();
                } else {
                    String[] s = {searchDirector_ET.getText().toString(), "director"};
                    getData(s);
                    break;
                }
            }
            case R.id.searchGenre_BT: {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                if (searchGenre_ET.getText() == null) {
                    Toast.makeText(this, "No search entered...", Toast.LENGTH_SHORT).show();
                } else {
                    String[] s = {searchGenre_ET.getText().toString(), "genre"};
                    getData(s);
                    break;
                }
            }
        }
    }

    @Override
    public void getData(String[] s) {
        SearchResultFragment resultFragment = (SearchResultFragment) getFragmentManager().findFragmentById(R.id.searchResult_fragment);

        getFragmentManager().beginTransaction().replace(R.id.searchResult_fragment, resultFragment).addToBackStack(null).commit();
        resultFragment.getSearchQuery(s);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(SearchMovieActivity.this, MainActivity.class);
        startActivity(i);
    }
}
