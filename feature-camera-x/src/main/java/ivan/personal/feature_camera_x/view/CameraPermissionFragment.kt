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
import ivan.personal.core.*
import ivan.personal.feature_camera_x.R
import ivan.personal.feature_camera_x.databinding.FragmentCameraPermissionBinding
import ivan.personal.feature_camera_x.viewmodel.CameraViewModel

@AndroidEntryPoint
class CameraPermissionFragment : Fragment() {

    // View binding
    private lateinit var binding: FragmentCameraPermissionBinding

    // View model
    private val viewModel by navGraphViewModels<CameraViewModel>(R.id.nav_camera_x) { defaultViewModelProviderFactory }

    // Permission launcher
    private lateinit var cameraPermissionLauncher: ActivityResultLauncher<String>

    // region Life cycle

    override fun onAttach(context: Context) {
        super.onAttach(context)
        cameraPermissionLauncher =
            (context as AppCompatActivity).createPermissionLauncher(permission = Manifest.permission.CAMERA) {
                updateMessage(status = it)
                updateGoToSettingButton(status = it)
                updateProvidePermissionButton(status = it)
                updateStartButton(status = it)
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
        // initial views
        initCloseButton()
        initMessage()
        initGoToSettingButton()
        initProvidePermissionButton()
        initStartButton()
        // start request permission
        Handler(Looper.getMainLooper()).postDelayed({ launchPermissionRequest() }, 1000)
    }

    // endregion

    // region Views

    private fun initCloseButton() {
        binding.buttonClose.setSafeOnClickListener { viewModel.navigateBack(view = it) }
    }

    private fun initMessage() {
        val frontFacing = viewModel.hasFrontFacing(context = binding.root.context)
        binding.textViewMessage.text =
            if (!frontFacing) "Your device does not have front camera"
            else "Checking camera permission ..."
    }

    @SuppressLint("SetTextI18n")
    private fun updateMessage(@PermissionStatus status: Int) {
        binding.textViewMessage.text = when (status) {
            PERMISSION_STATUS_RATIONALE -> "To try the feature, you have to tap the button below to provide camera permission"
            PERMISSION_STATUS_DENIED -> "Since you denied the permission before, you have to activate camera permission in settings manually"
            else -> "All good, you can start by taping the button below"
        }
    }

    private fun initGoToSettingButton() {
        binding.buttonGoToSetting.apply {
            visibility = View.GONE
            setSafeOnClickListener { }
        }
    }

    private fun updateGoToSettingButton(@PermissionStatus status: Int) {
        binding.buttonGoToSetting.visibility = when (status) {
            PERMISSION_STATUS_GRANTED, PERMISSION_STATUS_RATIONALE -> View.GONE
            else -> View.VISIBLE
        }
    }

    private fun initProvidePermissionButton() {
        binding.buttonProvidePermission.apply {
            visibility = View.GONE
            setSafeOnClickListener { launchPermissionRequest() }
        }
    }

    private fun updateProvidePermissionButton(@PermissionStatus status: Int) {
        binding.buttonProvidePermission.visibility = when (status) {
            PERMISSION_STATUS_GRANTED, PERMISSION_STATUS_DENIED -> View.GONE
            else -> View.VISIBLE
        }
    }

    private fun initStartButton() {
        binding.buttonStart.apply {
            visibility = View.GONE
            setSafeOnClickListener { }
        }
    }

    private fun updateStartButton(@PermissionStatus status: Int) {
        binding.buttonStart.visibility = when (status) {
            PERMISSION_STATUS_GRANTED -> View.VISIBLE
            else -> View.GONE
        }
    }

    // endregion

    // region Request permission

    private fun launchPermissionRequest() {
        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    // endregion
}