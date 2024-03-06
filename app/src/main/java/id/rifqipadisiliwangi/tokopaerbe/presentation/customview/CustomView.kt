package id.rifqipadisiliwangi.tokopaerbe.presentation.customview

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import id.rifqipadisiliwangi.tokopaerbe.R
import id.rifqipadisiliwangi.tokopaerbe.databinding.ItemDialogBinding
import id.rifqipadisiliwangi.tokopaerbe.databinding.ItemSnackbarContainerBinding
import id.rifqipadisiliwangi.tokopaerbe.databinding.ItemToastContainerBinding

object CustomView{
    @SuppressLint("RestrictedApi")
    fun showCustomSnackbar(v: View, context: Context, action : () -> Unit? ) {
        val inflater = LayoutInflater.from(context)
        val binding = ItemSnackbarContainerBinding.inflate(inflater)
        val snackbar = Snackbar.make(v, "", Snackbar.LENGTH_LONG)

        val snackbarLayout = snackbar.view as SnackbarLayout
        snackbarLayout.apply {
            val lp = layoutParams as FrameLayout.LayoutParams
            lp.gravity = Gravity.TOP
            layoutParams = lp
        }

        snackbarLayout.addView(binding.root, 0)

        snackbar.apply {
            view.setBackgroundColor(Color.TRANSPARENT)
            animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE

            val slideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down)
            val slideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up)
            view.startAnimation(slideDown)

            slideDown.setAnimationListener(object : Animation.AnimationListener{
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    view.startAnimation(slideUp)
                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })
            slideUp.setAnimationListener(object : Animation.AnimationListener{
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    view.isVisible = false
                    action.invoke()
                    dismiss()
                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })
            snackbar.show()
        }

    }

    fun Toast.showCustomToast(message: String, context: Context){

        val binding = ItemToastContainerBinding.inflate(
            LayoutInflater.from(context)
        )

        binding.tvToastText.text = message

        this.apply {
            setGravity(Gravity.BOTTOM, 0, 40)
            duration = Toast.LENGTH_LONG
            view = binding.itemToastContainer
            show()
        }
    }

    fun dialogCustom(
        context: Context,
        aligment: Int = Gravity.CENTER,
        rawResId : Int ,
        action: () -> Unit?
    ):Dialog{
        val dialog = Dialog(context)
        dialog.window?.apply {
            val params: WindowManager.LayoutParams = this.attributes
            params.width = 300
            params.height = 300
            attributes.windowAnimations = android.R.transition.fade
            setGravity(aligment)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        val inflater = LayoutInflater.from(context)
        val binding = ItemDialogBinding.inflate(inflater)
        dialog.setContentView(binding.root)
        binding.icLottie.setAnimation(rawResId)
        action.invoke()
        return dialog
    }

}

