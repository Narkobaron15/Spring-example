package org.example.storage;

import org.example.storage.exceptions.StorageException;
import org.example.utils.ImageUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
public class FileSystemStorageService implements StorageService {
    private final Path rootLocation;

    private static final int[] imageSizes = {32, 150, 300, 600, 1200};

    public FileSystemStorageService(StorageProperties props) {
        this.rootLocation = Paths.get(props.getLocation());
    }

    @Override
    public void init() {
        try {
            if (!Files.exists(rootLocation))
                Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                throw new StorageException("Проблем читання файлу: " + filename);
            }
            return resource;
        } catch (InvalidPathException | MalformedURLException e) {
            throw new StorageException("Файл не знайдено: " + filename, e);
        }
    }

    @Override
    public String store(String base64) {
        if (base64.isEmpty()) {
            throw new StorageException("Пустий base64");
        }
        String[] charArray = base64.split(",");
        String extension = charArray[0].equals("data:image/png;base64") ? "png" : "jpg";
        byte[] bytes = Base64.getDecoder().decode(charArray[1]);
        return store(bytes, extension);
    }

    @Override
    public void delete(String filename) {
        for (int size : imageSizes) {
            Path filePath = load(size + "_" + filename);
            File file = new File(filePath.toString());
            if (file.delete()) {
                System.out.println(filename + " Файл видалено.");
            } else {
                System.out.println(filename + " Файл не знайдено.");
            }
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public String store(MultipartFile file) {
        String extension = "jpg";

        try {
            return store(file.getBytes(), extension);
        } catch (IOException e) {
            throw new StorageException("Проблема перетворення та збереження base64", e);
        }
    }

    @Override
    public String store(byte[] bytes, String extension) {
        try {
            UUID uuid = UUID.randomUUID();
            String randomFileName = uuid + "." + extension;
            ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
            BufferedImage image = ImageIO.read(byteStream);
            for (int size : imageSizes) {
                String directory = rootLocation + "/" + size + "_" + randomFileName;
                BufferedImage newImg = ImageUtils.resizeImage(image, extension.equals("jpg") ? ImageUtils.IMAGE_JPEG : ImageUtils.IMAGE_PNG, size, size);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(newImg, extension, byteArrayOutputStream);
                byte[] newBytes = byteArrayOutputStream.toByteArray();
                FileOutputStream out = new FileOutputStream(directory);
                out.write(newBytes);
                out.close();
            }
            return randomFileName;
        } catch (IOException e) {
            throw new StorageException("Проблема перетворення та збереження base64", e);
        }
    }
}
