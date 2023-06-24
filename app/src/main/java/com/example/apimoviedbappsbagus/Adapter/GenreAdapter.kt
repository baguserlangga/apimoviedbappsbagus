package com.example.apimoviedbappsbagus.Adapter

import android.content.Context
import android.content.Intent

import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apimoviedbappsbagus.DetailMoviesActivity
import com.example.apimoviedbappsbagus.Model.GenresModel
import com.example.apimoviedbappsbagus.Model.Results
import com.example.apimoviedbappsbagus.databinding.ItemListGenreBinding
import com.example.apimoviedbappsbagus.databinding.MovieLayoutBinding

class GenreAdapter(val context:Context, val result : ArrayList <GenresModel>): RecyclerView.Adapter<GenreAdapter.ViewHolder>() {
    private var movieList = ArrayList<Results>()

    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

//    fun setMovieList(movieList: List<Results>) {
//        this.movieList = movieList as ArrayList<Results>
//        notifyDataSetChanged()
//    }

    class ViewHolder(val binding: ItemListGenreBinding) : RecyclerView.ViewHolder(binding.root) {
//        val
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemListGenreBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item  = result[position]

        holder.binding.tvGenre.text = item.name
        holder.binding.tvGenre.setOnClickListener{
//            val intent = Intent(context, DetailMoviesActivity::class.java)
//            intent.putExtra("idMovie", item.id.toString())
//            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return result.size
    }
}