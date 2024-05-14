package com.ilmiddin1701.cameraandgallery

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.ilmiddin1701.cameraandgallery.adapters.RvAdapter
import com.ilmiddin1701.cameraandgallery.databinding.ActivityMainBinding
import com.ilmiddin1701.cameraandgallery.db.MyDbHelper
import com.ilmiddin1701.cameraandgallery.models.ImageData
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var rvAdapter: RvAdapter
    private lateinit var photoUri: Uri
    private var absolutPath = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.apply {
            image1.setOnClickListener {
                if (edtName.text.isNotEmpty()) {
                    image2.visibility = View.GONE
                    val imageFile = createImageFile()
                    photoUri = FileProvider.getUriForFile(
                        this@MainActivity,
                        "com.ilmiddin1701.cameraandgallery",
                        imageFile
                    )
                    getTakeImageContent.launch(photoUri)
                }
            }
            image2.setOnClickListener {
                if (edtName.text.isNotEmpty()) {
                    image1.visibility = View.GONE
                    getImageCount.launch("image/*")
                }
            }
            myDbHelper = MyDbHelper(this@MainActivity)
            rvAdapter = RvAdapter(myDbHelper.showImages() as ArrayList)
            rv.adapter = rvAdapter

            btnSave.setOnClickListener {
                if (edtName.text.isNotEmpty() && absolutPath != "") {
                    myDbHelper.addImage(ImageData(edtName.text.toString(), absolutPath))
                    rvAdapter = RvAdapter(myDbHelper.showImages() as ArrayList)
                    rv.adapter = rvAdapter
                    image1.setImageResource(R.drawable.img1)
                    image2.setImageResource(R.drawable.img)
                    edtName.text = null
                    absolutPath = ""
                    image1.visibility = View.VISIBLE
                    image2.visibility = View.VISIBLE
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Avval bo'sh maydonlarni to'ldiring",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    private fun createImageFile(): File {
        val format = SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault()).format(Date())
        val externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_$format", ".jpg", externalFilesDir).apply {
            absolutPath = absolutePath
        }
    }

    private val getTakeImageContent =
        registerForActivityResult(ActivityResultContracts.TakePicture()) {
            val format = SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault()).format(Date())
            if (it) {
                binding.image1.setImageURI(photoUri)
                val inputStream = contentResolver?.openInputStream(photoUri)
                val file = File(filesDir, "IMG_$format.jpg")
                val fileOutputStream = FileOutputStream(file)
                inputStream?.copyTo(fileOutputStream)
                inputStream?.close()
                fileOutputStream.close()
            }
        }

    private val getImageCount = registerForActivityResult(ActivityResultContracts.GetContent()) {
        val format1 = SimpleDateFormat(
            "hh:mm:ss:SSS",
            Locale.getDefault()
        ).format(Date(System.currentTimeMillis()))
        binding.apply {
            it ?: return@registerForActivityResult
            image2.setImageURI(it)
            val inputStream = contentResolver?.openInputStream(it)
            val file = File(filesDir, "IMG_${edtName.text}_$format1.jpg")
            val fileOutputStream = FileOutputStream(file)
            inputStream?.copyTo(fileOutputStream)
            inputStream?.close()
            fileOutputStream.close()
            absolutPath = file.absolutePath
        }
    }

}













