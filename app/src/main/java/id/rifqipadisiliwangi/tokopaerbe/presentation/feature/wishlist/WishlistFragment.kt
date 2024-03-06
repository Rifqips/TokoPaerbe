package id.rifqipadisiliwangi.tokopaerbe.presentation.feature.wishlist

import android.annotation.SuppressLint
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.GridLayoutManager
import id.rifqipadisiliwangi.tokopaerbe.R
import id.rifqipadisiliwangi.tokopaerbe.databinding.FragmentWishlistBinding
import id.rifqipadisiliwangi.tokopaerbe.presentation.common.adapter.AdapterLayoutMode
import id.rifqipadisiliwangi.tokopaerbe.presentation.common.adapter.wishlist.WishlistAdapterItem
import id.rifqipadisiliwangi.tokopaerbe.utils.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class WishlistFragment :
    BaseFragment<FragmentWishlistBinding, WishlistViewModel>(FragmentWishlistBinding::inflate){

    private val adapterWishlist: WishlistAdapterItem by lazy {
        WishlistAdapterItem(viewModel,AdapterLayoutMode.LINEAR) {}
    }
    override val viewModel: WishlistViewModel by viewModel()

    override fun initView(){
        obeserveData()
        rvListWishlist()
        observeLayout()
        setupSwitchLayout()
        with(binding){
            ivChangeLayout.chipIcon = context?.let { AppCompatResources.getDrawable(it, R.drawable.ic_grid) }
        }

    }

    override fun initListener() {}
    @SuppressLint("SetTextI18n")
    private fun obeserveData(){
        viewModel.getWishlist()
        viewModel.wishlist.observe(viewLifecycleOwner) { wishlist ->
            with(binding){
                binding.rvWishlist.apply {
                    layoutManager = GridLayoutManager(context, 2)
                    adapter = adapterWishlist
                    adapterWishlist.setData(wishlist)
                }
                tvTitleItem.text = "${wishlist.size} ${getString(R.string.dummy_item)}"
            }
        }
    }

    private fun rvListWishlist() {
        val span = if (adapterWishlist.adapterLayoutMode == AdapterLayoutMode.LINEAR) 1 else 2
        binding.rvWishlist.apply {
            layoutManager = GridLayoutManager(requireActivity(), span)
            adapter = adapterWishlist
            adapterWishlist.refreshList()
        }
    }

    private fun observeLayout() {
        viewModel.appLayoutWishlistLiveData.observe(viewLifecycleOwner) { isGridLayout ->
            (binding.rvWishlist.layoutManager as GridLayoutManager).spanCount =
                if (isGridLayout) 2 else 1
            adapterWishlist.adapterLayoutMode =
                if (isGridLayout) AdapterLayoutMode.GRID else AdapterLayoutMode.LINEAR
            adapterWishlist.refreshList()
        }
    }

    private fun setupSwitchLayout() {
        binding.ivChangeLayout.setOnCheckedChangeListener  { _, isChecked ->
            viewModel.setUpLayoutWishlist(isChecked)
            when(isChecked){
                true -> binding.ivChangeLayout.chipIcon = context?.let { AppCompatResources.getDrawable(it, R.drawable.ic_grid) }
                else -> binding.ivChangeLayout.chipIcon = context?.let { AppCompatResources.getDrawable(it, R.drawable.ic_list_layout) }
            }
        }
    }
}