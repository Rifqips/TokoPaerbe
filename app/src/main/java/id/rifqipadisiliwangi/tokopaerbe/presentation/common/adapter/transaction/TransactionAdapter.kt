package id.rifqipadisiliwangi.tokopaerbe.presentation.common.adapter.transaction

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.rifqipadisiliwangi.core.domain.model.history.DataHistory
import id.rifqipadisiliwangi.tokopaerbe.R
import id.rifqipadisiliwangi.tokopaerbe.databinding.ItemProductTransactionBinding
import id.rifqipadisiliwangi.tokopaerbe.presentation.common.adapter.ViewHolderBinder
import id.rifqipadisiliwangi.tokopaerbe.utils.Helper.toCurrencyFormat

class TransactionAdapter(
    private val onClickListener: (DataHistory) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataDiffer = AsyncListDiffer(
        this,
        object : DiffUtil.ItemCallback<DataHistory>() {
            override fun areItemsTheSame(oldItem: DataHistory, newItem: DataHistory): Boolean {
                return oldItem.invoiceId == newItem.invoiceId
            }

            override fun areContentsTheSame(oldItem: DataHistory, newItem: DataHistory): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            else -> {
                LinearMenuItemViewHolder(
                    binding = ItemProductTransactionBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onClickListener
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolderBinder<DataHistory>).bind(dataDiffer.currentList[position])
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    fun setData(data: List<DataHistory>) {
        dataDiffer.submitList(data)
    }

    fun refreshList() {
        notifyItemRangeChanged(0, dataDiffer.currentList.size)
    }

    class LinearMenuItemViewHolder(
        private val binding: ItemProductTransactionBinding,
        private val onClickListener: (DataHistory) -> Unit
    ) : RecyclerView.ViewHolder(binding.root),
        ViewHolderBinder<DataHistory> {
        @SuppressLint("SetTextI18n")
        override fun bind(item: DataHistory) {
            with(binding) {
                btnReview.text = itemView.context.getString(R.string.string_ulas)
                ivProduct.load(item.image)
                ivShop.load(R.drawable.ic_shop_bag)
                tvResultDate.text = item.date
                tvDescripionProduct.text = item.name
                tvTitleTotalShop.text = itemView.resources.getString(R.string.string_total_belanja)
                val quantity = item.items.map { it.quantity }[0]
                tvResultQuantity.text =
                    "$quantity ${itemView.resources.getString(R.string.string_dummy_quantity)}"
                tvPriceTotalShop.text = item.total.toCurrencyFormat()
                when (item.status) {
                    true.toString() -> tvStatus.text =
                        itemView.context.resources.getString(R.string.string_selesai)

                    false.toString() -> tvStatus.text =
                        itemView.context.resources.getString(R.string.string_selesai)
                }
            }

            binding.root.setOnClickListener {
                onClickListener(item)
            }
        }
    }
}