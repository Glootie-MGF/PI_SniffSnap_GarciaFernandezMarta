package com.example.pi_sniffsnap_garciafernandezmarta.auth


import com.example.pi_sniffsnap_garciafernandezmarta.api.ApiResponseStatus
import com.example.pi_sniffsnap_garciafernandezmarta.api.DogsApi
import com.example.pi_sniffsnap_garciafernandezmarta.api.dto.SignUpDTO
import com.example.pi_sniffsnap_garciafernandezmarta.api.dto.UserDTOMapper
import com.example.pi_sniffsnap_garciafernandezmarta.api.makeNetworkCall
import com.example.pi_sniffsnap_garciafernandezmarta.model.User

class AuthRepository {

    suspend fun signUp(email: String, password: String, passwordConfirmation: String): ApiResponseStatus<User> {
        return makeNetworkCall {
            val signUpDTO = SignUpDTO(email, password, passwordConfirmation)

            val signUpResponse = DogsApi.retrofitService.signUp(signUpDTO)

            if (!signUpResponse.isSuccess){
                throw Exception(signUpResponse.message)
            }

            val userDTO = signUpResponse.data.user
            val userDTOMapper = UserDTOMapper()
            userDTOMapper.fromUserDTOToUserDomain(userDTO)
        }
    }
}