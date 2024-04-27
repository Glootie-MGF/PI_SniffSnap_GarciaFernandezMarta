package com.example.pi_sniffsnap_garciafernandezmarta.doglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pi_sniffsnap_garciafernandezmarta.Dog
import com.example.pi_sniffsnap_garciafernandezmarta.api.ApiResponseStatus
import kotlinx.coroutines.launch
import java.lang.Exception

class DogListViewModel : ViewModel() {

    private val _dogList =
        MutableLiveData<List<Dog>>() // No queremos que sea editable desde clases externas
                                    // al ViewModel por lo que creamos la siguiente variable
    val dogList: LiveData<List<Dog>> get() = _dogList

    private val _status =
        MutableLiveData<ApiResponseStatus>()
    val status: LiveData<ApiResponseStatus> get() = _status

    private val dogRepository = DogRepository()

    init {
        downloadDogs()
    }

    private fun downloadDogs() {
        // Utilizamos corrutinas para 'traernos' los perros de internet
        viewModelScope.launch {// Aqui es donde descargamos los perros. Añadimos la dependencia en gradle de lifecycle
            /*_status.value = ApiResponseStatus.LOADING
            try {
                _dogList.value = dogRepository.downloadDogs()
                _status.value = ApiResponseStatus.SUCCESS
            } catch (e: Exception) {
                _status.value = ApiResponseStatus.ERROR
            }
            _dogList.value = dogRepository.downloadDogs()
             */
            _status.value = ApiResponseStatus.Loading()
            handleResponseStatus(dogRepository.downloadDogs())
        }
    }

    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus) {
        if (apiResponseStatus is ApiResponseStatus.Success){
            _dogList.value = apiResponseStatus.dogList // Hará que se 'pinte' la lista de perros
        }
        _status.value = apiResponseStatus
    }
}