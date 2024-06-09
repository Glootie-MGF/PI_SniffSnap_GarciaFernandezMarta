package com.example.pi_sniffsnap_garciafernandezmarta.database

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pi_sniffsnap_garciafernandezmarta.R
import com.example.pi_sniffsnap_garciafernandezmarta.databinding.ItemFavoriteDogBinding

class FavoriteDogAdapter : RecyclerView.Adapter<FavoriteDogAdapter.ViewHolder>() {
    private var favoriteDogs = emptyList<FavoriteDogEntity>()

    class ViewHolder(val binding: ItemFavoriteDogBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteDog: FavoriteDogEntity) {
            binding.textViewFavoriteDogName.text = favoriteDog.dogName
            binding.textViewFavoriteDogDetail.text = "Adorable Doggie"
            Glide.with(itemView.context).load(favoriteDog.dogImageUrl).into(binding.imageViewFavoriteDog)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFavoriteDogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(favoriteDogs[position])
    }

    override fun getItemCount() = favoriteDogs.size

    internal fun setFavoriteDogs(favoriteDogs: List<FavoriteDogEntity>) {
        this.favoriteDogs = favoriteDogs
        notifyDataSetChanged()
    }
}