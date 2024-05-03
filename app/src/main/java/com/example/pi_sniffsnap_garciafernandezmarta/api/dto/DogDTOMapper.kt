package com.example.pi_sniffsnap_garciafernandezmarta.api.dto

import com.example.pi_sniffsnap_garciafernandezmarta.model.Dog

// Clase encargada de convertir el DogDTO en Dog y viceversa
class DogDTOMapper {

    private fun fromDogDTOToDogDomain(dogDTO: DogDTO): Dog {
        return Dog(
            dogDTO.id,
            dogDTO.index,
            dogDTO.name,
            dogDTO.type,
            dogDTO.heightFemale,
            dogDTO.heightMale,
            dogDTO.imageUrl,
            dogDTO.lifeExpectancy,
            dogDTO.temperament,
            dogDTO.weightFemale,
            dogDTO.weightMale
        )
    }
    fun fromDogDTOListToDogDomainList(dogDTOList:List<DogDTO> ): List<Dog> {
        // El mapa itera para cada uno de los elementos de la lista
        // de DogDTO y aplica una transformación a esos elementos
        return dogDTOList.map { fromDogDTOToDogDomain(it) }
    }
}