package com.example.pi_sniffsnap_garciafernandezmarta.doglist;

import com.example.pi_sniffsnap_garciafernandezmarta.R
import com.example.pi_sniffsnap_garciafernandezmarta.model.Dog;
import com.example.pi_sniffsnap_garciafernandezmarta.api.ApiResponseStatus
import com.example.pi_sniffsnap_garciafernandezmarta.api.DogsApi
import com.example.pi_sniffsnap_garciafernandezmarta.api.DogsApi.retrofitService
import com.example.pi_sniffsnap_garciafernandezmarta.api.dto.AddDogToUserDTO
import com.example.pi_sniffsnap_garciafernandezmarta.api.dto.DogDTOMapper
import com.example.pi_sniffsnap_garciafernandezmarta.api.makeNetworkCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

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
    suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {

        return makeNetworkCall {
            val addDogToUserDTO = AddDogToUserDTO(dogId)
            val defaultResponse = retrofitService.addDogToUser(addDogToUserDTO)

            if (!defaultResponse.isSuccess){
                throw Exception(defaultResponse.message)
            }
        }
    }

    suspend fun getUserDogs(): ApiResponseStatus<List<Dog>> {

        return makeNetworkCall {
            val dogListApiResponse = DogsApi.retrofitService.getUserDogs()
            val dogDTOList = dogListApiResponse.data.dogs
            val dogDTOMapper = DogDTOMapper()
            dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
        }
    }

    suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {

        return withContext(Dispatchers.IO) {
            val allDogsListDeferred = async { downloadDogs() }
            val userDogsListDeferred = async { getUserDogs() }

            val allDogsListResponse = allDogsListDeferred.await()
            val userDogsListResponse = userDogsListDeferred.await()

            if (allDogsListResponse is ApiResponseStatus.Error) {
                allDogsListResponse
            } else if (userDogsListResponse is ApiResponseStatus.Error) {
                userDogsListResponse
            } else if (allDogsListResponse is ApiResponseStatus.Success &&
                userDogsListResponse is ApiResponseStatus.Success) {

                //ApiResponseStatus.Success(listOf())
                ApiResponseStatus.Success(getCollectionList(allDogsListResponse.data,
                    userDogsListResponse.data))
            } else {
                ApiResponseStatus.Error(R.string.unknown_error)
            }
        }
    }

    private fun getCollectionList(allDogList: List<Dog>, userDogList: List<Dog>): List<Dog> {

        return allDogList.map {
            if (userDogList.contains(it)) {
                it
            } else {
                Dog(it.id, it.index, "", "", "", "", "",
                    "", "", "", "",
                    inCollection = false) // Perro falso
            }
        }.sorted()
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
