package com.example.pi_sniffsnap_garciafernandezmarta.database

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FavoriteDogAdapter : RecyclerView.Adapter<FavoriteDogAdapter.ViewHolder>() {
    private var favoriteDogs = emptyList<FavoriteDogEntity>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(favoriteDog: FavoriteDogEntity) {
            // Implementar el método bind para actualizar la UI con los datos del perro favorito
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Crear y devolver un ViewHolder

        return TODO("Provide the return value")
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