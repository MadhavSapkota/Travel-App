package com.app.travel.activity.editprofile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.app.travel.R
import com.app.travel.activity.editprofile.di.DaggerEditProfileComponent
import com.app.travel.activity.editprofile.di.EditProfileModule
import com.app.travel.activity.editprofile.mvp.EditProfilePresenter
import com.app.travel.activity.editprofile.mvp.EditProfileView
import com.app.travel.activity.userprofile.UserProfileActivity
import com.app.travel.app.AppApplication
import com.app.travel.apputlis.imageutils.ImagePicker
import com.app.travel.databinding.FragmentEditprofileBinding
import javax.inject.Inject


class EditProfileActivity : AppCompatActivity() {

    @Inject
    lateinit var editProfileView: EditProfileView

    @Inject
    lateinit var editProfilePresenter: EditProfilePresenter

    private lateinit var binding: FragmentEditprofileBinding
    val ImageREQUESTID = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mvpKotlinApplication = AppApplication()
        DaggerEditProfileComponent.builder()
            .appComponent(mvpKotlinApplication.get(this).appComponent)
            .editProfileModule(EditProfileModule(this))
            .build()
            .inject(this)

        binding = FragmentEditprofileBinding.inflate(layoutInflater)
        val view = binding.root
        editProfileView.start(binding)
        setContentView(view)
        editProfilePresenter.onCreateView()
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, EditProfileActivity::class.java))
        }
    }


    /* Following codes are written for the camera and gallery purposes.*/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ImageREQUESTID) {
            if (resultCode == Activity.RESULT_OK) {
                val bitmap = ImagePicker.getImageFromResult(this, resultCode, data)
                editProfilePresenter.getuserPicture(bitmap!!)
            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, UserProfileActivity.javaClass)
        startActivity(intent)
        super.onBackPressed()

    }
}