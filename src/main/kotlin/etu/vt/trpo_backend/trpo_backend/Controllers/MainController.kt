package etu.vt.trpo_backend.trpo_backend.Controllers

import etu.vt.trpo_backend.trpo_backend.Models.ResponsePictureData
import etu.vt.trpo_backend.trpo_backend.Models.ResponsePictureTest
import org.apache.tomcat.util.codec.binary.Base64
import org.springframework.boot.web.servlet.MultipartConfigFactory
import org.springframework.context.annotation.Bean
import org.springframework.core.annotation.Order
import org.springframework.util.unit.DataSize
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.commons.CommonsMultipartResolver
import org.springframework.web.multipart.support.MultipartFilter
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.servlet.MultipartConfigElement


/**
 *   Main controller for give reqeust and send response
 **/
@RestController
@RequestMapping("/api")
class MainController {
    val test = " %s"
    private final val testPathPicture = "resources/picture/test_picture.png"
    val file : File = File(testPathPicture)
    val counter: Long = 0

    /**
     *   Get method for testing server response on given data from User
     *   @param {String} String test text witch get from User
     *   @return {ResponsePictureTest} DataClass for testing result on request in test
     *   method (testResponse)
     **/
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
    @RequestMapping(
            value = ["/picture"],
            method = [RequestMethod.POST])
//            consumes = [MediaType.MULTIPART_MIXED_VALUE])
    @ResponseBody
    fun pictureResponse(@RequestParam("imageRequest") request: MultipartFile): ResponsePictureData{
        val success = "Success"
        val failed = "Failed"
        if (!request.isEmpty) {
            try {
                val bytes: ByteArray = request.bytes
                val stream = BufferedOutputStream(FileOutputStream(File("test_file" + "-uploaded")))
                stream.write(bytes)
                stream.close()
                println("Вы удачно загрузили " + "test_file" + " в " + "test_file" + "-uploaded !")
            } catch (e: Exception) {
                println("Вам не удалось загрузить " + "test_file" + " => " + e.message)
            }
        } else {
            println("Вам не удалось загрузить " + "test_file" + " потому что файл пустой.")
        }
        //val byteArrayString = request.arrPicture//Gson().fromJson<String>(request.arrPicture, String::class.java) //request.arrPicture
        //println(byteArrayString)

        //In mobile application retry Base64.encodeBase64String(byteArray) for sending ByteArray image data
        //val byteArray = Base64.decodeBase64(byteArrayString)
        //println(byteArray)
        //if (byteArrayString == null)
            //return ResponsePictureData(String.format(test, failed), Base64.encodeBase64String(byteArray))
        val stream = FileInputStream(File("test_file" + "-uploaded"))

        return ResponsePictureData(String.format(test, success), File("test_file" + "-uploaded"))
        //return ResponsePictureData(String.format(test, success), Base64.encodeBase64String(byteArray))
    }

    @Bean
    fun multipartConfigElement(): MultipartConfigElement? {
        val factory = MultipartConfigFactory()
        factory.setMaxFileSize(DataSize.ofMegabytes(20))
        factory.setMaxRequestSize(DataSize.ofMegabytes(20))
        return factory.createMultipartConfig()
    }

}