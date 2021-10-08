package com.app.travel.apputlis.imageutils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Log
import com.app.travel.apputlis.UserInfo

import timber.log.Timber
import java.io.*


class BitmapExt {

    companion object {
        fun decreaseImageFileSize(file: File): File? {
            try {

                // BitmapFactory options to downsize the image
                val o = BitmapFactory.Options()
                o.inJustDecodeBounds = true
                o.inSampleSize = 6
                // factor of downsizing the image

                var inputStream = FileInputStream(file)
                //Bitmap selectedBitmap = null;
                BitmapFactory.decodeStream(inputStream, null, o)
                inputStream.close()

                // The new size we want to scale to
                val REQUIRED_SIZE = 80

                // Find the correct scale value. It should be the power of 2.
                var scale = 1
                while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                    scale *= 2
                }

                val o2 = BitmapFactory.Options()
                o2.inSampleSize = scale
                inputStream = FileInputStream(file)

                val selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2)
                inputStream.close()

                // here i override the original image file
                file.createNewFile()
                val outputStream = FileOutputStream(file)

                selectedBitmap!!.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)

                return file
            } catch (e: Exception) {
                return null
            }

        }


        fun decreaseImage(file: File) {
            decreaseImageFileSize(file)
        }


        fun storeImage(context: Context, bitmap: Bitmap, fileType: FileTypeMain): File? {
            val pictureFile = getOutputMediaFile(context, fileType)
            try {
                when {
                    pictureFile != null -> {
                        val fos = FileOutputStream(pictureFile)
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos)
                        fos.close()
                    }
                    else -> Timber.v("Error creating media file, check storage permissions: ")
                }// e.getMessage());
            } catch (e: FileNotFoundException) {
                Timber.v("File not found: ${e.message}")
            } catch (e: IOException) {
                Timber.v("Error accessing file: ${e.message}")
            }
            return pictureFile

        }

        /** Create a File for saving an image or video  */
        private fun getOutputMediaFile(context: Context, fileType: FileTypeMain): File? {
            // To be safe, you should check that the SDCard is mounted
            // using Environment.getExternalStorageState() before doing this.
            //val mediaStorageDir = File("${Environment.getDataDirectory().absolutePath}/Android/data/${context.packageName}/Files")
            if (Environment.MEDIA_UNMOUNTED.equals(Environment.getExternalStorageState()) ||
                    Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState())) {
                Log.e("ExternalStorage Error:", "External Storage not mounted or cannot write to external storage")
                return null
            }

            val mediaStorageDir = File("${Environment.getExternalStorageDirectory().absolutePath}/Android/data/${context.packageName}/Images")


            // This location works best if you want the created images to be shared
            // between applications and persist after your app has been uninstalled.

            // Create the storage directory if it does not exist
            when {
                !mediaStorageDir.exists() -> if (!mediaStorageDir.mkdirs()) {
                    return null
                }
            }
// Create a media file name
            //val timeStamp = SimpleDateFormat("ddMMyyyy_HHmm").format(Date())
            // Create a media file name
            //val timeStamp = SimpleDateFormat("ddMMyyyy_HHmm").format(Date())
            val mediaFile: File
            var imageFileName = "Customer"
            when (fileType) {
                FileTypeMain.CUSTOMERIMAGE -> imageFileName += "Customer"


            }

            val time = System.currentTimeMillis()
            val imageName = time.toString() + "_"

            val mImageName = "$imageFileName$imageName.jpg"
            mediaFile = File(mediaStorageDir.path + File.separator + mImageName)

//            when{
//                UserInfo.isUserImage->
//                else->UserInfo.UserCardImage=mediaStorageDir.path + File.separator + mImageName
//            }
            UserInfo.UserProfileImage=mediaStorageDir.path + File.separator + mImageName

            return mediaFile
        }
    }
}