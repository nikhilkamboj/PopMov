package com.example.nikhil.fuzzflix;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by nikhil on 13/01/18.
 */

/**
 *  this class is helps in creating viewHolder objects and then displaying it to the screen through UI thread.
 *  uses an {@link MovieDataAdapterViewHolder} inner class for caching the references of views to be shown on screen.
 *
 *  extends {@link RecyclerView}
 *
 */

public class MovieDataAdapter extends RecyclerView.Adapter<MovieDataAdapter.MovieDataAdapterViewHolder>{

    String[] mMovieData;

    /**
     * this method is called when there is a need for viewHolders on the screen. when Recycle View is out of viewHolders
     * this method gets called to create new ViewHolders.
     *
     *@param viewGroup view around which these viewHolders are contained into.
     *@param viewType  not used now
     *
     *@return A new MovieDataAdapterViewHolder that holds the View for each list item
     */
    @Override
    public MovieDataAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();

        int layoutForItemList = R.layout.movie_data_list;

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        boolean shouldAttachToParentImmediately = false;

        View view = layoutInflater.inflate(layoutForItemList,viewGroup,shouldAttachToParentImmediately);

        return new MovieDataAdapterViewHolder(view);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the weather
     * details for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param movieHolder this is the holder object whose fields should be updated.
     * @param position     this tells about the current screen position
     */
    @Override
    public void onBindViewHolder(MovieDataAdapterViewHolder movieHolder, int position) {
        String movieDataForPosition = mMovieData[position];
        movieHolder.mTitleTextView.setText(movieDataForPosition);
    }


    /**
     * this gets the item count of the list etc to be shown on-screen
     *
     * @return returns the item count
     */
    @Override
    public int getItemCount() {
        if(mMovieData == null){
            return 0;
        }
        return mMovieData.length;
    }


    /**
     * Cache of the children views for a movie list item.
     */
    public class MovieDataAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView mTitleTextView;
        TextView mReleaseDateTextView;
        TextView mRatingTextView;


        public MovieDataAdapterViewHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.tv_title_list_id);
            mReleaseDateTextView = (TextView) itemView.findViewById(R.id.tv_release_date_list_id);
            mRatingTextView = (TextView) itemView.findViewById(R.id.tv_rating_list_id);
        }
    }

    /**
     * sets the data and notify the change
     *
     * @param movieData this is the movieData to be dispalyed
     */
    public void setMovieData(String[] movieData) {
        mMovieData = movieData;
        notifyDataSetChanged();
    }
}
