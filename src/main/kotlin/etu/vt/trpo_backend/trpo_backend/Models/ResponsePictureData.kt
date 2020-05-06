package etu.vt.trpo_backend.trpo_backend.Models

data class ResponsePictureData(val content: String, val arrPicture: String)
data class ResponsePictureTest(val id: Long, val content: String, val arrPicture: String)
data class RequestPictureData(val arrPicture: String)