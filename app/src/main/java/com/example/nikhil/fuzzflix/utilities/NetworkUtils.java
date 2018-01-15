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
/**
 *  ************************* HANDLES NETWORK RELATED OPERATIONS ************************
 *  USES buildUrl() for the purpose of creating a target url to reach the desired site (Returns URL).
 *  USES buildImageUrl(), from the Json Object received we get the poster Path to create an Image Url.
 *  USES getResponseFromHttpUrl() to set the connection and then getting the result from the set connection.(Returns String)
 *
 *
 *
 */

// TODO there needs to be more crisp and clear comments do in 2nd run and also modification in buildURl methods.
public class NetworkUtils {


    //    this part of the Image Url would be retrieved from the received JSon Object(image path appender-4)
    private String mPosterPath = "";

    String inputLine;

    /**
     * this method uses Uri.Builder to build the url for the network call
     *
     * @param filterType type of URL required popular or top_rated.
     * @return returns a url as per the user preference
     */
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

    /**
     * uses Uri.Builder to build a image Url to reach to the poster of particular movie.
     *
     * @param posterPath this is the poster path required for the image URL
     * @return returns a IMAGE URL
     */
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

    /**
     *
     * this method sets network connection to the url received and gets JSON data from there.
     *
     * uses InputStreamReader and BufferedReader to receive the inputStream of data and append that stream to the
     * StringBuilder which would be converted to the String object upon return.
     *
     * @param url receives the URl over which a request needs to be made
     * @return String Object received by calling the url usually a JSON type.
     * @throws IOException
     */
    public String getHttpUrlRequest(URL url) throws IOException{

        StringBuilder stringBuilder = null;


        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            urlConnection.setRequestMethod(AppConstants.getConnectionType());
            urlConnection.setReadTimeout(AppConstants.getReadTimeout());
            urlConnection.setConnectTimeout(AppConstants.getConnectionTimeout());

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




}
