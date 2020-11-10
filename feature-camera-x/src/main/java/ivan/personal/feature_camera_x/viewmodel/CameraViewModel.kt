package ivan.personal.feature_camera_x.viewmodel

import android.Manifest
import android.content.Context
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import ivan.personal.core.CameraUtil
import ivan.personal.core.PermissionStatus
import ivan.personal.core.createPermissionLauncher
import ivan.personal.core.createSettingsIntent

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

    fun navigateToSettings(context: Context) {
        val intent = context.createSettingsIntent()

    }

    // endregion
}