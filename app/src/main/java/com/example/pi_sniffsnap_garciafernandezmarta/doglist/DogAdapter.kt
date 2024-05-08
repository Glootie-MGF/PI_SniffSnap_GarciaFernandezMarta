package com.example.pi_sniffsnap_garciafernandezmarta.doglist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pi_sniffsnap_garciafernandezmarta.R
import com.example.pi_sniffsnap_garciafernandezmarta.model.Dog
import com.example.pi_sniffsnap_garciafernandezmarta.databinding.DogListItemBinding

class DogAdapter : ListAdapter<Dog, DogAdapter.DogViewHolder>(DiffCallback) {

    companion object DiffCallback :
        DiffUtil.ItemCallback<Dog>() { // Le dice a la lista de qué manera debe responder cuando se edite, agregue o borre un elemento
        override fun areItemsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem.id == newItem.id
        }
    }

    private var onItemClickListener: ((Dog)-> Unit)? = null
    fun setOnItemClickListener(onItemClickListener: (Dog)-> Unit){
        this.onItemClickListener = onItemClickListener
    }

    private var onLongItemClickListener: ((Dog) -> Unit)? = null
    fun setLongOnItemClickListener(onLongItemClickListener: (Dog) -> Unit){
        this.onLongItemClickListener = onLongItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val binding = DogListItemBinding.inflate(LayoutInflater.from(parent.context))
        return DogViewHolder(binding)
    }

    override fun onBindViewHolder(dogViewHolder: DogViewHolder, position: Int) {
        val dog = getItem(position)
        dogViewHolder.bind(dog)
    } // Para cada perro se crea un espacio (viewholder) donde se "pintará"

    inner class DogViewHolder(private val binding: DogListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dog: Dog) { // Función encargada de pintar los perros
            // binding.dogName.text = dog.name
            if (dog.inCollection){
                binding.dogListItemLayout.background = ContextCompat.getDrawable(
                    binding.dogImage.context, R.drawable.dog_list_item_background
                )
                binding.dogImage.visibility = View.VISIBLE
                binding.dogIndex.visibility = View.GONE

                binding.dogListItemLayout.setOnClickListener{
                    onItemClickListener?.invoke(dog)
                }

                binding.dogImage.load(dog.imageUrl)
            } else {
                binding.dogImage.visibility = View.GONE
                binding.dogIndex.visibility = View.VISIBLE
                binding.dogIndex.text = dog.index.toString()
                binding.dogListItemLayout.background = ContextCompat.getDrawable(
                    binding.dogImage.context, R.drawable.dog_list_item_null_background
                )
                binding.dogListItemLayout.setOnLongClickListener{
                    onLongItemClickListener?.invoke(dog)
                    true
                }
            }
        }
    }
}