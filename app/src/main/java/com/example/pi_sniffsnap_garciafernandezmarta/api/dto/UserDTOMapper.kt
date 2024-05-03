package com.example.pi_sniffsnap_garciafernandezmarta.api.dto

import com.example.pi_sniffsnap_garciafernandezmarta.model.User

class UserDTOMapper {
    fun fromUserDTOToUserDomain(userDTO: UserDTO): User {
        return User(userDTO.id, userDTO.email, userDTO.authenticationToken)
    }
}