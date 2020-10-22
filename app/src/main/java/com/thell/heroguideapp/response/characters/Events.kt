package com.thell.heroguideapp.response.characters

data class Events(
    val available: Int,
    val collectionURI: String,
    val items: List<İtemX>,
    val returned: Int
)