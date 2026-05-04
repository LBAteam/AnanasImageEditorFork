package iamutkarshtiwari.github.io.imageeditorsample.imagepicker.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Parcelable
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.FileProvider
import iamutkarshtiwari.github.io.imageeditorsample.R
import iamutkarshtiwari.github.io.imageeditorsample.imagepicker.ParentActivity
import iamutkarshtiwari.github.io.imageeditorsample.imagepicker.utils.compressImageFile
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.util.*

private const val RES_IMAGE = 100

class ImagePickerActivity : ParentActivity(R.layout.activity_main) {
    private var queryImageUrl: String = ""
    private var imgPath: String = ""
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chooseImage()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RES_IMAGE -> {
                if (resultCode == Activity.RESULT_OK) {
                    returnResult(data)
                } else {
                    finish()
                }
            }
        }
    }

    private fun returnResult(data: Intent?) {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            Toast.makeText(
                this,
                throwable.localizedMessage ?: getString(R.string.ananas_image_editor_select_error),
                Toast.LENGTH_SHORT
            ).show()
        }

        GlobalScope.launch(Dispatchers.Main + exceptionHandler) {

            if (data?.data != null) {     //Photo from gallery
                imageUri = data.data
                queryImageUrl = imageUri?.path!!
                queryImageUrl = compressImageFile(queryImageUrl, false, imageUri!!)
            } else {
                queryImageUrl = imgPath
                compressImageFile(queryImageUrl, uri = imageUri!!)
            }
            imageUri = Uri.fromFile(File(queryImageUrl))

            val result = Intent().apply {
                putExtra(BUNDLE_EXTRA_IMAGE_PATH, imageUri?.path)
            }
            setResult(RESULT_OK, result)
            finish()
        }

    }

    private fun chooseImage() {
        var intent: Intent? = getPickImageIntent()
        if (intent != null) {
            startActivityForResult(intent, RES_IMAGE)
        } else {
            Toast.makeText(
                this,
                getString(R.string.ananas_image_editor_select_error),
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
    }

    private fun getPickImageIntent(): Intent? {
        var chooserIntent: Intent? = null

        var intentList: MutableList<Intent> = ArrayList()

        val pickIntent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }

        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri())
        takePhotoIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

        intentList = addIntentsToList(this, intentList, takePhotoIntent)
        intentList = addIntentsToList(this, intentList, pickIntent)

        if (intentList.size > 0) {
            chooserIntent = Intent.createChooser(
                intentList.removeAt(intentList.size - 1),
                getString(R.string.ananas_image_editor_select_capture_image)
            )
            chooserIntent!!.putExtra(
                Intent.EXTRA_INITIAL_INTENTS,
                intentList.toTypedArray<Parcelable>()
            )
        }

        return chooserIntent
    }

    private fun setImageUri(): Uri {
        val folder = File("${getExternalFilesDir(Environment.DIRECTORY_DCIM)}")
        folder.mkdirs()

        val file = File(folder, TMP_FILE_NAME)
        if (file.exists())
            file.delete()
        file.createNewFile()
        imageUri = FileProvider.getUriForFile(
            this,
            packageName + getString(R.string.file_provider_name),
            file
        )
        imgPath = file.absolutePath
        return imageUri!!
    }

    private fun addIntentsToList(
        context: Context,
        list: MutableList<Intent>,
        intent: Intent
    ): MutableList<Intent> {
        val resInfo = context.packageManager.queryIntentActivities(intent, 0)
        for (resolveInfo in resInfo) {
            val packageName = resolveInfo.activityInfo.packageName
            val targetedIntent = Intent(intent)
            targetedIntent.setPackage(packageName)
            list.add(targetedIntent)
        }
        return list
    }

    companion object {
        const val BUNDLE_EXTRA_IMAGE_PATH = "image_path"
        private const val TMP_FILE_NAME = "ananas_tmp_image.jpg"
    }
}
