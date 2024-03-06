package id.rifqipadisiliwangi.tokopaerbe.utils

import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.gson.Gson
import java.util.regex.Pattern

object Helper {

    inline fun <reified T> T.toJson(): String {
        return Gson().toJson(this)
    }
    fun Int.toCurrencyFormat(): String {
        val format: android.icu.text.NumberFormat = android.icu.text.NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 0
        format.currency = android.icu.util.Currency.getInstance("IDR")
        return format.format(this)
    }

    fun List<Int>.toSumInt(): Int {
        if (isEmpty()) {
            return 0
        }
        return this.reduce { acc, i -> acc + i }
    }
    fun ChipGroup.getSelectedChipValues(): String {
        val selectedChips = mutableListOf<String>()
        for (i in 0 until childCount) {
            val chip: Chip = getChildAt(i) as Chip
            if (chip.isChecked) {
                selectedChips.add(chip.text.toString())
            }
        }
        return selectedChips.joinToString(", ")
    }
    fun String.removeSquareBrackets(): String {
        return this.replace("[", "").replace("]", "")
    }

    val emailPattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
    val passwordPattern = Pattern.compile(
        "^(?=.*[A-Z])" +
                "(?=.*[a-z])" +
                "(?=.*\\d)" + //
                "(?=.*[@#\$%^&+=!])" +
                "[A-Za-z\\d@#\$%^&+=!]{8,}"
    )

    fun String.toSpannableHyperLink(
        locale: String,
        color :Int,
        actionInc: () -> Unit,
        actionPolicy : () -> Unit
    ):SpannableString{
        val spannableString = SpannableStringBuilder(this)

        val tncText = if (locale =="in") "Syarat & Ketentuan" else "Terms & Conditions"
        val policyText = if (locale == "in") "Kebijakan Privasi" else "Privacy Policy"

        val startTnc = this.indexOf(tncText)
        val endTnc = startTnc + tncText.length

        val startPolicy = this.indexOf(policyText)
        val endPolicy = startPolicy + policyText.length

        val tncClickableSpan = object : ClickableSpan(){
            override fun onClick(widget: View) {
                actionInc.invoke()
            }
        }

        val policyClickableSpan = object :  ClickableSpan(){
            override fun onClick(widget: View) {
                actionPolicy.invoke()
            }
        }

        spannableString.setSpan(
            tncClickableSpan,
            startTnc,
            endTnc,
            0
        )
        spannableString.setSpan(
            policyClickableSpan,
            startPolicy,
            endPolicy,
            0
        )

        spannableString.setSpan(
            ForegroundColorSpan(color),
            startTnc,
            endTnc,
            0
        )
        spannableString.setSpan(
            ForegroundColorSpan(color),
            startPolicy,
            endPolicy,
            0
        )

        return SpannableString(spannableString)
    }

}