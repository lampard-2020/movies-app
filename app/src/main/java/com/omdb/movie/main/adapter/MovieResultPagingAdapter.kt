package com.omdb.movie.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.omdb.movie.R
import com.omdb.movie.data.domain.Movie
import com.omdb.movie.databinding.ItemMovieBinding

class MovieResultPagingAdapter(
    private var itemClicked: ((Movie) -> Unit)? = null,
) : PagingDataAdapter<Movie, MovieResultPagingAdapter.ViewHolder>(MovieDiffUtil) {

    class ViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = getItem(position) ?: return

        holder.binding.textTitle.text = model.title
        holder.binding.textSubTitle.text = model.year

        Glide.with(holder.binding.root.context).load(model.poster)
            .error(R.drawable.ic_movie_default)
            .diskCacheStrategy(
                DiskCacheStrategy.ALL
            ).into(holder.binding.image)


        holder.itemView.setOnClickListener {
            itemClicked?.invoke(model)
        }
    }

    object MovieDiffUtil : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.imdbID == newItem.imdbID
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

}
