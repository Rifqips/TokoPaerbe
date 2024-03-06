package id.rifqipadisiliwangi.tokopaerbe.presentation.common.adapter.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.rifqipadisiliwangi.core.data.local.database.cart.CartKeys
import id.rifqipadisiliwangi.tokopaerbe.R
import id.rifqipadisiliwangi.tokopaerbe.databinding.ItemCartProductBinding
import id.rifqipadisiliwangi.tokopaerbe.utils.Constant.counting_value
import id.rifqipadisiliwangi.tokopaerbe.utils.Constant.minus
import id.rifqipadisiliwangi.tokopaerbe.utils.Constant.plus
import id.rifqipadisiliwangi.tokopaerbe.utils.Constant.result_counting
import id.rifqipadisiliwangi.tokopaerbe.utils.Constant.stock_minimum
import id.rifqipadisiliwangi.tokopaerbe.utils.Helper.removeSquareBrackets
import id.rifqipadisiliwangi.tokopaerbe.utils.Helper.toCurrencyFormat

class CartAdapter(
    private var itemList: List<CartKeys>,
    private val itemSelected : (CartKeys, Boolean) -> Unit,
    private val itemSelectedPlus : (CartKeys) -> Unit,
    private val itemSelectedMinus : (CartKeys) -> Unit,
    private val itemDelete : (CartKeys, Int) -> Unit,
) : RecyclerView.Adapter<CartAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val binding = ItemCartProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setlistCart(listCart: List<CartKeys>){
        this.itemList = listCart
        notifyDataSetChanged()
    }


    inner class ItemViewHolder(private val binding: ItemCartProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: CartKeys) {
            with(binding) {
                ivProduct.load(item.image)
                ivDelete.load(R.drawable.ic_trash)
                countingPlus(item)
                countingMinus(item)
                tvDescripionProduct.text= item.productName
                tvTitleStorage.text = item.variantName.removeSquareBrackets()
                tvTitleStock.text = item.stock.toString()
                tvProductPrice.text = item.productPrice.toCurrencyFormat()
                tvCountNumber.text = "1"
                tvMinus.text = minus
                tvPlus.text = plus
                ivDelete.setOnClickListener { itemDelete(item, item.productPrice) }
                cbItem.isChecked = item.isChecked
                cbItem.setOnCheckedChangeListener { _, isChecked ->
                    itemSelected(item, isChecked)
                    when(isChecked){
                        true ->{
                            tvMinus.isClickable  = true
                            tvPlus.isClickable  = true
                        }
                        false -> {
                            tvMinus.isClickable  = false
                            tvPlus.isClickable  = false
                        }
                    }
                }
                if (item.stock < stock_minimum) {
                    tvTitleStock.text = "${itemView.context.getString(R.string.string_dummy_stock)} ${item.stock}"
                    tvTitleStock.setTextColor(itemView.context.getColor(R.color.red_splash))
                } else {
                    tvTitleStock.text = "${itemView.context.getString(R.string.string_dummy_stock)} ${item.stock}"
                }
            }
        }

        private fun countingPlus(item: CartKeys){
            binding.tvPlus.setOnClickListener {
                if (result_counting < item.stock){
                    itemSelectedPlus(item)
                    result_counting++
                    resultPrice()
                    item.quantity = result_counting
                }
            }
        }
        private fun countingMinus(item: CartKeys){
            binding.tvMinus.setOnClickListener {
                if (result_counting >  counting_value) {
                    itemSelectedMinus(item)
                    result_counting--
                    resultPrice()
                    item.quantity = result_counting
                }
            }
        }

        private fun resultPrice(){
            binding.tvCountNumber.text = result_counting.toString()
        }

    }

}