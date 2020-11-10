package ivan.personal.core

import android.content.Context
import android.content.pm.PackageManager
import javax.inject.Inject

class CameraUtil @Inject constructor() {

    /**
     * Check the device has front facing camera or not
     */
    fun hasFrontFacingCamera(context: Context): Boolean =
        context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)

}