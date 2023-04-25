package ja.burhanrashid52.photoeditor

import android.graphics.Bitmap.CompressFormat
import androidx.annotation.IntRange


class SaveSettings private constructor(builder: Builder) {
    val isTransparencyEnabled: Boolean
    val isClearViewsEnabled: Boolean
    val compressFormat: CompressFormat
    val compressQuality: Int

    class Builder {
        var isTransparencyEnabled = true
        var isClearViewsEnabled = true
        var compressFormat = CompressFormat.PNG
        var compressQuality = 100

        fun setTransparencyEnabled(transparencyEnabled: Boolean): Builder {
            isTransparencyEnabled = transparencyEnabled
            return this
        }

        fun setClearViewsEnabled(clearViewsEnabled: Boolean): Builder {
            isClearViewsEnabled = clearViewsEnabled
            return this
        }

        fun setCompressFormat(compressFormat: CompressFormat): Builder {
            this.compressFormat = compressFormat
            return this
        }

        fun setCompressQuality(@IntRange(from = 0, to = 100) compressQuality: Int): Builder {
            this.compressQuality = compressQuality
            return this
        }

        fun build(): SaveSettings {
            return SaveSettings(this)
        }
    }

    init {
        isClearViewsEnabled = builder.isClearViewsEnabled
        isTransparencyEnabled = builder.isTransparencyEnabled
        compressFormat = builder.compressFormat
        compressQuality = builder.compressQuality
    }
}