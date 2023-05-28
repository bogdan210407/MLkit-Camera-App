package com.nikita.mlkitcameraapp.helpers

import android.annotation.SuppressLint
import android.util.Size
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.PoseDetector
import com.google.mlkit.vision.pose.PoseDetectorOptionsBase
import com.google.mlkit.vision.pose.PoseLandmark
import com.google.mlkit.vision.pose.accurate.AccuratePoseDetectorOptions
import com.nikita.mlkitcameraapp.camera_activity.LandmarkView
import java.lang.Integer.max
import java.lang.Integer.min

class ImageAnalyser(
    private val view: LandmarkView
): ImageAnalysis.Analyzer {
    private val options = AccuratePoseDetectorOptions
        .Builder()
        .setDetectorMode(AccuratePoseDetectorOptions.STREAM_MODE)
        .build()
    private val detector = PoseDetection.getClient(options)
    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(image: ImageProxy) {
        val frameImage = image.image
        if(frameImage != null) {
            val imageForDetector = InputImage.fromMediaImage(frameImage, image.imageInfo.rotationDegrees)
            val task = detector.process(imageForDetector)
            task
                .addOnSuccessListener {
                    val size = Size(
                        min(image.width, image.height),
                        max(image.width, image.height)
                    )
                    view.setPose(it, size)
                    image.close()
                }
                .addOnFailureListener {
                    image.close()
                }
        }


    }
}