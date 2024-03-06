package id.rifqipadisiliwangi.tokopaerbe.presentation.common.adapter.payment.choose

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.rifqipadisiliwangi.core.domain.model.payment.Payment
import id.rifqipadisiliwangi.tokopaerbe.databinding.ItemChoosePaymentBinding
import id.rifqipadisiliwangi.tokopaerbe.presentation.common.adapter.payment.method.PaymentMethodsAdapter


class ChoosePaymentAdapter(
    private val itemClickListener: PaymentMethodsAdapter.OnItemClickListener
) :
    ListAdapter<Payment, ChoosePaymentAdapter.ListViewHolder>(CartEntityDiffCallback()) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemChoosePaymentBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val payment = getItem(position)
        holder.bind(payment)

        holder.binding.rvPaymentChoose.layoutManager = LinearLayoutManager(holder.itemView.context)
        val adapter = PaymentMethodsAdapter(itemClickListener)
        holder.binding.rvPaymentChoose.adapter = adapter
        adapter.submitList(payment.item)
    }

    class ListViewHolder(var binding: ItemChoosePaymentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n", "ResourceAsColor")
        fun bind(data: Payment) {
            binding.apply {
                tvPaymentChoose.text = data.title

            }
        }
    }

    private class CartEntityDiffCallback : DiffUtil.ItemCallback<Payment>() {
        override fun areItemsTheSame(oldItem: Payment, newItem: Payment): Boolean {
            return oldItem.item == newItem.item
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Payment, newItem: Payment): Boolean {
            return oldItem == newItem
        }
    }
    interface OnItemClickListener {
        fun onItemClick(image: String, name: String)
    }
}