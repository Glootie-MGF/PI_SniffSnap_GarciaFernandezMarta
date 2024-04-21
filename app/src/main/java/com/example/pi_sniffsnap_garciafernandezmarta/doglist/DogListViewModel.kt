package com.example.pi_sniffsnap_garciafernandezmarta.doglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pi_sniffsnap_garciafernandezmarta.Dog
import kotlinx.coroutines.launch

class DogListViewModel : ViewModel() {

    private val _dogList =
        MutableLiveData<List<Dog>>() // No queremos que sea editable desde clases externas
                                    // al ViewModel por lo que creamos la siguiente variable
    val dogList: LiveData<List<Dog>> get() = _dogList

    private val dogRepository = DogRepository()

    init {
        downloadDogs()
    }

    private fun downloadDogs() {
        // Utilizamos corrutinas para traernos los perros de internet
        viewModelScope.launch {
            // Aqui es donde descargamos los perros. Añadimos la dependencia en gradle de lifecycle
            _dogList.value = dogRepository.downloadDogs()
        }
    }
}