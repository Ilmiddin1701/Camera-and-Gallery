package com.ilmiddin1701.cameraandgallery.db

import com.ilmiddin1701.cameraandgallery.models.ImageData

interface MyDbInterface {
    fun addImage(imageData: ImageData)
    fun showImages(): List<ImageData>
}