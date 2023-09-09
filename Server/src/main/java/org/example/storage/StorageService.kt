package org.example.storage

import org.springframework.core.io.Resource
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Path

interface StorageService {
    fun init()
    fun store(file: MultipartFile): String
    fun store(base64: String): String
    fun store(bytes: ByteArray, extension: String): String
    fun store(file: MultipartFile, saveFormat: FileFormat): String
    fun load(filename: String): Path
    fun loadAsResource(filename: String): Resource
    fun delete(filename: String)
    // fun deleteAll();
}
