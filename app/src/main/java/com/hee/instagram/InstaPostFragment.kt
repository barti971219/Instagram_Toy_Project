package com.hee.instagram

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

class InstaPostFragment : Fragment() {
    var imageUri : Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.insta_post_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 변수에 함수를 정의해놓음
        val imagePickerLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                val imageUri = it.data!!.data
                Log.d("instaa", "" + imageUri)
            }

        imagePickerLauncher.launch(
            Intent(Intent.ACTION_PICK).apply {
                this.type = MediaStore.Images.Media.CONTENT_TYPE
            }
        )
    }
}