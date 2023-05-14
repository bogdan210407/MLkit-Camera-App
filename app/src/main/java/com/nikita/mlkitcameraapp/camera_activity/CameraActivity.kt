package com.nikita.mlkitcameraapp.camera_activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.google.common.util.concurrent.ListenableFuture
import com.nikita.mlkitcameraapp.R
import com.nikita.mlkitcameraapp.helpers.ImageAnalyser

class CameraActivity: AppCompatActivity() {
    private var cameraProvider: ProcessCameraProvider? = null
    private lateinit var cameraFuture: ListenableFuture<ProcessCameraProvider>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_layout)
        cameraFuture = ProcessCameraProvider.getInstance(this)
        cameraFuture.addListener({
            cameraProvider = cameraFuture.get()
            bindPreview()
        }, ContextCompat.getMainExecutor(this))
    }
    private fun bindPreview(){
        val preview = Preview.Builder().build()
        val surface = findViewById<PreviewView>(R.id.preview_view)
        preview.setSurfaceProvider(surface.surfaceProvider)

        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        val imageAnalyser = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
        imageAnalyser.setAnalyzer(
            mainExecutor,
            ImageAnalyser()
        )

        cameraProvider?.bindToLifecycle(this, cameraSelector, preview, imageAnalyser)
    }

}