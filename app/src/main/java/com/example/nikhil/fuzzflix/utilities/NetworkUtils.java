package com.example.nikhil.fuzzflix.utilities;

import android.net.Uri;

import com.example.nikhil.fuzzflix.constants.AppConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by nikhil on 11/01/18.
 */
/*
      ************************* HANDLES NETWORK RELATED OPERATIONS ************************
      * USES buildUrl() for the purpose of creating a target url to reach the desired site (Returns URL).
      * USES buildImageUrl(), from the Json Object received we get the poster Path to create an Image Url.
      * USES getResponseFromHttpUrl() to set the connection and then getting the result from the set connection.(Returns String)
 */

// TODO there needs to be more crisp and clear comments do in 2nd run and also modification in buildURl methods.
public class NetworkUtils {


    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;

    //    this part of the Image Url would be retrieved from the received JSon Object(image path appender-4)
    private String mPosterPath = "";

    String inputLine;

//     creates and return base Movie Url.
    public URL buildBaseUrl(String filterType){

        URL url = null;

        Uri.Builder uriBuilder = new Uri.Builder();

        uriBuilder.scheme(AppConstants.getSchemeOfUrl())
                    .authority(AppConstants.getBaseUrlAuthority())
                    .appendPath(AppConstants.getDbVersion())
                    .appendPath(AppConstants.getMovieDataRequest())
                    .appendPath(filterType)
                    .appendQueryParameter(AppConstants.getApiAuthenticationKeyParam() ,AppConstants.getApiAuthenticationKeyValue()).build();

        try{
            url = new URL(uriBuilder.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        return url;
    }


//     creates and return image url.
    public URL buildImageUrl(String posterPath){
        mPosterPath = posterPath;
        URL url = null;
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme(AppConstants.getSchemeOfUrl())
                .authority(AppConstants.getImageAuthority())
                .appendPath(AppConstants.getT())
                .appendPath(AppConstants.getP())
                .appendPath(AppConstants.getImageSize())
                .appendPath(mPosterPath).build();

        try{
            url = new URL(uriBuilder.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        return url;

    }


//     connecting to the url and getting back a response
    public String getHttpUrlRequest(URL url) throws IOException{

        StringBuilder stringBuilder = null;


        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(READ_TIMEOUT);
            urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);

            urlConnection.connect();


            //Create a new InputStreamReader
            InputStreamReader streamReader = new
                    InputStreamReader(urlConnection.getInputStream());

            //Create a new buffered reader and String Builder
            BufferedReader reader = new BufferedReader(streamReader);

            stringBuilder = new StringBuilder();


            //Check if the line we are reading is not null
            while((inputLine = reader.readLine()) != null){
                stringBuilder.append(inputLine);
            }

            //Close our InputStream and Buffered reader
            reader.close();
            streamReader.close();

        } finally {
            urlConnection.disconnect();
        }

        return stringBuilder.toString();
    }


// more comments required.


    // https://api.themoviedb.org/3/movie/popular?api_key=<<api_key>>&language=en-US&page=1
    //https://api.themoviedb.org/3/movie/top_rated?api_key=<<api_key>>&language=en-US&page=1



}
