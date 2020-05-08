package etu.vt.trpo_backend.trpo_backend.Models

import org.springframework.web.multipart.MultipartFile
import java.io.File

data class ResponsePictureData(val content: String, val arrPicture: File)
data class ResponsePictureTest(val id: Long, val content: String, val arrPicture: String)
data class RequestPictureData(val arrPicture: MultipartFile)