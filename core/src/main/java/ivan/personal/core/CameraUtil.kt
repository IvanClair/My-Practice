package ivan.personal.core

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.camera.core.ImageProxy
import java.io.File
import javax.inject.Inject

class CameraUtil @Inject constructor() {

    /**
     * Check the device has front facing camera or not
     */
    fun hasFrontFacingCamera(context: Context): Boolean =
        context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)

    /**
     * Get output directory for save captured image
     */
    fun getOutputDirForCapturedImage(activity: Activity): File {
        val mediaDir = activity
            .externalMediaDirs
            ?.firstOrNull()
            ?.let { File(it, activity.packageName).apply { mkdirs() } }
        return if (mediaDir?.exists() == true) mediaDir else activity.filesDir
    }
}