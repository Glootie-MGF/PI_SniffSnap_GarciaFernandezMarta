package com.example.pi_sniffsnap_garciafernandezmarta.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Dog (
    val id: Long,
    val index: Int,
    val name: String,
    val type: String,
    val heightFemale: String,
    val heightMale: String,
    val imageUrl: String,
    val lifeExpectancy: String,
    val temperament: String,
    val weightFemale: String,
    val weightMale: String,
    var inCollection: Boolean = true
) : Parcelable, Comparable<Dog>{
    override fun compareTo(other: Dog): Int {
        return if (this.index > other.index) {
            1
        }else {
            -1
        }
    }

}