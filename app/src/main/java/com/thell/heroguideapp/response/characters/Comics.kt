package com.thell.heroguideapp.response.characters

data class Comics(
    val available: Int,
    val collectionURI: String,
    val items: List<İtem>,
    val returned: Int
)