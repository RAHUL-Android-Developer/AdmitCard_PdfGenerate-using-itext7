package com.example.admitcard

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition

import com.example.admitcard.Pdf.PdfGenerate
import com.example.admitcard.pdf.ApiManager
import com.example.admitcard.pdf.ImageLoadCallback
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.layout.element.Image
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var apiManager: ApiManager

    // Example usage

    private lateinit var createPdfButton: Button
    private lateinit var imagevirew: ImageView
    private lateinit var imagevirewsignature: ImageView
    private lateinit var pdfGenrate: PdfGenerate

    private  val STUDENT_IMAGE="http://www.prsuuniv.in/ftpwebapps/prsu/resources/studentdata/users/profileimage/"
    private  val STUDENT_SIGNATURE="http://www.prsuuniv.in/ftpwebapps/prsu/resources/studentdata/users/profilesignature/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        apiManager = ApiManager()
        createPdfButton = findViewById(R.id.createpdf)
        imagevirew = findViewById(R.id.studentimage)
        imagevirewsignature = findViewById(R.id.studentsig)
        pdfGenrate = PdfGenerate(this)







        createPdfButton.setOnClickListener {
            val token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJNQURIVTEyM0AiLCJpYXQiOjE3MTY3OTYyNzgsImp3dFR5cGUiOiJhZG1pdGNhcmQiLCJleHAiOjE3MTY4ODI2Nzh9.7YJjGfrLv6Ly_Pd0uaW_ZyExTvNfAsmLSyUPyWucEsA"
            fetchDataFromApi(token)

        }
    }


    fun fetchDataFromApi(token: String) {
        apiManager.fetchData(token,
            onDataReceived = { responseData ->
                Toast.makeText(this, "Clicked Please Wait!!", Toast.LENGTH_SHORT).show()
                imageStudentAndSignature(responseData, object : ImageLoadCallback {
                    override fun onImagesLoaded(studentImage: Image, signatureImage: Image) {
                        pdfGenrate.createPdf(responseData, studentImage, signatureImage)
                    }
                })
            },
            onError = { errorMessage ->
                Log.e("MainActivity", errorMessage)
                Toast.makeText(this, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
            }
        )
    }


    fun imageStudentAndSignature(responseDataList: DataItem, callback: ImageLoadCallback) {

        var studentImage: Image? = null
        var signatureImage: Image? = null

        Glide.with(this)
            .asBitmap()
            .load(STUDENT_IMAGE + responseDataList.profileImage)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.placeholder_image)
            .into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    val studentImageStream = ByteArrayOutputStream()
                    resource.compress(Bitmap.CompressFormat.PNG, 100, studentImageStream)
                    val studentBitmapData = studentImageStream.toByteArray()
                    val studentImageData = ImageDataFactory.create(studentBitmapData)
                    studentImage = Image(studentImageData).apply {
                        setHeight(82f)
                        setWidth(90f)
                        setPadding(-10f)
                    }
                    if (studentImage != null && signatureImage != null) {
                        callback.onImagesLoaded(studentImage!!, signatureImage!!)
                    }
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    val errorBitmap = BitmapFactory.decodeResource(resources, R.drawable.placeholder_image)
                    val errorImageStream = ByteArrayOutputStream()
                    errorBitmap.compress(Bitmap.CompressFormat.PNG, 100, errorImageStream)
                    val errorBitmapData = errorImageStream.toByteArray()
                    val errorImageData = ImageDataFactory.create(errorBitmapData)
                    studentImage = Image(errorImageData).apply {
                        setHeight(82f)
                        setWidth(90f)
                        setPadding(-10f)
                    }
                    if (studentImage != null && signatureImage != null) {
                        callback.onImagesLoaded(studentImage!!, signatureImage!!)
                    }
                }
            })

        Glide.with(this)
            .asBitmap()
            .load(STUDENT_SIGNATURE + responseDataList.signatureImage)
            .placeholder(R.drawable.placeholder_signature)
            .error(R.drawable.placeholder_signature)
            .into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    val signatureImageStream = ByteArrayOutputStream()
                    resource.compress(Bitmap.CompressFormat.PNG, 100, signatureImageStream)
                    val signatureBitmapData = signatureImageStream.toByteArray()
                    val signatureImageData = ImageDataFactory.create(signatureBitmapData)
                    signatureImage = Image(signatureImageData).apply {
                        setHeight(17f)
                        setWidth(90f)
                        setPadding(-5f)
                    }
                    if (studentImage != null && signatureImage != null) {
                        callback.onImagesLoaded(studentImage!!, signatureImage!!)
                    }
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    val errorBitmap = BitmapFactory.decodeResource(resources, R.drawable.placeholder_signature)
                    val errorImageStream = ByteArrayOutputStream()
                    errorBitmap.compress(Bitmap.CompressFormat.PNG, 100, errorImageStream)
                    val errorBitmapData = errorImageStream.toByteArray()
                    val errorImageData = ImageDataFactory.create(errorBitmapData)
                    signatureImage = Image(errorImageData).apply {
                        setHeight(17f)
                        setWidth(90f)
                        setPadding(-5f)
                    }
                    if (studentImage != null && signatureImage != null) {
                        callback.onImagesLoaded(studentImage!!, signatureImage!!)
                    }
                }
            })
    }


}
