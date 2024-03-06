package id.rifqipadisiliwangi.tokopaerbe.presentation.feature.profile

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.net.Uri
import android.provider.MediaStore
import android.text.method.LinkMovementMethod
import android.view.Gravity
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.net.toUri
import androidx.core.view.isGone
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import id.rifqipadisiliwangi.core.utils.proceedWhen
import id.rifqipadisiliwangi.tokopaerbe.R
import id.rifqipadisiliwangi.tokopaerbe.databinding.FragmentRegisterProfileBinding
import id.rifqipadisiliwangi.tokopaerbe.presentation.customview.CustomView
import id.rifqipadisiliwangi.tokopaerbe.utils.Constant.dataIntentExtras
import id.rifqipadisiliwangi.tokopaerbe.utils.Constant.ig
import id.rifqipadisiliwangi.tokopaerbe.utils.Constant.imageOutput
import id.rifqipadisiliwangi.tokopaerbe.utils.Constant.png
import id.rifqipadisiliwangi.tokopaerbe.utils.Constant.tokoPaerbe
import id.rifqipadisiliwangi.tokopaerbe.utils.Constant.yt
import id.rifqipadisiliwangi.tokopaerbe.utils.Helper.toSpannableHyperLink
import id.rifqipadisiliwangi.tokopaerbe.utils.IOnBackPressed
import id.rifqipadisiliwangi.tokopaerbe.utils.ImageSaver
import id.rifqipadisiliwangi.tokopaerbe.utils.base.BaseFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class RegisterProfileFragment:
    BaseFragment<FragmentRegisterProfileBinding, RegisterProfileViewModel>(FragmentRegisterProfileBinding::inflate),
    IOnBackPressed {

    override val viewModel: RegisterProfileViewModel by viewModel()

    private var imageFile : File? = null
    private var imageUri: Uri? = Uri.EMPTY
    private var imagePost: Bitmap? = null

    override fun initView() {
        setUpSpannable()
        with(binding){
            menuToolbar.title = getString(R.string.string_profile)
            ivUser.load(R.drawable.ic_person)
            ivUser.scaleType = ImageView.ScaleType.CENTER_INSIDE
            etNameInput.hint= getString(R.string.string_name)
            btnLogin.text = getString(R.string.string_masuk)
        }
    }

    override fun initListener() {
        with(binding){
            ivUser.setOnClickListener {
                showDialog()
            }
            btnLogin.setOnClickListener {
                doRegisterProfile()
                observeData()
            }
        }
    }

    private fun observeData() {
        viewModel.registerProfileResult.observe(viewLifecycleOwner){
            lifecycleScope.launch {
                delay(2500)
                binding.pbRegisterProfile.isGone = false
            }
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbRegisterProfile.isGone = true
                    dialogSuccess()
                },
                doOnLoading = {
                    binding.pbRegisterProfile.isGone = true
                },
                doOnError = {
                    binding.pbRegisterProfile.isGone = false
                    dialogFailed()
                }
            )
        }
    }
    private fun doRegisterProfile() {
        if (imageFile != null) {
            val imageRequestBody = imageFile?.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part? = imageRequestBody?.let {
                MultipartBody.Part.createFormData("userImage", imageFile?.name, it )
            }
            viewModel.userRegisterProfile(
                binding.etNameEdit.text.toString().trim().toRequestBody("multipart/form-data".toMediaType()),
                imageMultipart
            )
        }
        val username = binding.etNameEdit.text.toString()
        viewModel.saveUsername(username)
    }

    private fun setUpSpannable(){
        with(binding){
            tvRules.movementMethod = LinkMovementMethod.getInstance()
            val color = context?.let { ContextCompat.getColor(it, R.color.primary_purple) }

            val actionInc : () -> Unit = {
                Intent(Intent.ACTION_VIEW, yt.toUri()).run {
                    context?.startActivity(this)
                }
            }
            val actionPolicy : () -> Unit = {
                Intent(Intent.ACTION_VIEW,ig.toUri()).run {
                    context?.startActivity(this)
                }
            }
            if (color != null){
                tvRules.text = getString(R.string.string_helper_rules)
                    .toSpannableHyperLink(
                        resources.configuration.locales[0].language,
                        color,
                        actionInc,
                        actionPolicy
                    )
            }
        }
    }
    private fun dialogSuccess() {
        val dialog = context?.let { context ->
            CustomView.dialogCustom(context,Gravity.CENTER,R.raw.success) {
                findNavController().navigate(R.id.action_registerProfileFragment_to_homeFragment)
            }
        }
        dialog?.show()
        lifecycleScope.launch {
            delay(2000)
            dialog?.dismiss()
        }
    }

    private fun dialogFailed() {
        val dialog = context?.let { context ->
            CustomView.dialogCustom(context,Gravity.CENTER,R.raw.failed
            ) {}
        }
        dialog?.show()
        lifecycleScope.launch {
            delay(3000)
            dialog?.dismiss()
        }
    }

    private fun openCamera(){
        if (checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, 1)
        } else {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
        }
    }
    private fun openGallery(){
        getContent.launch(imageOutput)
    }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                val contentResolver: ContentResolver = requireContext().contentResolver
                val type = contentResolver.getType(it)
                imageUri = it
                val fileNameimg = "${System.currentTimeMillis()}$png"
                binding.ivUser.setImageURI(it)
                binding.ivUser.scaleType = ImageView.ScaleType.CENTER_CROP

                val tempFile = File.createTempFile(tokoPaerbe, fileNameimg, null)
                imageFile = tempFile
                val inputstream = contentResolver.openInputStream(uri)
                tempFile.outputStream().use    { result ->
                    inputstream?.copyTo(result)
                }
            }
        }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            imagePost = data!!.extras!![dataIntentExtras] as Bitmap?
            val dimension = imagePost?.height?.let { imagePost!!.width.coerceAtMost(it) }
            imagePost = dimension?.let { ThumbnailUtils.extractThumbnail(imagePost, it, dimension) }
            val imageSaver = ImageSaver(requireContext())
            imagePost?.let { imageSaver.saveBitmapToGallery(it) }
            with(binding){
                ivUser.setImageBitmap(imagePost)
                ivUser.scaleType = ImageView.ScaleType.CENTER_CROP
            }

            val yourBitmap: Bitmap? = imagePost
            val fileName = "your_image.png"
            imageFile = yourBitmap?.let { bitmapToFile(requireContext(), it, fileName) }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * commentar still in use
     */
    private fun showDialog() {
        MaterialAlertDialogBuilder(requireContext()).apply {
            val dialogItem = arrayOf(
                resources.getString(R.string.string_camera),
                resources.getString(R.string.string_galery)
            )
            setTitle(R.string.string_pilih_gambar)
            setItems(dialogItem) { _, items ->
                when (items) {
                    0 -> openCamera()
                    1 -> openGallery()
                }
            }.show()
        }
    }

    fun bitmapToFile(context: Context, bitmap: Bitmap, fileName: String): File {
        val cacheDir = context.cacheDir
        val file = File(cacheDir, fileName)

        try {
            val fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.flush()
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
    }

    override fun onBackPressed(): Boolean {
        return if (view != null) {
            childFragmentManager.popBackStack()
            true
        } else {
            false
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.debugScreenView(getString(R.string.string_masuk))
    }
}