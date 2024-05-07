package com.example.pi_sniffsnap_garciafernandezmarta.doglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pi_sniffsnap_garciafernandezmarta.model.Dog
import com.example.pi_sniffsnap_garciafernandezmarta.api.ApiResponseStatus
import kotlinx.coroutines.launch

class DogListViewModel : ViewModel() {

    private val _dogList =
        MutableLiveData<List<Dog>>() // No queremos que sea editable desde clases externas
                                    // al ViewModel por lo que creamos la siguiente variable
    val dogList: LiveData<List<Dog>> get() = _dogList

    // Cambiamos el tipo <List<Dog>> por <Any> para poder reutilizar la variable
    private val _status =
        MutableLiveData<ApiResponseStatus<Any>>()
    val status: LiveData<ApiResponseStatus<Any>> get() = _status

    private val dogRepository = DogRepository()

    init {
        // downloadUserDogs()
        // downloadDogs()
        getDogCollection()
    }

    fun addDogToUser(dogId: Long){
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            handleAddDogToUserResponseStatus(dogRepository.addDogToUser(dogId))
        }
    }
    private fun downloadUserDogs(){
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            handleResponseStatus(dogRepository.getUserDogs())
        }
    }

    private fun getDogCollection(){
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            handleResponseStatus(dogRepository.getDogCollection())
        }
    }

    // Al implementar getDogCollection, ya no necesitaremos usar este método:
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

    @Suppress("UNCHECKED_CAST") // Para eliminar warning as castear _status
    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<List<Dog>>) {
        if (apiResponseStatus is ApiResponseStatus.Success){
            _dogList.value = apiResponseStatus.data // Hará que se 'pinte' la lista de perros
        }
        _status.value = apiResponseStatus as ApiResponseStatus<Any>
    }

    private fun handleAddDogToUserResponseStatus(apiResponseStatus: ApiResponseStatus<Any>){
        if (apiResponseStatus is ApiResponseStatus.Success){
            // downloadDogs()
            getDogCollection()
        }
        _status.value = apiResponseStatus
    }
}