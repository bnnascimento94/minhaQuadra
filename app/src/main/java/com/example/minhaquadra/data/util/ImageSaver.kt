package com.example.minhaquadra.data.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.os.Environment
import android.util.Log
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Ilya Gazman on 3/6/2016.
 * Esta classe tem por objetivo tratar as imagens que são geradas pela camera
 * Salva no dispositivo
 * Exclui da galeria do dispositivo
 * E diminui o seu tamanho ao enviar para o servidor
 */
class ImageSaver {
    private var directoryName = "images"
    private var fileName = "image.jpeg"
    private var context: Context
    private var external = false
    private var mImageFile: File? = null

    constructor(context: Context) {
        this.context = context
    }

    constructor(context: Context, directoryName: String, fileName: String) {
        this.context = context
        this.directoryName = directoryName
        this.fileName = fileName
    }

    fun setFileName(fileName: String): ImageSaver {
        this.fileName = fileName
        return this
    }

    fun setExternal(external: Boolean): ImageSaver {
        this.external = external
        return this
    }

    fun setDirectoryName(directoryName: String): ImageSaver {
        this.directoryName = directoryName
        return this
    }

    fun save(bitmapImage: Bitmap): String? {
        var fileOutputStream: FileOutputStream? = null
        var fileName: String? = null
        try {
            val file = createFile()
            fileName = file.absolutePath
            fileOutputStream = FileOutputStream(file)
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fileOutputStream?.close()
            } catch (e: IOException) {
               e.printStackTrace()
            }
        }
        return fileName
    }

    fun saveJPEG(bitmapImage: Bitmap) {
        var fileOutputStream: FileOutputStream? = null
        try {
            fileOutputStream = FileOutputStream(createFile())
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fileOutputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun createFile(): File {
        val directory =
            File(context.getExternalFilesDir(null)!!.absolutePath + "/" + directoryName)
        if (!directory.exists() && !directory.mkdirs()) {
            Log.e("ImageSaver", "Error creating directory $directory")
        }
        return File(directory, fileName)
    }

    fun load(): Bitmap? {
        var inputStream: FileInputStream? = null
        try {
            inputStream = FileInputStream(createFile())
            return BitmapFactory.decodeStream(inputStream)
            //return CarregadorDeFoto.carrega(createFile().getAbsolutePath());
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                inputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return null
    }

    fun deleteFile(): Boolean {
        val deleted: Boolean
        val dir = File(Environment.getExternalStorageDirectory().toString() + "/" + directoryName)
        val f0 = File(dir, fileName)
        deleted = f0.delete()
        return deleted
    }

    fun deleteDir(): Boolean {
        val dir = File(Environment.getExternalStorageDirectory().toString() + "/" + directoryName)
        deleteRecursive(dir)
        return true
    }

    fun deleteRecursive(fileOrDirectory: File) {
        if (fileOrDirectory.isDirectory) {
            for (child in fileOrDirectory.listFiles()) {
                deleteRecursive(child)
            }
        }
        fileOrDirectory.delete()
    }

    fun createImageFile(): ImageSaver {
        try {
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val imageFileName = "IMAGE_" + timeStamp + "_"
            val storageDirectory = context.getExternalFilesDir(null)
            mImageFile = File.createTempFile(imageFileName, ".jpg", storageDirectory)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return this
    }

    fun rotateImage(): Bitmap {
        var exifInterface: ExifInterface? = null
        var bitmap: Bitmap? = null
        try {
            bitmap = BitmapFactory.decodeFile(mImageFile!!.absolutePath)
            exifInterface = ExifInterface(mImageFile!!.absolutePath)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val orientation = exifInterface!!.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_UNDEFINED
        )
        val matrix = Matrix()
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.setRotate(90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.setRotate(180f)
            else -> {
            }
        }
        return Bitmap.createBitmap(bitmap!!, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    companion object {
        private const val SOLICITAR_PERMISSAO = 1
        var MAX_IMAGE_DIMENSION = 720
        fun decodeFile(filePath: String?): Bitmap? {
            val o = BitmapFactory.Options()
            o.inJustDecodeBounds = true
            BitmapFactory.decodeFile(filePath, o)

// The new size we want to scale to
            val REQUIRED_SIZE = 1024

// Find the correct scale value. It should be the power of 2.
            var width_tmp = o.outWidth
            var height_tmp = o.outHeight
            var scale = 1
            while (true) {
                if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE) break
                width_tmp /= 2
                height_tmp /= 2
                scale *= 2
            }

// Decode with inSampleSize
            val o2 = BitmapFactory.Options()
            o2.inSampleSize = scale
            var image = BitmapFactory.decodeFile(filePath, o2)
            val exif: ExifInterface
            try {
                exif = ExifInterface(filePath!!)
                val exifOrientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )
                var rotate = 0
                when (exifOrientation) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> rotate = 90
                    ExifInterface.ORIENTATION_ROTATE_180 -> rotate = 180
                    ExifInterface.ORIENTATION_ROTATE_270 -> rotate = 270
                }
                if (rotate != 0) {
                    val w = image.width
                    val h = image.height

                    // Setting pre rotate
                    val mtx = Matrix()
                    mtx.preRotate(rotate.toFloat())

                    // Rotating Bitmap & convert to ARGB_8888, required by tess
                    image = Bitmap.createBitmap(image, 0, 0, w, h, mtx, false)
                }
            } catch (e: IOException) {
                return null
            }
            return image.copy(Bitmap.Config.ARGB_8888, true)
        }

        fun getDominantColor(bitmap: Bitmap?): Int {
            val newBitmap = Bitmap.createScaledBitmap(bitmap!!, 1, 1, true)
            val color = newBitmap.getPixel(0, 0)
            newBitmap.recycle()
            return color
        }

        fun getFileQualityReduced(img: File, context: Context): File? {
            var imgToSdcard: File? = null
            if (img.exists()) {
                //tamanho original
                try {
                    val bit = decodeFile(img.absolutePath)
                    val bos = ByteArrayOutputStream()
                    bit!!.compress(Bitmap.CompressFormat.JPEG, 30 /*ignored for PNG*/, bos)
                    val bitmapdata = bos.toByteArray()
                    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                    val nomeImg = "IMAGE_" + timeStamp + "_"
                    //File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    val storageDirectory = context.getExternalFilesDir(null)
                    imgToSdcard = File.createTempFile(nomeImg, ".jpg", storageDirectory)
                    val outputStream = FileOutputStream(imgToSdcard, false)
                    outputStream.write(bitmapdata, 0, bitmapdata.size)
                    outputStream.flush()
                    outputStream.close()
                } catch (e: Exception) {
                    Log.e("ERRO DE CONVERSAO", e.message!!)
                }
            }
            return imgToSdcard
        }

        val isExternalStorageWritable: Boolean
            get() {
                val state = Environment.getExternalStorageState()
                return Environment.MEDIA_MOUNTED == state
            }
        val isExternalStorageReadable: Boolean
            get() {
                val state = Environment.getExternalStorageState()
                return Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state
            }

        fun createImageFile(context: Context): File? {
            var image: File? = null
            try {
                val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val imageFileName = "IMAGE_" + timeStamp + "_"
                val storageDirectory = context.getExternalFilesDir(null)
                image = File.createTempFile(imageFileName, ".jpg", storageDirectory)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return image
        }

        fun rotateImage(mImageFileLocation: String?): Bitmap {
            var exifInterface: ExifInterface? = null
            var bitmap: Bitmap? = null
            try {
                bitmap = BitmapFactory.decodeFile(mImageFileLocation)
                exifInterface = ExifInterface(mImageFileLocation!!)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val orientation = exifInterface!!.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED
            )
            val matrix = Matrix()
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> matrix.setRotate(90f)
                ExifInterface.ORIENTATION_ROTATE_180 -> matrix.setRotate(180f)
                else -> {
                }
            }
            return Bitmap.createBitmap(
                bitmap!!,
                0,
                0,
                bitmap.width,
                bitmap.height,
                matrix,
                true
            )
        }
        fun createImageDCIM(bitmap: Bitmap): String{
            val diretorio = File("/storage/emulated/0/DCIM/")
            diretorio.mkdirs()

            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val imageFileName = "IMAGE_" + timeStamp + "_"
            
            val file = File(diretorio.path, "JUI Monitoria/Monitoria/${imageFileName}.jpeg");

            if(!file.exists()) {
                file.parentFile?.mkdirs()
            }

            bitmap.compress(Bitmap.CompressFormat.JPEG, 85,FileOutputStream(file))

            return file.absolutePath
        }
    }
}