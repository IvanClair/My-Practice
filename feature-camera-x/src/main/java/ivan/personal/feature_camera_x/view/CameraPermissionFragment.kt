package ivan.personal.feature_camera_x.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.navGraphViewModels
import dagger.hilt.android.AndroidEntryPoint
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

    // region Life cycle

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
        initMessage()
        initCloseButton()
    }

    // endregion

    // region Views

    @SuppressLint("SetTextI18n")
    private fun initMessage() {
        context?.run {

            fun setMessage(message: String) {
                binding.textViewMessage.text = message
            }

            // check front facing
            if (!viewModel.hasFrontFacing(context = this)) {
                setMessage(message = "Your device does not have front camera")
                return
            }

            // check camera permission
            if (!viewModel.isCameraPermissionGranted(context = this)) {
                setMessage(message = "Tap the button below to provide camera permission")
                return
            }
        }
    }

    private fun initCloseButton() {
        binding.buttonClose.setSafeOnClickListener { viewModel.navigateBack(view = it) }
    }

    // endregion
}