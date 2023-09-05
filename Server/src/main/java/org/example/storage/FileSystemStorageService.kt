package org.example.storage

import org.example.storage.exceptions.StorageException
import org.example.utils.ImageUtils
import org.example.utils.ImageUtils.resizeImage
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.*
import java.nio.file.Files
import java.nio.file.InvalidPathException
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import javax.imageio.ImageIO

@Service
class FileSystemStorageService(props: StorageProperties) : StorageService {
    private val rootLocation: Path = Paths.get(props.location)

    companion object {
        @JvmStatic
        val imageSizes = intArrayOf(32, 150, 300, 600, 1200)
    }

    override fun init() {
        try {
            if (!Files.exists(rootLocation))
                Files.createDirectories(rootLocation)
        } catch (e: IOException) {
            throw StorageException("Could not initialize storage", e)
        }
    }

    override fun loadAsResource(filename: String): Resource {
        return try {
            val file = load(filename)
            val resource: Resource = UrlResource(file.toUri())
            if (!resource.exists() || resource.isReadable) resource else throw StorageException("Проблем читання файлу: $filename")
        } catch (e: InvalidPathException) {
            throw StorageException("Файл не знайдено: $filename", e)
        }
    }

    override fun store(base64: String): String {
        if (base64.isEmpty()) {
            throw StorageException("Пустий base64")
        }
        // розділяємо код картинки на дві частини, відділяємо розширення
        val charArray = base64.split(",".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        println("-----------------" + charArray[0])
        val extension: String = when (charArray[0]) {
            "data:image/png;base64" -> "png"
            else -> "jpg"
        }
        val decoder: Base64.Decoder = Base64.getDecoder() //створюємо екземпляр декодера
        val bytes: ByteArray = decoder.decode(charArray[1])  // декодуємо Base64 до вайтів
        return store(bytes, extension)
    }

    override fun delete(filename: String) {
        for (size in imageSizes) {
            val filePath = load(size.toString() + "_" + filename)
            val file = File(filePath.toString())
            if (file.delete()) {
                println("$filename Файл видалено.")
            } else println("$filename Файл не знайдено.")
        }
    }

    override fun load(filename: String): Path {
        return rootLocation.resolve(filename)
    }

    override fun store(file: MultipartFile): String {
        val extension = "jpg"
        return store(file.bytes, extension)
    }

    override fun store(bytes: ByteArray, extension: String): String {
        return try {
            val uuid: UUID = UUID.randomUUID()
            val randomFileName = "$uuid.$extension" //робимо ім'я файліка: унікальне ім'я + розширення
            ByteArrayInputStream(bytes).use { byteStream ->
                val image = ImageIO.read(byteStream)
                for (size in imageSizes) { // в циклі створюємо фотки кожного розміру
                    val directory = "$rootLocation/${size}_$randomFileName" //створюємо папку де фотка буде зберігатися
                    //створюємо буфер для нової фотографії, де важливо вказуємо розширення яке буде у фотки та розмір (32х32, 150х150)
                    //по типу оперативна пам'ять
                    val newImg = resizeImage(
                        image,
                        if (extension == "jpg") ImageUtils.IMAGE_JPEG else ImageUtils.IMAGE_PNG,
                        size,
                        size
                    )
                    val byteArrayOutputStream = ByteArrayOutputStream() //створюємо Stream
                    //фото записуємо у потік для отримання масиву байтів
                    ImageIO.write(
                        newImg,
                        extension,
                        byteArrayOutputStream
                    ) //за допомогою цього Stream записуємо в буфер фотографію згідно з розширенням
                    val newBytes: ByteArray =
                        byteArrayOutputStream.toByteArray() //з цього Stream знову отримуємо байти
                    val out = FileOutputStream(directory)
                    out.write(newBytes) //байти зберігаємо у фійлову систему на сервері
                    out.close()
                }
            }
            randomFileName
        } catch (e: IOException) {
            throw StorageException("Проблема перетворення та збереження base64", e)
        }
    }
}