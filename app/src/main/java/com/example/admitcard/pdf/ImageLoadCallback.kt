package com.example.admitcard.pdf

import com.itextpdf.layout.element.Image

interface ImageLoadCallback {

    fun onImagesLoaded(studentImage: Image, signatureImage: Image)
}