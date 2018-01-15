package com.example.nikhil.fuzzflix;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.nikhil.fuzzflix.constants.AppConstants;
import com.example.nikhil.fuzzflix.utilities.JsonUtils;
import com.example.nikhil.fuzzflix.utilities.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    private void loadMovieData(String selectedFilter){
        if(selectedFilter == AppConstants.getPopularFilterValue()){
           new FetchMovieData().execute(AppConstants.getPopularFilterValue());
        }else{
            new FetchMovieData().execute(AppConstants.getTopRatedFilterValue());
        }
    }




    /*
      overriding onCreateOptionsMenu for the purpose of inflating the menu resource
      file and letting it visible in Main Activity
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // getting MenuInflater instance
        MenuInflater menuInflater = getMenuInflater();

        // inflating the menu layout
        menuInflater.inflate(R.menu.filter,menu);

        return true;
    }

    /*
      overriding the onOptionsItemSelected(), if any of the menu item is clicked this
      callback is called.
      handling all menu click events over here.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem clickedItem) {
        // getting id of the clicked item
        int id = clickedItem.getItemId();
        if(id == R.id.top_rated_menu_item){
            loadMovieData(AppConstants.getTopRatedFilterValue());
        }else{
            loadMovieData(AppConstants.getPopularFilterValue());
        }

        return super.onOptionsItemSelected(clickedItem);
    }

    //  fetches movie data from url
    public class FetchMovieData extends AsyncTask<String, Void, String[]>{
        @Override
        protected String[] doInBackground(String... params) {

            if(params.length == 0){
                return null;
            }

            String filterType = params[0];

            String [] result = null;


            NetworkUtils networkUtils = new NetworkUtils();

            URL url = networkUtils.buildBaseUrl(filterType);

            String movieData = null;

            try {

                movieData = networkUtils.getHttpUrlRequest(url);

                JsonUtils jsonUtils = new JsonUtils();

                result = jsonUtils.getMovieDataFromJsonString(MainActivity.this, movieData);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }



            return result;
        }

        @Override
        protected void onPostExecute(String[] strings) {
            TextView tv = (TextView) findViewById(R.id.tv_result);

            for (int i = 0 ; i< strings.length; i++){
                tv.append(strings[i] + "\n");
            }

        }
    }







}
