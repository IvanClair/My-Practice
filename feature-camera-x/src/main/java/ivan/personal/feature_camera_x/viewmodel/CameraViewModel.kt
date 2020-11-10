package ivan.personal.feature_camera_x.viewmodel

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import ivan.personal.core.CameraUtil
import ivan.personal.core.PermissionHelper
import java.util.jar.Manifest

class CameraViewModel @ViewModelInject constructor(
    private val permissionHelper: PermissionHelper,
    private val cameraUtil: CameraUtil
) :
    ViewModel() {

    // region Camera checking

    /**
     * Check the device has front facing camera or not
     */
    fun hasFrontFacing(context: Context): Boolean =
        cameraUtil.hasFrontFacingCamera(context = context)

    fun isCameraPermissionGranted(context: Context): Boolean =
        permissionHelper.isPermissionGranted(
            context = context,
            permission = android.Manifest.permission.CAMERA
        )

    // endregion
}