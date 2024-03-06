package id.rifqipadisiliwangi.tokopaerbe.presentation.common.adapter.wishlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.rifqipadisiliwangi.core.data.local.database.wishlist.WishlistKeys
import id.rifqipadisiliwangi.tokopaerbe.databinding.ItemGridProductWishlistBinding
import id.rifqipadisiliwangi.tokopaerbe.databinding.ItemLinearProductWishlistBinding
import id.rifqipadisiliwangi.tokopaerbe.presentation.common.adapter.AdapterLayoutMode
import id.rifqipadisiliwangi.tokopaerbe.presentation.common.adapter.ViewHolderBinder
import id.rifqipadisiliwangi.tokopaerbe.presentation.feature.wishlist.WishlistViewModel

class WishlistAdapterItem(
    private val viewModel: WishlistViewModel,
    var adapterLayoutMode: AdapterLayoutMode,
    private val onClickListener: (WishlistKeys) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    private val dataDiffer = AsyncListDiffer(
        this,
        object : DiffUtil.ItemCallback<WishlistKeys>() {
            override fun areItemsTheSame(oldItem: WishlistKeys, newItem: WishlistKeys): Boolean {
                return oldItem.productId == newItem.productId
            }

            override fun areContentsTheSame(oldItem: WishlistKeys, newItem: WishlistKeys): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            AdapterLayoutMode.GRID.ordinal -> {
                GridWishlistItemViewHolder(
                    binding = ItemGridProductWishlistBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onClickListener,
                    viewModel
                )
            }
            else -> {
                LinearWishlistItemViewHolder(
                    binding = ItemLinearProductWishlistBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onClickListener,
                    viewModel
                )
            }
        }
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolderBinder<WishlistKeys>).bind(dataDiffer.currentList[position])
    }


    override fun getItemViewType(position: Int): Int {
        return adapterLayoutMode.ordinal
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<WishlistKeys>?) {
        dataDiffer.submitList(data)
        notifyDataSetChanged()
    }

    fun refreshList() {
        notifyItemRangeChanged(0, dataDiffer.currentList.size)
    }

}