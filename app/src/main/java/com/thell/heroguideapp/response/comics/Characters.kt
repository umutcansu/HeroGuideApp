package com.thell.heroguideapp.response.comics

data class Characters(
    val available: Int,
    val collectionURI: String,
    val items: List<İtem>,
    val returned: Int
)