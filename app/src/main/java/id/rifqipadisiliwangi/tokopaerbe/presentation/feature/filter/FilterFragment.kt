package id.rifqipadisiliwangi.tokopaerbe.presentation.feature.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.rifqipadisiliwangi.tokopaerbe.R
import id.rifqipadisiliwangi.tokopaerbe.databinding.FragmentFilterBinding
import id.rifqipadisiliwangi.tokopaerbe.utils.Helper.getSelectedChipValues

class FilterFragment : BottomSheetDialogFragment(){

    private lateinit var binding : FragmentFilterBinding

    private var filterListener: OnFilterListener? = null

    interface OnFilterListener {
        fun onFilterApplied(
            brandItem: String? = null,
            sortItem: String? = null,
        )
    }

    fun setFilterListener(listener: OnFilterListener) {
        filterListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        with(binding){
            tvTitleFilter.text = getString(R.string.string_filter)
            tvTitleShorting.text = getString(R.string.string_urutkan)
            tvResetFilter.text = getString(R.string.string_reset)
            tvTitleCategory.text = getString(R.string.string_category)
            tvTitlePrice.text = getString(R.string.string_harga)
            btnApply.text = getString(R.string.string_tampilkan_produk)
            cpApple.text = getString(R.string.string_apple)
            cpAssus.text = getString(R.string.string_asus)
            cpDell.text = getString(R.string.string_dell)
            cpLenovo.text = getString(R.string.string_lenovo)
            cpReviews.text = getString(R.string.string_ulasan)
            cpSelling.text = getString(R.string.string_penjualan)
            cpLowestPrice.text = getString(R.string.string_harga_terendah)
            cpHighestPrice.text = getString(R.string.string_harga_tertinggi)
            etHighestPriceInput.hint = getString(R.string.string_tertinggi)
            etLowestPriceInput.hint = getString(R.string.string_terendah)

            btnApply.setOnClickListener {
                val brandItem: String = cgCategory.getSelectedChipValues().trim()
                val sortItem: String = cgShorting.getSelectedChipValues().trim()
                filterListener?.onFilterApplied(brandItem, sortItem)
                dismiss()
            }
        }
    }
}