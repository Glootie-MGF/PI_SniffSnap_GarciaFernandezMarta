package com.example.pi_sniffsnap_garciafernandezmarta.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Size
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import coil.annotation.ExperimentalCoilApi
import com.example.pi_sniffsnap_garciafernandezmarta.LABEL_PATH
import com.example.pi_sniffsnap_garciafernandezmarta.MODEL_PATH
import com.example.pi_sniffsnap_garciafernandezmarta.PhotoImageActivity
import com.example.pi_sniffsnap_garciafernandezmarta.R
import com.example.pi_sniffsnap_garciafernandezmarta.api.ApiResponseStatus
import com.example.pi_sniffsnap_garciafernandezmarta.api.ApiServiceInterceptor
import com.example.pi_sniffsnap_garciafernandezmarta.auth.LoginActivity
import com.example.pi_sniffsnap_garciafernandezmarta.databinding.ActivityMainBinding
import com.example.pi_sniffsnap_garciafernandezmarta.dogdetail.DogDetailActivity
import com.example.pi_sniffsnap_garciafernandezmarta.dogdetail.DogDetailActivity.Companion.DOG_KEY
import com.example.pi_sniffsnap_garciafernandezmarta.dogdetail.DogDetailActivity.Companion.IS_RECOGNITION_KEY
import com.example.pi_sniffsnap_garciafernandezmarta.doglist.DogListActivity
import com.example.pi_sniffsnap_garciafernandezmarta.machinelearning.Classifier
import com.example.pi_sniffsnap_garciafernandezmarta.machinelearning.DogRecognition
import com.example.pi_sniffsnap_garciafernandezmarta.model.Dog
import com.example.pi_sniffsnap_garciafernandezmarta.model.User
import com.example.pi_sniffsnap_garciafernandezmarta.model.UserGoogle
import com.example.pi_sniffsnap_garciafernandezmarta.settings.SettingsActivity
import org.tensorflow.lite.support.common.FileUtil
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
@ExperimentalCoilApi
class MainActivity : AppCompatActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // startCamera()
                setupCamera()
            } else {
                Toast.makeText(
                    this, getString(R.string.accept_camera_permission),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    private lateinit var binding: ActivityMainBinding
    private lateinit var imageCapture: ImageCapture
    private lateinit var cameraExecutor: ExecutorService
    private var isCameraReady = false
    private lateinit var classifier: Classifier
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = getString(R.string.sniffsnap_by_martagf)

        // val user = User.getLoggedInUser(this)
        val user = User.getLoggedInUser(this) ?: UserGoogle.getLoggedInUser(this)
        if (user == null) {
            openLoginActivity()
            return
        } else {
            // ApiServiceInterceptor.setSessionToken(user.authenticationToken)
            ApiServiceInterceptor.setSessionToken((user as? User)?.authenticationToken ?: (user as UserGoogle).idToken)
        }

        setListeners()

        viewModel.status.observe(this){
                status ->

            when(status) {
                is ApiResponseStatus.Error -> {
                    binding.loadingWheel.visibility = View.GONE
                    Toast.makeText(this, status.messageId, Toast.LENGTH_SHORT).show()
                }
                is ApiResponseStatus.Loading -> binding.loadingWheel.visibility = View.VISIBLE
                is ApiResponseStatus.Success -> binding.loadingWheel.visibility = View.GONE
            }
        }
        viewModel.dog.observe(this){
            dog ->
            if (dog != null){
                openDogDetailActivity(dog)
            }
        }
        viewModel.dogRecognition.observe(this){
            enableTakePhotoButton(it)
        }

        requestCameraPermission()
    }

    private fun openDogDetailActivity(dog: Dog) {
        // Modificamos DogDetailActivity por DogDetailComposeActivity
        val intent = Intent(this, DogDetailActivity::class.java)
        intent.putExtra(DOG_KEY, dog)
        intent.putExtra(IS_RECOGNITION_KEY, true)
        startActivity(intent)
    }

    private fun setListeners(){
        binding.settingsFab.setOnClickListener {
            openSettingsActivity()
        }

        binding.dogListFab.setOnClickListener {
            openDogListActivity()
        }

        binding.takePhotoFab.setOnClickListener {
            if (isCameraReady){
                takePhoto()
            }
        }
    }

    private fun openDogListActivity() {
        startActivity(Intent(this, DogListActivity::class.java))
    }

    private fun openSettingsActivity() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    private fun openLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                // startCamera()
                setupCamera()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this, Manifest.permission.CAMERA
            ) -> {
                AlertDialog.Builder(this)
                    .setTitle(R.string.camera_permission_rationale_dialog_title)
                    .setMessage(R.string.camera_permission_rationale_dialog_message)
                    .setPositiveButton(android.R.string.ok) { _, _ ->
                        requestPermissionLauncher.launch(
                            Manifest.permission.CAMERA
                        )
                    }
                    .setNegativeButton(android.R.string.cancel) { _, _ ->
                    }.show()
            }

            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                requestPermissionLauncher.launch(
                    Manifest.permission.CAMERA
                )
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider = cameraProviderFuture.get()
            // Preview
            val preview = Preview.Builder().build()
            preview.setSurfaceProvider(binding.cameraPreview.surfaceProvider)

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            val imageAnalysis = ImageAnalysis.Builder()
                // enable the following line if RGBA output is needed.
                // .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                .setTargetResolution(Size(1280, 720))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
            imageAnalysis.setAnalyzer(cameraExecutor) { imageProxy ->
                val rotationDegrees = imageProxy.imageInfo.rotationDegrees

                /*val bitmap = convertImageProxyToBitmap(imageProxy)
                if (bitmap != null){
                    val dogRecognition = classifier.recognizeImage(bitmap)
                        .first()
                    enableTakePhotoButton(dogRecognition)
                }*/
                viewModel.recognizeImage(imageProxy)
                // imageProxy.close() // No se puede cerrar ya aquí porque recognizeImage se ejecuta en un thread diferente
            }
            // Bind use cases to camera
            cameraProvider.bindToLifecycle(
                this, cameraSelector,
                preview, imageCapture, imageAnalysis
            )
        }, ContextCompat.getMainExecutor(this))
    }

    private fun enableTakePhotoButton(dogRecognition: DogRecognition) {
        if (dogRecognition.confidence > 70.0){
            binding.takePhotoFab.alpha = 1f
            binding.takePhotoFab.setOnClickListener {
                viewModel.getDogByMlId(dogRecognition.id)
            }
        } else { // Botón deshabilitado
            binding.takePhotoFab.alpha = 0.2f
            binding.takePhotoFab.setOnClickListener(null)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.setupClassifier(
            FileUtil.loadMappedFile(this@MainActivity, MODEL_PATH),
            FileUtil.loadLabels(this@MainActivity, LABEL_PATH)
        )
    }
    override fun onDestroy() {
        super.onDestroy()
        if (::cameraExecutor.isInitialized) {
            cameraExecutor.shutdown()
        }
    }

    private fun setupCamera() {
        binding.cameraPreview.post {
            imageCapture = ImageCapture.Builder()
                .setTargetRotation(binding.cameraPreview.display.rotation)
                .build()
            cameraExecutor = Executors.newSingleThreadExecutor()
            startCamera()
            isCameraReady = true
        } // Para asegurar que está listo el view
    }

    private fun takePhoto() {
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(getOutputPhotoFile()).build()
        imageCapture.takePicture(outputFileOptions, cameraExecutor,
            object : ImageCapture.OnImageSavedCallback{
                override fun onError(error: ImageCaptureException) {
                    Toast.makeText(
                        this@MainActivity,
                        "ERROR Taking photo ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val photoUri = outputFileResults.savedUri
                    /*val classifier = Classifier(
                        FileUtil.loadMappedFile(this@MainActivity, MODEL_PATH),
                        FileUtil.loadLabels(this@MainActivity, LABEL_PATH)
                    )*/

                    val bitmap = BitmapFactory.decodeFile(photoUri?.path)
                    // classifier.recognizeImage(bitmap)
                    val dogRecognition = classifier.recognizeImage(bitmap)
                        .first() // Al estar ordenados ya, nos quedamos con el 1º que es el que más fiabilidad tiene

                    // openPhotoImageActivity(photoUri.toString())
                    // Ya no queremos que abra esta Activity, sino la ficha de detalles de la raza del perro detectado

                    viewModel.getDogByMlId(dogRecognition.id) // Para descargar el perro - No lo vamos a necesitar
                                                                // una vez implementado el analizador antes de tomar la foto
                }
            })
    }

    private fun getOutputPhotoFile(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name) + ".jpg").apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists()) {
            mediaDir
        } else {
            filesDir
        }
    }

    private fun openPhotoImageActivity(photoUri: String) {
        val intent = Intent(this, PhotoImageActivity::class.java)
        intent.putExtra(PhotoImageActivity.PHOTO_URI_KEY, photoUri)
        startActivity(intent)
    } // No lo vamos a utilizar una vez implementado que se vaya a la pantalla de detalles
    // directamente después de echar la foto y reconocer la raza

}