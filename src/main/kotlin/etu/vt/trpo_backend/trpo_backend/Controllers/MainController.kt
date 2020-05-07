package etu.vt.trpo_backend.trpo_backend.Controllers

import com.google.gson.Gson
import etu.vt.trpo_backend.trpo_backend.Models.RequestPictureData
import etu.vt.trpo_backend.trpo_backend.Models.ResponsePictureData
import etu.vt.trpo_backend.trpo_backend.Models.ResponsePictureTest
import org.apache.tomcat.util.codec.binary.Base64
import org.springframework.boot.json.GsonJsonParser
import org.springframework.http.HttpRequest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.converter.json.GsonHttpMessageConverter
import org.springframework.web.bind.annotation.*
import java.io.File
import java.io.FileInputStream

/**
 *   Main controller for give reqeust and send response
 **/
@RestController
@RequestMapping("/api")
class MainController {
    val test = " %s"
    private final val testPathPicture = "D:\\TRPO\\trpo_backend\\trpo_backend\\src\\main\\resources\\picture\\test_picture.png"
    val file : File = File(testPathPicture)
    val counter: Long = 0

    /**
     *   Get method for testing server response on given data from User
     *   @param {String} String test text witch get from User
     *   @return {ResponsePictureTest} DataClass for testing result on request in test
     *   method (testResponse)
     **/
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/test")
    fun testResponse(@RequestParam(value = "name", defaultValue = "Test") name: String): ResponsePictureTest{
        val byteArr: ByteArray
        val s = FileInputStream(file)
        byteArr = s.readBytes()
        s.close()

        return ResponsePictureTest(counter.inc(), String.format(test, name),
                Base64.encodeBase64String(byteArr))
    }

    /**
     *   Post method for load message on server and get result for scanning picture
     *   @param {ByteArray} ByteArray picture witch get from User
     *   @return {ResponsePictureData} DataClass for sending result on request
     **/
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = ["/picture"], method = [RequestMethod.POST], consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun pictureResponse(@RequestBody request: RequestPictureData): ResponsePictureData{
        val success = "Success"
        val failed = "Failed"
        val byteArrayString = Gson().fromJson<String>(request.arrPicture, String::class.java) //request.arrPicture
        println(byteArrayString)

        //In mobile application retry Base64.encodeBase64String(byteArray) for sending ByteArray image data
        val byteArray = Base64.decodeBase64(byteArrayString)
        println(byteArray)
        byteArray.forEach { println(it.toInt()) }
        if (byteArrayString == null)
            return ResponsePictureData(String.format(test, failed), Base64.encodeBase64String(byteArray))

        return ResponsePictureData(String.format(test, success), Base64.encodeBase64String(byteArray))
    }
}