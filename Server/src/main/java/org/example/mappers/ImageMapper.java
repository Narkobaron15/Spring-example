package org.example.mappers;

import org.mapstruct.Named;

public interface ImageMapper {
    int SIZE_XS = 32;
    int SIZE_SM = 150;
    int SIZE_MD = 300;
    int SIZE_LG = 600;
    int SIZE_XL = 1200;

    @Named("image_xs")
    static String imageXs(String filename) {
        return image(filename, SIZE_XS);
    }

    @Named("image_sm")
    static String imageSm(String filename) {
        return image(filename, SIZE_SM);
    }

    @Named("image_md")
    static String imageMd(String filename) {
        return image(filename, SIZE_MD);
    }

    @Named("image_lg")
    static String imageLg(String filename) {
        return image(filename, SIZE_LG);
    }

    @Named("image_xl")
    static String imageXl(String filename) {
        return image(filename, SIZE_XL);
    }

    private static String image(String filename, int size) {
        return "http://localhost:8081/uploads/" + size + "_" + filename;
    }
}
