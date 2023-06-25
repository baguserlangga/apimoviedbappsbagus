package com.example.apimoviedbappsbagus.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apimoviedbappsbagus.DetailMoviesActivity
import com.example.apimoviedbappsbagus.Model.ReviewModel
import com.example.apimoviedbappsbagus.R
import com.example.apimoviedbappsbagus.databinding.ItemListReviewerBinding
import java.text.SimpleDateFormat

class ReviewerAdapter(val context:Context, val result : ArrayList <ReviewModel>): RecyclerView.Adapter<ReviewerAdapter.ViewHolder>() {


    class ViewHolder(val binding: ItemListReviewerBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemListReviewerBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item  = result[position]
        holder.binding.imageReview.setOnClickListener{
            val intent = Intent(context, DetailMoviesActivity::class.java)
            intent.putExtra("idMovie", item.id.toString())
            context.startActivity(intent)
        }
        Glide.with(holder.itemView)
            .load("https://image.tmdb.org/t/p/w500" + item.authorDetails!!.avatarPath)
            .error(R.drawable.ic_profile)
            .into(holder.binding.imageReview)
        holder.binding.textViewAuthor.text = item.author+" "
        holder.binding.textViewReview.text = item.content

        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatter = SimpleDateFormat("dd.MMMM.yyyy HH:mm")
        val output: String = formatter.format(parser.parse(item.createdAt.toString()))
        holder.binding.textViewCreated.text = output

    }

    override fun getItemCount(): Int {
        return result.size
    }
}