package id.rifqipadisiliwangi.tokopaerbe.presentation.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.isVisible
import id.rifqipadisiliwangi.tokopaerbe.databinding.ErrorStasteStoreBinding

class ErrorStateStore @JvmOverloads constructor(
    context : Context,
    attrs : AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var binding : ErrorStasteStoreBinding

    init {
        binding = ErrorStasteStoreBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
    }

    fun setErrorMessage(
        title : String,
        description : String,
        btnTitle : String = "",
        action: ()->(Unit)
    ) = with(binding){
            tvTitleState.text = title
            tvDescriptionState.text = description
            btnActionState.isVisible = btnTitle.isNotEmpty()
            btnActionState.text = btnTitle
            btnActionState.setOnClickListener {
                action.invoke()
            }
    }
}