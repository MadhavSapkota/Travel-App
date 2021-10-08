package com.app.travel.apputlis.imageutils

import java.io.File


fun File.resizeImageFile() {
    BitmapExt.decreaseImage(this)
}