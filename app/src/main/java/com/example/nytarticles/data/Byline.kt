package com.example.nytarticles.data

data class Byline(
    val organization: Any,
    val original: String,
    val person: List<Person>
)