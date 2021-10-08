package com.app.travel.apputlis.imageutils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Parcelable
import android.os.StrictMode
import android.provider.MediaStore
import android.util.Log
import com.app.travel.R
import com.app.travel.apputlis.UserInfo


import java.io.File
import java.io.FileNotFoundException


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ImagePicker {
    companion object {
        private val DEFAULT_MIN_WIDTH_QUALITY=400        // min pixels
        private val TAG="ImagePicker"
        private val TEMP_IMAGE_NAME="tempImage"

        var minWidthQuality=DEFAULT_MIN_WIDTH_QUALITY

        fun getPickImageIntent(context: Context): Intent? {
            val builder=StrictMode.VmPolicy.Builder()
            StrictMode.setVmPolicy(builder.build())
            var chooserIntent: Intent?=null

            var intentList: MutableList<Intent> =ArrayList()

            val pickIntent=Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            val takePhotoIntent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            takePhotoIntent.putExtra("return-data", true)
            takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getTempFile(context)))
            intentList=addIntentsToList(context, intentList, pickIntent)
            intentList=addIntentsToList(context, intentList, takePhotoIntent)

            if (intentList.size > 0) {
                chooserIntent=Intent.createChooser(
                    intentList.removeAt(intentList.size - 1),
                    context.getString(R.string.pick_image_intent_text)
                )
//                chooserIntent = Intent.createChooser(intentList.removeAt(intentList.size),
//                        context.getString(R.string.pick_image_intent_text))

                chooserIntent!!.putExtra(
                    Intent.EXTRA_INITIAL_INTENTS,
                    intentList.toTypedArray<Parcelable>()
                )
            }

            return chooserIntent
        }


        fun getCameraImageIntent(context: Context): Intent? {
            val builder=StrictMode.VmPolicy.Builder()
            StrictMode.setVmPolicy(builder.build())
            val takePhotoIntent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            takePhotoIntent.putExtra("return-data", true)
            takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getTempFile(context)))
            return takePhotoIntent
        }

        fun getgallerymageIntent(context: Context): Intent? {
            val builder=StrictMode.VmPolicy.Builder()
            StrictMode.setVmPolicy(builder.build())
            val pickIntent=Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )

            return pickIntent
        }


        private fun getTempFile(context: Context): File {
            val imageFile=File(context.externalCacheDir, TEMP_IMAGE_NAME)

            imageFile.parentFile.mkdirs()
            return imageFile
        }

        private fun addIntentsToList(
            context: Context,
            list: MutableList<Intent>,
            intent: Intent
        ): MutableList<Intent> {
            val resInfo=context.packageManager.queryIntentActivities(intent, 0)
            for (resolveInfo in resInfo) {
                val packageName=resolveInfo.activityInfo.packageName
                val targetedIntent=Intent(intent)
                targetedIntent.`package`=packageName
                list.add(targetedIntent)
                Log.d(TAG, "Intent: " + intent.action + " package: " + packageName)
            }
            return list
        }

        fun getImageFromResult(
            context: Context, resultCode: Int,
            imageReturnedIntent: Intent?
        ): Bitmap? {
            Log.d(TAG, "getImageFromResult, resultCode: $resultCode")

            var bmt: Bitmap?=null
            val imageFile=getTempFile(context)

            if (resultCode == Activity.RESULT_OK) {
                val selectedImage: Uri?
                val isCamera=imageReturnedIntent == null ||
                        imageReturnedIntent.data == null ||
                        imageReturnedIntent.data!!.toString().contains(imageFile.toString())
                if (isCamera) {
                    /** CAMERA  */
                    selectedImage=Uri.fromFile(imageFile)
                    UserInfo.UserProfileImage= selectedImage.toString()
                    val myBitmap=BitmapFactory.decodeFile(imageFile.toString())

                    bmt=ExifUtil.rotateBitmap(imageFile.toString(), myBitmap)
                } else {
                    /** ALBUM  */
                    selectedImage=imageReturnedIntent!!.data
                    UserInfo.UserProfileImage= selectedImage.toString()
                    var galleryBtm=getImageResized(context, selectedImage!!)
                    bmt=ExifUtil.rotateBitmap(imageFile.toString(), galleryBtm)

                }
                Log.d(TAG, "selectedImage: " + selectedImage!!)

            }
            return bmt
        }

        fun getImagepath(
            context: Context, resultCode: Int,
            imageReturnedIntent: Intent?
        ): Uri? {
            val imageFile=getTempFile(context)
            var selectedImage: Uri?=null
            if (resultCode == Activity.RESULT_OK) {
                val isCamera=imageReturnedIntent == null ||
                        imageReturnedIntent.data == null ||
                        imageReturnedIntent.data!!.toString().contains(imageFile.toString())
                if (isCamera) {
                    /** CAMERA  */
                    selectedImage=Uri.fromFile(imageFile)

                } else {
                    /** ALBUM  */
                    selectedImage=imageReturnedIntent!!.data

                }


            }

            return selectedImage!!
        }

        private fun getImageResized(context: Context, selectedImage: Uri): Bitmap {
            var bm: Bitmap?=null
            val sampleSizes=intArrayOf(5, 3, 2, 1)
            var i=0
            do {
                bm=decodeBitmap(context, selectedImage, sampleSizes[i])
                Log.d(TAG, "resizer: new bitmap width = " + bm.width)
                i++
            } while (bm!!.width < minWidthQuality && i < sampleSizes.size)
            return bm
        }


        private fun decodeBitmap(context: Context, theUri: Uri, sampleSize: Int): Bitmap {
            val options=BitmapFactory.Options()
            options.inSampleSize=sampleSize

            var fileDescriptor: AssetFileDescriptor?=null
            try {
                fileDescriptor=context.contentResolver.openAssetFileDescriptor(theUri, "r")
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }

            val actuallyUsableBitmap=BitmapFactory.decodeFileDescriptor(
                fileDescriptor!!.fileDescriptor, null, options
            )

            Log.d(
                TAG, options.inSampleSize.toString() + " sample method bitmap ... " +
                        actuallyUsableBitmap.width + " " + actuallyUsableBitmap.height
            )

            return actuallyUsableBitmap
        }


    }
}