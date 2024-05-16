package com.example.pi_sniffsnap_garciafernandezmarta.main

import androidx.camera.core.ImageProxy
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pi_sniffsnap_garciafernandezmarta.api.ApiResponseStatus
import com.example.pi_sniffsnap_garciafernandezmarta.doglist.DogRepository
import com.example.pi_sniffsnap_garciafernandezmarta.machinelearning.Classifier
import com.example.pi_sniffsnap_garciafernandezmarta.machinelearning.ClassifierRepository
import com.example.pi_sniffsnap_garciafernandezmarta.machinelearning.DogRecognition
import com.example.pi_sniffsnap_garciafernandezmarta.model.Dog
import kotlinx.coroutines.launch
import java.nio.MappedByteBuffer

class MainViewModel: ViewModel() {

    private val _dog = MutableLiveData<Dog>()
    val dog: LiveData<Dog> get() = _dog

    private val _status =
        MutableLiveData<ApiResponseStatus<Dog>>()
    val status: LiveData<ApiResponseStatus<Dog>> get() = _status

    private val _dogRecognition = MutableLiveData<DogRecognition>()
    val dogRecognition: LiveData<DogRecognition> get() = _dogRecognition

    private val dogRepository = DogRepository()
    private lateinit var classifierRepository: ClassifierRepository

    fun getDogByMlId(mlDogId: String) {
        viewModelScope.launch {
            handleResponseStatus(dogRepository.getDogByMlId(mlDogId))
        }
    }

    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<Dog>) {
        if (apiResponseStatus is ApiResponseStatus.Success){
            _dog.value = apiResponseStatus.data
        }
        _status.value = apiResponseStatus
    }

    fun setupClassifier(tfLiteModel: MappedByteBuffer, labels: List<String>) {

        val classifier = Classifier(tfLiteModel, labels)
        classifierRepository = ClassifierRepository(classifier)
    }

    fun recognizeImage (imageProxy: ImageProxy) {
        viewModelScope.launch {
            _dogRecognition.value = classifierRepository.recognizedImage(imageProxy)
            imageProxy.close()
        }
    }

}