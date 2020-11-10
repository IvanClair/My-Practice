package ivan.personal.feature_camera_x.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import dagger.hilt.android.AndroidEntryPoint
import ivan.personal.core.setSafeOnClickListener
import ivan.personal.feature_camera_x.R
import ivan.personal.feature_camera_x.databinding.FragmentCameraPreviewBinding
import ivan.personal.feature_camera_x.viewmodel.CameraViewModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class CameraPreviewFragment : Fragment() {

    // View binding
    private lateinit var binding: FragmentCameraPreviewBinding

    // View model
    private val viewModel by navGraphViewModels<CameraViewModel>(R.id.nav_camera_x) { defaultViewModelProviderFactory }

    // Camera
    private lateinit var cameraExecutor: ExecutorService

    // region Life cycle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCameraPreviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cameraExecutor = Executors.newSingleThreadExecutor()
        initCloseButton()
        initCameraPreview()
        initPhotoButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cameraExecutor.shutdown()
    }

    // endregion

    // region Views

    private fun initCloseButton() {
        binding.buttonClose setSafeOnClickListener { viewModel.navigateBack(view = it) }
    }

    private fun initCameraPreview() {
        context?.run {
            val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
            cameraProviderFuture.addListener(
                {
                    // used to bind the lifecycle of cameras to the lifecycle owner
                    val cameraProvider = cameraProviderFuture.get()

                    // preview
                    val preview = Preview.Builder()
                        .build()
                        .also { it.setSurfaceProvider(binding.viewFinder.surfaceProvider) }

                    // Select front camera as a default
                    val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

                    try {

                        // unbind use cases before rebinding
                        cameraProvider.unbindAll()

                        // Bind use cases to camera
                        cameraProvider.bindToLifecycle(viewLifecycleOwner, cameraSelector, preview)

                    } catch (e: Exception) {
                        Log.e(this::class.java.simpleName, "Use case binding failed", e)
                    }
                },
                ContextCompat.getMainExecutor(this)
            )
        }
    }

    private fun initPhotoButton() {
        binding.buttonTakePhoto setSafeOnClickListener { }
    }

    // endregion
}