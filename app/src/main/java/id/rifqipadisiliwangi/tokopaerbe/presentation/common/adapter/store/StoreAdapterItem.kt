package id.rifqipadisiliwangi.tokopaerbe.presentation.common.adapter.store

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.rifqipadisiliwangi.core.data.network.api.model.store.ResponseItemDataStore
import id.rifqipadisiliwangi.tokopaerbe.R
import id.rifqipadisiliwangi.tokopaerbe.databinding.ItemGridProductStoreBinding
import id.rifqipadisiliwangi.tokopaerbe.databinding.ItemLinearProductStoreBinding
import id.rifqipadisiliwangi.tokopaerbe.utils.Helper.toCurrencyFormat

class StoreAdapterItem(
    private var isGridMode: Boolean = false,
    private val onClickLister: (ResponseItemDataStore) -> Unit
) :
    PagingDataAdapter<ResponseItemDataStore, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ResponseItemDataStore>() {
            override fun areItemsTheSame(
                oldItem: ResponseItemDataStore,
                newItem: ResponseItemDataStore
            ): Boolean {
                return oldItem.productId == newItem.productId
            }

            override fun areContentsTheSame(
                oldItem: ResponseItemDataStore,
                newItem: ResponseItemDataStore
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (isGridMode) {
            val binding = ItemGridProductStoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            GridViewHolder(binding)
        } else {
            val binding = ItemLinearProductStoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LinearViewHolder(binding)
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            when (holder) {
                is GridViewHolder -> holder.bindGrid(it)
                is LinearViewHolder -> holder.bindLinear(it)
            }
        }
    }

    fun toggleLayoutMode(gridMode: Boolean) {
        isGridMode = gridMode
    }

    inner class GridViewHolder(
        private val binding: ItemGridProductStoreBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindGrid(item: ResponseItemDataStore) {
            with(binding){
                ivProduct.load(item.image)
                tvDescripionProduct.text = item.brand
                tvTitleResultPrice.text= item.productPrice?.toCurrencyFormat()
                tvTitleStore.text = item.store
                tvTotalSell.text="${itemView.context.getString(R.string.string_hasil_penjualan)} ${item.sale}"
                icProduct.load(R.drawable.ic_profile_user)
                tvRating.text = item.productRating.toString()
                ivStar.load(R.drawable.ic_star)
            }
            binding.root.setOnClickListener {
                onClickLister(item)
            }
        }
    }

    inner class LinearViewHolder(
        private val binding: ItemLinearProductStoreBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindLinear(item: ResponseItemDataStore) {
            with(binding) {
                ivProduct.load(item.image)
                tvDescripionProduct.text = item.brand
                tvTitleResultPrice.text= item.productPrice?.toCurrencyFormat()
                tvTitleStore.text = item.store
                tvTotalSell.text="${itemView.context.getString(R.string.string_hasil_penjualan)} ${item.sale}"
                icProduct.load(R.drawable.ic_profile_user)
                tvContentRating.text = item.productRating.toString()
                ivStar.load(R.drawable.ic_star)

            }
            binding.root.setOnClickListener {
                onClickLister(item)
            }
        }
    }
}


