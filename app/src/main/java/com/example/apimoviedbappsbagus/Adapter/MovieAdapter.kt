package com.example.apimoviedbappsbagus.Adapter

import android.content.Context
import android.content.Intent

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apimoviedbappsbagus.DetailMoviesActivity
import com.example.apimoviedbappsbagus.MainActivity
import com.example.apimoviedbappsbagus.Model.Movies
import com.example.apimoviedbappsbagus.Model.Results
import com.example.apimoviedbappsbagus.databinding.MovieLayoutBinding

class MovieAdapter(val context:Context,val result : ArrayList <Results>): RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private var movieList = ArrayList<Results>()

    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

//    fun setMovieList(movieList: List<Results>) {
//        this.movieList = movieList as ArrayList<Results>
//        notifyDataSetChanged()
//    }

    class ViewHolder(val binding: MovieLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
//        val
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            MovieLayoutBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item  = result[position]
        holder.binding.movieImage.setOnClickListener{
            val intent = Intent(context, DetailMoviesActivity::class.java)
            intent.putExtra("idMovie", item.id.toString())
            context.startActivity(intent)
        }
        Glide.with(holder.itemView)
            .load("https://image.tmdb.org/t/p/w500" + result[position].poster_path)
            .into(holder.binding.movieImage)
        holder.binding.movieName.text = result[position].title +" " +item.id
    }

    override fun getItemCount(): Int {
        return result.size
    }
}