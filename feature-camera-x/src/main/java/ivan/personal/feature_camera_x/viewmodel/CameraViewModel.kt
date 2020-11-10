package ivan.personal.feature_camera_x.viewmodel

import android.content.Context
import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import ivan.personal.core.CameraUtil
import ivan.personal.feature_camera_x.view.CameraPermissionFragmentDirections

class CameraViewModel @ViewModelInject constructor(private val cameraUtil: CameraUtil) :
    ViewModel() {

    // region Camera checking

    /**
     * Check the device has front facing camera or not
     */
    fun hasFrontFacing(context: Context): Boolean =
        cameraUtil.hasFrontFacingCamera(context = context)

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