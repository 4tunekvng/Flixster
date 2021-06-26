package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Parcel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.R;
import com.example.flixster.models.Movie;
import com.example.flixster.models.MovieDetailsActivity;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;


    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }


    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter","OnCreateViewHolder" );
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }


    // Involves populating data into the item from the holder
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Log.d("MovieAdapter","OnBindViewHolder" + position);
        // Get the movie at the passed in position
        Movie movie=movies.get(position);
        // Bind the movie data into the View Holder
        holder.bind(movie);

    }
    // Return the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    // class cannot be static
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;



        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            // Add ItemView's OnClickListener
            itemView.setOnClickListener(this);

        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            // Glide.with(context).load(movie.getPosterPath()).placeholder(R.drawable.flicks_backdrop_placeholder).into(ivPoster);
            String imageUrl;
            // if phone is in landscape
            if (context.getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
                // then imageUrl= backdropImage
                imageUrl= movie.getBackdropPath();
            }
            else {
                // else imageUrl = posterImage
                imageUrl= movie.getPosterPath();
            }

            Glide.with(context).load(imageUrl).placeholder(R.drawable.flicks_movie_placeholder).into(ivPoster);
        }

        // We want it such that when the user clicks on a row we show the MovieDetailsActivity for the movie on that row
        @Override
        public void onClick(View v) {
            // get item position
            int position = getAdapterPosition();
            // Make sure position is valid, and that it actually exists in the View
            if(position!= RecyclerView.NO_POSITION){
                // get the movie at the position, this won't work if the class is static
                Movie movie= movies.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                // Serialize the movie using parceler, use its short name as a key
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                // Show activity
                context.startActivity(intent);
            }

        }
    }
}
