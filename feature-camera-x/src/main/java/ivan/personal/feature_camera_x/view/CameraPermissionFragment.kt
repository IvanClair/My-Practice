package ivan.personal.feature_camera_x.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import ivan.personal.feature_camera_x.R
import ivan.personal.feature_camera_x.databinding.FragmentCameraPermissionBinding
import ivan.personal.feature_camera_x.viewmodel.CameraViewModel

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
    }

    // endregion
}