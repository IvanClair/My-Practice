package ivan.personal.feature_camera_x.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import dagger.hilt.android.AndroidEntryPoint
import ivan.personal.core.PermissionHelper
import ivan.personal.core.createPermissionLauncher
import ivan.personal.core.setSafeOnClickListener
import ivan.personal.feature_camera_x.R
import ivan.personal.feature_camera_x.databinding.FragmentCameraPermissionBinding
import ivan.personal.feature_camera_x.viewmodel.CameraViewModel

@AndroidEntryPoint
class CameraPermissionFragment : Fragment() {

    // View binding
    private lateinit var binding: FragmentCameraPermissionBinding

    // View model
    private val viewModel by navGraphViewModels<CameraViewModel>(R.id.nav_camera_x) { defaultViewModelProviderFactory }

    // Camera
    private var frontFacing: Boolean = false
    private lateinit var cameraPermissionLauncher: ActivityResultLauncher<String>

    // region Life cycle

    override fun onAttach(context: Context) {
        super.onAttach(context)
        cameraPermissionLauncher = createPermissionLauncher {
            (activity as? AppCompatActivity)?.run {
                val cameraPermissionStatus = viewModel.checkCameraPermissionStatus(activity = this)
                updateMessage(permissionStatus = cameraPermissionStatus)
                updateGoToSettingButton(permissionStatus = cameraPermissionStatus)
                updateProvidePermissionButton(permissionStatus = cameraPermissionStatus)
                updateStartButton(permissionStatus = cameraPermissionStatus)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCameraPermissionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // check front facing
        frontFacing = viewModel.hasFrontFacing(context = view.context)
        // initial views
        initCloseButton()
        initMessage()
        initGoToSettingButton()
        initProvidePermissionButton()
        initStartButton()
        // start request permission
        Handler(Looper.getMainLooper()).postDelayed({ startRequestPermission() }, 1000)
    }

    // endregion

    // region Views

    private fun initCloseButton() {
        binding.buttonClose.setSafeOnClickListener { viewModel.navigateBack(view = it) }
    }

    private fun initMessage() {
        binding.textViewMessage.text =
            if (!frontFacing) "Your device does not have front camera"
            else "Checking camera permission ..."
    }

    @SuppressLint("SetTextI18n")
    private fun updateMessage(@PermissionHelper.Companion.PermissionStatus permissionStatus: Int) {
        binding.textViewMessage.text = when (permissionStatus) {
            PermissionHelper.PERMISSION_STATUS_RATIONALE -> "Tap the button below to provide camera permission"
            PermissionHelper.PERMISSION_STATUS_DENIED -> "Since you denied the permission before, you have to go to setting to active camera permission"
            else -> "All good, you can start"
        }
    }

    private fun initGoToSettingButton() {
        binding.buttonGoToSetting.apply {
            visibility = View.GONE
            setSafeOnClickListener { }
        }
    }

    private fun updateGoToSettingButton(@PermissionHelper.Companion.PermissionStatus permissionStatus: Int) {
        binding.buttonGoToSetting.visibility = when (permissionStatus) {
            PermissionHelper.PERMISSION_STATUS_GRANTED, PermissionHelper.PERMISSION_STATUS_RATIONALE -> View.GONE
            else -> View.VISIBLE
        }
    }

    private fun initProvidePermissionButton() {
        binding.buttonProvidePermission.apply {
            visibility = View.GONE
            setSafeOnClickListener { }
        }
    }

    private fun updateProvidePermissionButton(@PermissionHelper.Companion.PermissionStatus permissionStatus: Int) {
        binding.buttonProvidePermission.visibility = when (permissionStatus) {
            PermissionHelper.PERMISSION_STATUS_GRANTED, PermissionHelper.PERMISSION_STATUS_DENIED -> View.GONE
            else -> View.VISIBLE
        }
    }

    private fun initStartButton() {
        binding.buttonStart.apply {
            visibility = View.GONE
            setSafeOnClickListener { }
        }
    }

    private fun updateStartButton(@PermissionHelper.Companion.PermissionStatus permissionStatus: Int) {
        binding.buttonStart.visibility = when (permissionStatus) {
            PermissionHelper.PERMISSION_STATUS_GRANTED -> View.VISIBLE
            else -> View.GONE
        }
    }

    // endregion

    // region

    private fun startRequestPermission() {
        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    // endregion
}