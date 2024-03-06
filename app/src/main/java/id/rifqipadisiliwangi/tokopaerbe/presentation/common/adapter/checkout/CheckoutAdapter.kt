package id.rifqipadisiliwangi.tokopaerbe.presentation.common.adapter.checkout

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.rifqipadisiliwangi.core.domain.model.checkout.CheckoutDataClass
import id.rifqipadisiliwangi.tokopaerbe.R
import id.rifqipadisiliwangi.tokopaerbe.databinding.ItemCheckoutBinding
import id.rifqipadisiliwangi.tokopaerbe.utils.Helper.removeSquareBrackets
import id.rifqipadisiliwangi.tokopaerbe.utils.Helper.toCurrencyFormat


class CheckoutAdapter(
    private val listCheckoutProduct: List<CheckoutDataClass>,
    private val itemPrice : (Int) -> Unit,
    private val itemSelectedPlus : (CheckoutDataClass) -> Unit,
    private val itemSelectedMinus : (CheckoutDataClass) -> Unit,
) : RecyclerView.Adapter<CheckoutAdapter.ItemViewHolder>() {

    private var totalToggleValue = 1
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i: Int
    ): ItemViewHolder {
        val binding =
            ItemCheckoutBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = listCheckoutProduct[position]
        holder.bind(item)

    }
    inner class ItemViewHolder(private val binding: ItemCheckoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: CheckoutDataClass) {
            with(binding) {
                countingPlus(item)
                countingMinus(item)
                itemPrice(item.productPrice * item.productQuantity)
                itemImage.load(item.productImage)
                itemTitle.text = item.productName
                variant.text = item.productVariant.removeSquareBrackets()
                toggleTotal.text = item.productQuantity.toString()
                itemPrice.text = item.productPrice.toCurrencyFormat()
                if (item.productStock < 5) {
                    stock.text = "${itemView.resources.getString(R.string.string_sisa)} ${item.productStock}"
                    stock.setTextColor(itemView.context.getColor(R.color.red_splash))
                }else{
                    stock.text = "${itemView.resources.getString(R.string.string_sisa)} ${item.productStock}"
                }
            }
        }

        private fun countingPlus(item: CheckoutDataClass){
            binding.togglePlus.setOnClickListener {
                val currentTotal = binding.toggleTotal.text.toString().toInt()
                val newTotal = (currentTotal + 1).coerceAtMost(item.productStock)
                binding.toggleTotal.text = newTotal.toString()
                totalToggleValue = newTotal
                item.productQuantity = totalToggleValue
                itemSelectedPlus(item)
            }
        }
        private fun countingMinus(item: CheckoutDataClass){
            binding.toggleMinus.setOnClickListener {
                val currentTotal = binding.toggleTotal.text.toString().toInt()
                val newTotal = (currentTotal - 1).coerceAtLeast(1)
                binding.toggleTotal.text = newTotal.toString()
                totalToggleValue = newTotal
                item.productQuantity = totalToggleValue
                itemSelectedMinus(item)
            }
        }

    }

    override fun getItemCount(): Int = listCheckoutProduct.size

    interface OnItemClickListener {
        fun onItemClick(position: Int, item: CheckoutDataClass)
    }
}
