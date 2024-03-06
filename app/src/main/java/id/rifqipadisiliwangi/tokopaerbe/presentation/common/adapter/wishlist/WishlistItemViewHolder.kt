package id.rifqipadisiliwangi.tokopaerbe.presentation.common.adapter.wishlist

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.rifqipadisiliwangi.core.data.local.database.wishlist.WishlistKeys
import id.rifqipadisiliwangi.tokopaerbe.R
import id.rifqipadisiliwangi.tokopaerbe.databinding.ItemGridProductWishlistBinding
import id.rifqipadisiliwangi.tokopaerbe.databinding.ItemLinearProductWishlistBinding
import id.rifqipadisiliwangi.tokopaerbe.presentation.common.adapter.ViewHolderBinder
import id.rifqipadisiliwangi.tokopaerbe.presentation.common.adapter.wishlist.GridWishlistItemViewHolder.Companion.btnOrder
import id.rifqipadisiliwangi.tokopaerbe.presentation.feature.wishlist.WishlistViewModel
import id.rifqipadisiliwangi.tokopaerbe.utils.Helper.toCurrencyFormat

class LinearWishlistItemViewHolder(
    private val binding: ItemLinearProductWishlistBinding,
    private val onClickListener: (WishlistKeys) -> Unit,
    private val viewModel: WishlistViewModel
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<WishlistKeys> {

    @SuppressLint("SetTextI18n")
    override fun bind(item: WishlistKeys) {
        with(binding) {
            tvDescripionProduct.text = item.productName
            ivProduct.load(item.image)
            tvTitleResultPrice.text= item.productPrice.toCurrencyFormat()
            tvTitleStore.text = item.store
            btnOrderNow.text = btnOrder
            tvTotalSell.text = "${itemView.context.getString(R.string.string_hasil_penjualan)} ${item.sale}"
            tvRating.text= item.productRating.toString()
            ivStart.load(R.drawable.ic_star)
            icProduct.load(R.drawable.ic_profile_user)
            ivTrash.load(R.drawable.ic_trash)
        }
        binding.ivTrash.setOnClickListener {
            viewModel.deleteWishlist(item.productId)
        }
        binding.root.setOnClickListener {
            onClickListener(item)
        }
    }

}

class GridWishlistItemViewHolder(
    private val binding: ItemGridProductWishlistBinding,
    private val onClickListener: (WishlistKeys) -> Unit,
    private val viewModel: WishlistViewModel
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<WishlistKeys> {

    @SuppressLint("SetTextI18n")
    override fun bind(item: WishlistKeys) {
        with(binding) {
            tvDescripionProduct.text = item.productName
            ivProduct.load(item.image)
            tvTitleResultPrice.text= item.productPrice.toCurrencyFormat()
            tvTitleStore.text = item.store
            btnOrderNow.text = btnOrder
            tvTotalSell.text = "${itemView.context.getString(R.string.string_hasil_penjualan)} ${item.sale}"
            tvRating.text= item.productRating.toString()
            ivStar.load(R.drawable.ic_star)
            icProduct.load(R.drawable.ic_profile_user)
            ivTrash.load(R.drawable.ic_trash)
        }
        binding.ivTrash.setOnClickListener {
            viewModel.deleteWishlist(item.productId)
        }
        binding.root.setOnClickListener {
            onClickListener(item)
        }
    }

    companion object {
        val btnOrder ="Shop"
    }
}