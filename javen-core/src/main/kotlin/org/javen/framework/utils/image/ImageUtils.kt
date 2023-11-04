package org.javen.framework.utils.image

import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import javax.imageio.ImageIO

class ImageUtils private constructor() {
    companion object {
        fun resizeImage(targetPath: String): ByteArray {
            if (!isExist(targetPath)) {
                return ByteArray(0)
            }
            val division = 5
            return try {
                val imageTargetPath = Paths.get(targetPath)
                val imageFile = imageTargetPath.toFile()
                val inputImage = ImageIO.read(imageFile)
                val height = inputImage.height / division
                val width = inputImage.width / division
                resizeImage(targetPath, ImageScaleOption.of(width, height))
            } catch (e: IOException) {
                ByteArray(0)
            }
        }

        fun resizeImage(targetPath: String?, resizeOption: ImageScaleOption): ByteArray {
            return if (!isExist(targetPath!!)) {
                ByteArray(0)
            } else try {
                val imageTargetPath = Paths.get(targetPath)
                val imageFile = imageTargetPath.toFile()
                val inputImage = ImageIO.read(imageFile)
                val transferredImage =
                    inputImage.getScaledInstance(resizeOption.width, resizeOption.height, resizeOption.hint)
                val outputImage = BufferedImage(resizeOption.width, resizeOption.height, BufferedImage.TYPE_INT_BGR)
                val graphics = outputImage.graphics
                graphics.drawImage(transferredImage, 0, 0, null)
                val outputStream = ByteArrayOutputStream()
                val contentType = Files.probeContentType(imageTargetPath)
                ImageIO.write(outputImage,
                    contentType.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1], outputStream
                )
                outputStream.toByteArray()
            } catch (e: IOException) {
                ByteArray(0)
            }
        }

        private fun isExist(path: String): Boolean {
            val imageTargetPath = Paths.get(path)
            return Files.exists(imageTargetPath)
        }
    }
}