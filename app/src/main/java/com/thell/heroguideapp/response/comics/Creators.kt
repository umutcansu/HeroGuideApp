package com.thell.heroguideapp.response.comics

data class Creators(
    val available: Int,
    val collectionURI: String,
    val items: List<İtemX>,
    val returned: Int
)