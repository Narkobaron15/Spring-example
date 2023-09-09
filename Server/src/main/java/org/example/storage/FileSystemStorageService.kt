package org.example.storage

import net.coobird.thumbnailator.Thumbnails
import org.example.storage.exceptions.StorageException
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.io.File
import java.io.IOException
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.InvalidPathException
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import javax.imageio.ImageIO

@Service
open class FileSystemStorageService(props: StorageProperties) : StorageService {
    private val rootLocation: Path

    init {
        rootLocation = Paths.get(props.location)
    }

    override fun init() {
        try {
            if (!Files.exists(rootLocation)) Files.createDirectories(rootLocation)
        } catch (e: IOException) {
            throw StorageException("Could not initialize storage", e)
        }
    }

    override fun loadAsResource(filename: String): Resource {
        return try {
            val file = load(filename)
            val resource: Resource = UrlResource(file.toUri())
            if (!resource.exists() || !resource.isReadable) {
                throw StorageException("Проблем читання файлу: $filename")
            }
            resource
        } catch (e: InvalidPathException) {
            throw StorageException("Файл не знайдено: $filename", e)
        } catch (e: MalformedURLException) {
            throw StorageException("Файл не знайдено: $filename", e)
        }
    }

    override fun store(base64: String): String {
        if (base64.isEmpty()) {
            throw StorageException("Пустий base64")
        }
        val charArray = base64.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val extension = if (charArray[0] == "data:image/png;base64") "png" else "jpg"
        val bytes = Base64.getDecoder().decode(charArray[1])
        return store(bytes, extension)
    }

    override fun delete(filename: String) {
        for (size in imageSizes) {
            val filePath = load(size.toString() + "_" + filename)
            val file = File(filePath.toString())
            if (file.delete()) {
                println("$filename Файл видалено.")
            } else {
                println("$filename Файл не знайдено.")
            }
        }
    }

    override fun load(filename: String): Path {
        return rootLocation.resolve(filename)
    }

    /**
     * Stores an image locally
     * @param file Image to store as MultipartFile
     * @return Name of the file
     */
    override fun store(file: MultipartFile): String {
        return try {
            store(file, FileFormat.WEBP)
        } catch (e: IOException) {
            throw StorageException("Проблема перетворення та збереження base64", e)
        }
    }

    /**
     * Stores an image locally
     * @param bytes Image to store as BLOB
     * @param extension Image's format
     * @return Name of the file
     */
    override fun store(bytes: ByteArray, extension: String): String {
        return try {
            val uuid = UUID.randomUUID()
            val randomFileName = "$uuid.$extension"
            val byteStream = ByteArrayInputStream(bytes)
            val image = ImageIO.read(byteStream)
            for (size in imageSizes) {
                val outputPath = rootLocation.toString() + "/" + size + "_" + randomFileName
                //                BufferedImage newImgBuf = ImageUtils.resizeImage(image, extension.equals("jpg") ? ImageUtils.IMAGE_JPEG : ImageUtils.IMAGE_PNG, size, size);
//                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                ImageIO.write(newImgBuf, extension, byteArrayOutputStream);
//                byte[] newBytes = byteArrayOutputStream.toByteArray();
//                FileOutputStream out = new FileOutputStream(outputPath);
//                out.write(newBytes);
//                out.close();
                Thumbnails.of(image)
                    .size(size, size)
                    .outputFormat(extension)
                    .toFile(outputPath)
            }
            randomFileName
        } catch (e: IOException) {
            throw StorageException("Проблема перетворення та збереження картинки", e)
        }
    }

    /**
     * Stores an image locally
     * @param file Image to store
     * @param saveFormat Image's format
     * @return Name of the file
     */
    override fun store(file: MultipartFile, saveFormat: FileFormat): String {
        return try {
            store(file.bytes, saveFormat.name.lowercase(Locale.getDefault()))
        } catch (e: IOException) {
            throw StorageException("Проблема перетворення та збереження картинки", e)
        }
    }

    companion object {
        private val imageSizes = intArrayOf(32, 150, 300, 600, 1200)
    }
}
