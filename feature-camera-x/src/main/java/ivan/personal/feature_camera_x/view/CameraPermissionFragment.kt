package ivan.personal.feature_camera_x.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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

    // Launcher
    private lateinit var cameraPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var settingsLauncher: ActivityResultLauncher<Intent>

    // region Life cycle

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // permission launcher
        cameraPermissionLauncher =
            createPermissionLauncher(permission = Manifest.permission.CAMERA) { updateViews(status = it) }

        // settings launcher
        settingsLauncher = createLauncherForResult {
            activity?.run {
                updateViews(
                    status = checkPermissionStatus(permission = Manifest.permission.CAMERA)
                )
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
        // initial views
        initialViews()
        // start request permission
        Handler(Looper.getMainLooper()).postDelayed({ launchPermissionRequest() }, 1000)
    }

    // endregion

    // region Views - initial

    private fun initialViews() {
        initCloseButton()
        initMessage()
        initGoToSettingButton()
        initProvidePermissionButton()
        initStartButton()
    }

    private fun initCloseButton() {
        binding.buttonClose.setSafeOnClickListener { viewModel.navigateBack(view = it) }
    }

    private fun initMessage() {
        val frontFacing = viewModel.hasFrontFacing(context = binding.root.context)
        binding.textViewMessage.text =
            if (!frontFacing) "Your device does not have front camera"
            else "Checking camera permission ..."
    }

    private fun initGoToSettingButton() {
        binding.buttonGoToSetting.apply {
            visibility = View.GONE
            setSafeOnClickListener { settingsLauncher.launch(context.createSettingsIntent()) }
        }
    }

    private fun initProvidePermissionButton() {
        binding.buttonProvidePermission.apply {
            visibility = View.GONE
            setSafeOnClickListener { launchPermissionRequest() }
        }
    }

    private fun initStartButton() {
        binding.buttonStart.apply {
            visibility = View.GONE
            setSafeOnClickListener { viewModel.navigateToCameraPreview(view = it) }
        }
    }

    // endregion

    // region Views - update

    private fun updateViews(@PermissionStatus status: Int) {
        updateMessage(status = status)
        updateGoToSettingButton(status = status)
        updateProvidePermissionButton(status = status)
        updateStartButton(status = status)
    }

    @SuppressLint("SetTextI18n")
    private fun updateMessage(@PermissionStatus status: Int) {
        binding.textViewMessage.text = when (status) {
            PERMISSION_STATUS_RATIONALE -> "To try the feature, you have to tap the button below to provide camera permission"
            PERMISSION_STATUS_DENIED -> "Since you denied the permission before, you have to activate camera permission in settings manually"
            else -> "All good, you can start by taping the button below"
        }
    }

    private fun updateGoToSettingButton(@PermissionStatus status: Int) {
        binding.buttonGoToSetting.visibility = when (status) {
            PERMISSION_STATUS_GRANTED, PERMISSION_STATUS_RATIONALE -> View.GONE
            else -> View.VISIBLE
        }
    }

    private fun updateProvidePermissionButton(@PermissionStatus status: Int) {
        binding.buttonProvidePermission.visibility = when (status) {
            PERMISSION_STATUS_GRANTED, PERMISSION_STATUS_DENIED -> View.GONE
            else -> View.VISIBLE
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