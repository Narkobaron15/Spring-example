package org.example.mappers

import org.mapstruct.Named

interface ImageMapper {
    companion object {
        private const val sizeXs = 32
        private const val sizeSm = 150
        private const val sizeMd = 300
        private const val sizeLg = 600
        private const val sizeXl = 1200

        @JvmStatic
        @Named("image_xs")
        fun imageXs(filename: String): String {
            return image(filename, sizeXs)
        }

        @JvmStatic
        @Named("image_sm")
        fun imageSm(filename: String): String {
            return image(filename, sizeSm)
        }

        @JvmStatic
        @Named("image_md")
        fun imageMd(filename: String): String {
            return image(filename, sizeMd)
        }

        @JvmStatic
        @Named("image_lg")
        fun imageLg(filename: String): String {
            return image(filename, sizeLg)
        }

        @JvmStatic
        @Named("image_xl")
        fun imageXl(filename: String): String {
            return image(filename, sizeXl)
        }

        @JvmStatic
        fun image(filename: String, size: Int): String {
            return "http://localhost:8081/uploads/" + size + "_" + filename
        }
    }
}