package com.nikita.mlkitcameraapp.helpers

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy

class ImageAnalyser: ImageAnalysis.Analyzer {
    override fun analyze(image: ImageProxy) {
        println("lgmsljdf;nbkS<dmavls np")

        image.close()
    }
}