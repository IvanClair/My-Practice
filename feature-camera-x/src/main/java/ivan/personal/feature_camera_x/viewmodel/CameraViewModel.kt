package ivan.personal.feature_camera_x.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import ivan.personal.core.PermissionHelper

class CameraViewModel @ViewModelInject constructor(private val permissionHelper: PermissionHelper) :
    ViewModel() {
}