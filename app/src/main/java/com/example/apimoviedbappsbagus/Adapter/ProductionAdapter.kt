package com.example.apimoviedbappsbagus.Adapter

import android.content.Context
import android.content.Intent

import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apimoviedbappsbagus.DetailMoviesActivity
import com.example.apimoviedbappsbagus.Fragment.ListFragmentMovies
import com.example.apimoviedbappsbagus.Fragment.ReviewerFragment.Companion.newInstance
import com.example.apimoviedbappsbagus.Model.GenresModel
import com.example.apimoviedbappsbagus.Model.ProductionCompanies
import com.example.apimoviedbappsbagus.Model.Results
import com.example.apimoviedbappsbagus.MovieByGenreActivity
import com.example.apimoviedbappsbagus.R
import com.example.apimoviedbappsbagus.databinding.ItemListGenreBinding
import com.example.apimoviedbappsbagus.databinding.ItemListProductionBinding
import com.example.apimoviedbappsbagus.databinding.MovieLayoutBinding

class ProductionAdapter(val context:Context, val result : ArrayList <ProductionCompanies>): RecyclerView.Adapter<ProductionAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemListProductionBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemListProductionBinding.inflate(
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
            val intent = Intent(context, MovieByGenreActivity::class.java)
            intent.putExtra("genreId", item.id.toString())
            context.startActivity(intent)


        }
        if(item.logoPath!=null)
        {
            Glide.with(holder.itemView)
                .load("https://image.tmdb.org/t/p/w500" + item.logoPath!!)
                .error(R.drawable.ic_profile)
                .into(holder.binding.imageReview)
        }
        else{
            Glide.with(holder.itemView)
                .load(R.drawable.ic_profile)
                .error(R.drawable.ic_profile)
                .into(holder.binding.imageReview)
        }


    }

    override fun getItemCount(): Int {
        return result.size
    }
}