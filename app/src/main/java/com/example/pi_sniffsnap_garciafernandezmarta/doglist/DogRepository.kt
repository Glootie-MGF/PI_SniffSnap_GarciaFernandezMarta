package com.example.pi_sniffsnap_garciafernandezmarta.doglist;

import com.example.pi_sniffsnap_garciafernandezmarta.model.Dog;
import com.example.pi_sniffsnap_garciafernandezmarta.api.ApiResponseStatus
import com.example.pi_sniffsnap_garciafernandezmarta.api.DogsApi
import com.example.pi_sniffsnap_garciafernandezmarta.api.dto.DogDTOMapper
import com.example.pi_sniffsnap_garciafernandezmarta.api.makeNetworkCall

class DogRepository {
    suspend fun downloadDogs(): ApiResponseStatus<List<Dog>> {
        return makeNetworkCall {
            val dogListApiResponse = DogsApi.retrofitService.getAllDogs()
            val dogDTOList = dogListApiResponse.data.dogs
            val dogDTOMapper = DogDTOMapper()
            dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
        }
        /*return withContext(Dispatchers.IO){
            try {
                val dogListApiResponse = retrofitService.getAllDogs()
                val dogDTOList = dogListApiResponse.data.dogs
                val dogDTOMapper = DogDTOMapper()
                // dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
                ApiResponseStatus.Success(dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList))
            } catch (e: Exception){
                ApiResponseStatus.Error(R.string.unknown_error)
            } catch (e: UnknownHostException){
                ApiResponseStatus.Error(R.string.error_unknown_host_exc)
            }
        }*/
    }

    // Una vez que funciona la conexión a la API para traer la lista de perros
    // no nos hace falta la siguiente función
    /*private fun getFakeDogs(): MutableList<Dog> {
        val dogList = mutableListOf<Dog>()
        dogList.add(
            Dog(
                1, 1, "Chihuahua", "Toy", 5.4,
                6.7, "", "12 - 15", "", 10.5,
                12.3
            )
        )
        dogList.add(
            Dog(
                2, 1, "Labrador", "Toy", 5.4,
                6.7, "", "12 - 15", "", 10.5,
                12.3
            )
        )
        dogList.add(
            Dog(
                3, 1, "Retriever", "Toy", 5.4,
                6.7, "", "12 - 15", "", 10.5,
                12.3
            )
        )
        dogList.add(
            Dog(
                4, 1, "San Bernardo", "Toy", 5.4,
                6.7, "", "12 - 15", "", 10.5,
                12.3
            )
        )
        dogList.add(
            Dog(
                5, 1, "Husky", "Toy", 5.4,
                6.7, "", "12 - 15", "", 10.5,
                12.3
            )
        )
        dogList.add(
            Dog(
                6, 1, "Xoloscuincle", "Toy", 5.4,
                6.7, "", "12 - 15", "", 10.5,
                12.3
            )
        )
        return dogList
    }*/
}
