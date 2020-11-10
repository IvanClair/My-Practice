package ivan.personal.feature_camera_x.view

import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
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
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class CameraPreviewFragment : Fragment() {

    // View binding
    private lateinit var binding: FragmentCameraPreviewBinding

    // View model
    private val viewModel by navGraphViewModels<CameraViewModel>(R.id.nav_camera_x) { defaultViewModelProviderFactory }

    // Camera
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    private var imageCapture: ImageCapture? = null


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
        activity?.also { outputDirectory = viewModel.getDirForCapturedImage(activity = it) }
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

                    imageCapture = ImageCapture.Builder().build()

                    // Select front camera as a default
                    val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

                    try {

                        // unbind use cases before rebinding
                        cameraProvider.unbindAll()

                        // Bind use cases to camera
                        cameraProvider.bindToLifecycle(
                            viewLifecycleOwner,
                            cameraSelector,
                            preview,
                            imageCapture
                        )

                    } catch (e: Exception) {
                        Log.e(this::class.java.simpleName, "Use case binding failed", e)
                    }
                },
                ContextCompat.getMainExecutor(this)
            )
        }
    }

    private fun initPhotoButton() {
        binding.buttonTakePhoto setSafeOnClickListener {
            // get a stable reference of the modifiable image capture use case
            imageCapture?.run {

                // create time-stamped output file to hold the image
                val photoFile = File(outputDirectory, "${System.currentTimeMillis()}.jpj")

                // create output options object which contains file + metadata
                val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

                // set up image capture listener, which is triggered after photo has
                // been taken
                context?.also {
                    takePicture(
                        outputOptions,
                        ContextCompat.getMainExecutor(it),
                        object : ImageCapture.OnImageSavedCallback {
                            override fun onError(e: ImageCaptureException) {
                                Log.e(
                                    this::class.java.simpleName,
                                    "Photo capture failed: ${e.message}",
                                    e
                                )
                            }

                            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                                val savedUri = Uri.fromFile(photoFile)
                                val source = ImageDecoder.createSource(it.contentResolver, savedUri)
                                val bitmap = ImageDecoder.decodeBitmap(source)
                                binding.imageView.setImageBitmap(bitmap)
                            }
                        })
                }
            }
        }
    }

    // endregion
}