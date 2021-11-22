package com.mehmetalioyur.findmovieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.mehmetalioyur.findmovieapp.R
import com.mehmetalioyur.findmovieapp.moviesmodel.Result
import com.mehmetalioyur.findmovieapp.util.Constants.POSTER_BASE_URL
import javax.inject.Inject

class MoviesRecyclerAdapter @Inject constructor(

    private val glide: RequestManager
) :
    RecyclerView.Adapter<MoviesRecyclerAdapter.MoviesViewHolder>() {


    class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    private var onItemClickListener : ((Result)-> Unit)? = null

    fun setOnItemClickListener (listener : (Result)-> Unit){

        onItemClickListener = listener
    }


    //  bu result değil movies model olabilir.
    private val diffUtil = object  : DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem //true
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem //false
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)

     var movies : List<Result>
        get() {
            return recyclerListDiffer.currentList
        }
        set(value) {

            return recyclerListDiffer.submitList(value)
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_row, parent, false)
        return MoviesViewHolder(view)
    }


    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {

        val imageView = holder.itemView.findViewById<ImageView>(R.id.rvPosterImage)
        val movieText = holder.itemView.findViewById<TextView>(R.id.rvMovieName)
        val movie = movies[position]
        holder.itemView.apply {
            movieText.text = movie.title
            glide.load("$POSTER_BASE_URL${movie.poster_path}").into(imageView)
            setOnClickListener {
                onItemClickListener?.let {
                    it(movie)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return movies.size
    }



}

