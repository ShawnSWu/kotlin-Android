package com.shawn.day1

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import com.shawn.day1.databinding.ActivityMainBinding
import android.support.v4.content.FileProvider
import android.widget.ImageView
import java.io.File
import java.util.*


class MainActivity : AppCompatActivity() ,View.OnClickListener{

    var binding: ActivityMainBinding?=null
    var uriFromCamera:Uri? = null

    val CALL_CAMERA:Int = 9527
    val CALL_ABLUM:Int = 9528

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding?.buttonAlbum?.setOnClickListener(this)
        binding?.buttonCamera?.setOnClickListener(this)

        //裁切圖片
        binding?.imageView?.scaleType = ImageView.ScaleType.CENTER_CROP

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode) {

            CALL_CAMERA->{
                if(resultCode == Activity.RESULT_OK) {
                    val  bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uriFromCamera))
                    binding?.imageView?.setImageBitmap(bitmap)
                }
            }

            CALL_ABLUM->{
                if(resultCode == Activity.RESULT_OK) {
                    //從相簿選取,直接取data
                    val uriFromAlbum:Uri  = data!!.data
                    binding?.imageView?.setImageURI(uriFromAlbum)
                }
            }
        }
    }

    fun intentCamera() {
        //Android 7.0調用CAMERA ，http://www.jianshu.com/p/8004813c0a89
        val imagePath = File(getExternalCacheDir(),"Pictures")
        if (!imagePath.exists()){imagePath.mkdirs();}
        val time:String = Date(System.currentTimeMillis()).time.toString()
        val newFile = File(imagePath,"IMAGE-$time.jpg")
        //使用FileProvider
        uriFromCamera = FileProvider.getUriForFile(this,"com.shawn.camera",newFile)


        val intent:Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uriFromCamera)
        startActivityForResult(intent,CALL_CAMERA)
    }

    fun intentAlbum() {
        val i:Intent = Intent(Intent.ACTION_PICK)
        i.setType("image/*")
        startActivityForResult(i, CALL_ABLUM)
    }


    override fun onClick(p0: View?) {

        when(p0?.id) {
            R.id.button_camera -> intentCamera()

            R.id.button_album-> intentAlbum()
        }
    }
}

