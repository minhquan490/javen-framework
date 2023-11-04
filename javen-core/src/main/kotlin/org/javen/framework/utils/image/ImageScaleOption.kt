package org.javen.framework.utils.image

import java.awt.Image

enum class ImageScaleOption private constructor(var width: Int, var height: Int, var hint: Int) {
    DEFAULT(300, 300, Image.SCALE_SMOOTH);

    companion object {
        fun of(width: Int, height: Int, hint: Int): ImageScaleOption {
            val result = DEFAULT
            result.width = width
            result.height = height
            result.hint = hint
            return result
        }

        fun of(width: Int, height: Int): ImageScaleOption {
            return of(width, height, DEFAULT.hint)
        }
    }
}