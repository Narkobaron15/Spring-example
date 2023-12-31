package org.example.utils

import java.awt.Dimension
import java.awt.Image
import java.awt.image.BufferedImage
import java.awt.image.PixelGrabber
import java.io.File
import java.io.IOException
import java.util.*
import javax.imageio.IIOImage
import javax.imageio.ImageIO
import javax.imageio.ImageWriteParam
import javax.imageio.plugins.jpeg.JPEGImageWriteParam
import kotlin.math.ceil
import kotlin.math.min

object ImageUtils {
    const val IMAGE_UNKNOWN = -1
    const val IMAGE_JPEG = 0
    const val IMAGE_PNG = 1
    const val IMAGE_GIF = 2

    /**
     * Resizes an image.
     *
     * @param imgName   The image name to resize. Must be the complete path to the file
     * @param type      int
     * @param maxWidth  The image's max width
     * @param maxHeight The image's max height
     * @return A resized `BufferedImage`
     */
    fun resizeImage(imgName: String, type: Int, maxWidth: Int, maxHeight: Int): BufferedImage? {
        return try {
            resizeImage(ImageIO.read(File(imgName)), type, maxWidth, maxHeight)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Resizes an image.
     *
     * @param image     The image to resize
     * @param type      int
     * @param maxWidth  The image's max width
     * @param maxHeight The image's max height
     * @return A resized `BufferedImage`
     */
    fun resizeImage(image: BufferedImage, type: Int, maxWidth: Int, maxHeight: Int): BufferedImage {
        val largestDimension = Dimension(maxWidth, maxHeight)

        // Original size
        var imageWidth = image.getWidth(null)
        var imageHeight = image.getHeight(null)
        val aspectRatio = imageWidth.toFloat() / imageHeight
        if (imageWidth > maxWidth || imageHeight > maxHeight) {
            if (largestDimension.width.toFloat() / largestDimension.height > aspectRatio) {
                largestDimension.width = ceil((largestDimension.height * aspectRatio).toDouble()).toInt()
            } else {
                largestDimension.height = ceil((largestDimension.width / aspectRatio).toDouble()).toInt()
            }
            imageWidth = largestDimension.width
            imageHeight = largestDimension.height
        }
        return createHeadlessSmoothBufferedImage(image, type, imageWidth, imageHeight)
    }

    /**
     * Saves an image to the disk.
     *
     * @param image      The image to save
     * @param toFileName The filename to use
     * @param type       The image type. Use `ImageUtils.IMAGE_JPEG` to save as JPEG images,
     * or `ImageUtils.IMAGE_PNG` to save as PNG.
     * @return `false` if no appropriate writer is found
     */
    fun saveImage(image: BufferedImage?, toFileName: String, type: Int): Boolean {
        return try {
            ImageIO.write(image, if (type == IMAGE_JPEG) "jpg" else "png", File(toFileName))
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    /**
     * Compress and save an image to the disk. Currently, this method only supports JPEG images.
     *
     * @param image      The image to save
     * @param toFileName The filename to use
     * @param type       The image type. Use `ImageUtils.IMAGE_JPEG` to save as JPEG images,
     * or `ImageUtils.IMAGE_PNG` to save as PNG.
     */
    fun saveCompressedImage(image: BufferedImage?, toFileName: String, type: Int) {
        try {
            if (type == IMAGE_PNG) {
                throw UnsupportedOperationException("PNG compression not implemented")
            }
            val iter = ImageIO.getImageWritersByFormatName("jpg")
            val writer = iter.next()
            writer.setOutput(ImageIO.createImageOutputStream(File(toFileName)))
            val iwparam: ImageWriteParam = JPEGImageWriteParam(Locale.getDefault())
            iwparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT)
            iwparam.setCompressionQuality(0.7f)
            writer.write(null, IIOImage(image, null, null), iwparam)
            writer.dispose()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * Creates a `BufferedImage` from an `Image`. This method can
     * function on a completely headless system. This especially includes Linux and Unix systems
     * that do not have the X11 libraries installed, which are required for the AWT subsystem to
     * operate. The resulting image will be smoothly scaled using bilinear filtering.
     *
     * @param source The image to convert
     * @param type   int
     * @param width  The desired image width
     * @param height The desired image height
     * @return The converted image
     */
    private fun createHeadlessSmoothBufferedImage(
        source: BufferedImage,
        type: Int,
        width: Int,
        height: Int
    ): BufferedImage {
        val imgType =
            if (type == IMAGE_PNG && hasAlpha(source)) BufferedImage.TYPE_INT_ARGB else BufferedImage.TYPE_INT_RGB
        val dest = BufferedImage(width, height, imgType)
        var sourcex: Int
        var sourcey: Int
        val scalex = width.toDouble() / source.width
        val scaley = height.toDouble() / source.height
        var x1: Int
        var y1: Int
        var xdiff: Double
        var ydiff: Double
        var rgb: Int
        var rgb1: Int
        var rgb2: Int
        for (y in 0 until height) {
            sourcey = (y * source.height / dest.height.toDouble()).toInt()
            ydiff = scale(y, scaley) - sourcey
            for (x in 0 until width) {
                sourcex = (x * source.width / dest.width.toDouble()).toInt()
                xdiff = scale(x, scalex) - sourcex
                x1 = min((source.width - 1).toDouble(), (sourcex + 1).toDouble()).toInt()
                y1 = min((source.height - 1).toDouble(), (sourcey + 1).toDouble()).toInt()
                rgb1 = getRGBInterpolation(source.getRGB(sourcex, sourcey), source.getRGB(x1, sourcey), xdiff)
                rgb2 = getRGBInterpolation(source.getRGB(sourcex, y1), source.getRGB(x1, y1), xdiff)
                rgb = getRGBInterpolation(rgb1, rgb2, ydiff)
                dest.setRGB(x, y, rgb)
            }
        }
        return dest
    }

    private fun scale(point: Int, scale: Double): Double {
        return point / scale
    }

    private fun getRGBInterpolation(value1: Int, value2: Int, distance: Double): Int {
        val alpha1 = value1 and -0x1000000 ushr 24
        val red1 = value1 and 0x00FF0000 ushr 16
        val green1 = value1 and 0x0000FF00 ushr 8
        val blue1 = value1 and 0x000000FF
        val alpha2 = value2 and -0x1000000 ushr 24
        val red2 = value2 and 0x00FF0000 ushr 16
        val green2 = value2 and 0x0000FF00 ushr 8
        val blue2 = value2 and 0x000000FF
        val alpha = (alpha1 * (1.0 - distance) + alpha2 * distance + 0.5).toInt()
        val red = (red1 * (1.0 - distance) + red2 * distance + 0.5).toInt()
        val green = (green1 * (1.0 - distance) + green2 * distance + 0.5).toInt()
        val blue = (blue1 * (1.0 - distance) + blue2 * distance + 0.5).toInt()
        return alpha shl 24 or (red shl 16) or (green shl 8) or blue
    }

    /**
     * Determines if the image has transparent pixels.
     *
     * @param image The image to check for transparent pixels.
     * @return `true` of `false`, according to the result
     */
    fun hasAlpha(image: Image): Boolean {
        return try {
            val pg = PixelGrabber(image, 0, 0, 1, 1, false)
            pg.grabPixels()
            pg.colorModel.hasAlpha()
        } catch (e: InterruptedException) {
            false
        }
    }
}
