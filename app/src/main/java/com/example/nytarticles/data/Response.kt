package com.example.nytarticles.data

data class Response(
    val docs: List<Doc>,
    val meta: Meta
)