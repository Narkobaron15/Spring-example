package org.example.mappers

import org.mapstruct.Named

interface ImageMapper {
    companion object {
        @JvmStatic
        @Named("image_xs")
        fun imageXs(filename: String): String {
            return image(filename, SIZE_XS)
        }

        @JvmStatic
        @Named("image_sm")
        fun imageSm(filename: String): String {
            return image(filename, SIZE_SM)
        }

        @JvmStatic
        @Named("image_md")
        fun imageMd(filename: String): String {
            return image(filename, SIZE_MD)
        }

        @JvmStatic
        @Named("image_lg")
        fun imageLg(filename: String): String {
            return image(filename, SIZE_LG)
        }

        @JvmStatic
        @Named("image_xl")
        fun imageXl(filename: String): String {
            return image(filename, SIZE_XL)
        }

        private fun image(filename: String, size: Int): String {
            return "http://localhost:8081/uploads/" + size + "_" + filename
        }

        protected const val SIZE_XS = 32
        protected const val SIZE_SM = 150
        protected const val SIZE_MD = 300
        protected const val SIZE_LG = 600
        protected const val SIZE_XL = 1200
    }
}
