package ivan.personal.feature_camera_x.viewmodel

import android.content.Context
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
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

    /**
     * Check the status camera permission
     */
    fun checkCameraPermissionStatus(activity: AppCompatActivity): Int =
        permissionHelper.checkPermissionStatus(
            activity = activity,
            permission = android.Manifest.permission.CAMERA
        )

    // endregion

    // region Navigation

    /**
     * Back to previous screen
     */
    fun navigateBack(view: View?) {
        view?.findNavController()?.navigateUp()
    }

    // endregion
}