package com.ilmiddin1701.cameraandgallery.models

class ImageData {
    var id: Int? = null
    var name: String? = null
    var image: String? = null

    constructor(id: Int?, name: String?, image: String?) {
        this.id = id
        this.name = name
        this.image = image
    }

    constructor(name: String?, image: String?) {
        this.name = name
        this.image = image
    }
}
