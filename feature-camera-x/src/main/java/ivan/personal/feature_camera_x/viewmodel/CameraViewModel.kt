package ivan.personal.feature_camera_x.viewmodel

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import ivan.personal.core.CameraUtil
import ivan.personal.feature_camera_x.view.CameraPermissionFragmentDirections
import java.io.File

class CameraViewModel @ViewModelInject constructor(private val cameraUtil: CameraUtil) :
    ViewModel() {

    // region Camera checking

    /**
     * Check the device has front facing camera or not
     */
    fun hasFrontFacing(context: Context): Boolean =
        cameraUtil.hasFrontFacingCamera(context = context)

    /**
     * Get dir to save captured image
     */
    fun getDirForCapturedImage(activity: Activity): File =
        cameraUtil.getOutputDirForCapturedImage(activity = activity)

    // endregion

    // region Navigation

    /**
     * Back to previous screen
     */
    fun navigateBack(view: View?) {
        view?.findNavController()?.navigateUp()
    }

    /**
     * Navigate to camera preview
     */
    fun navigateToCameraPreview(view: View?) {
        view?.findNavController()?.navigate(CameraPermissionFragmentDirections.toCameraPreview())
    }

    // endregion
}