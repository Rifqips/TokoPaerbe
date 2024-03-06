package id.rifqipadisiliwangi.tokopaerbe.presentation.common.adapter.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.rifqipadisiliwangi.core.domain.model.notification.NotificationDataItem
import id.rifqipadisiliwangi.tokopaerbe.R
import id.rifqipadisiliwangi.tokopaerbe.databinding.ItemNotificationBinding
import id.rifqipadisiliwangi.tokopaerbe.utils.Constant.dummyDatetime
import id.rifqipadisiliwangi.tokopaerbe.utils.Constant.dummyNotif
import id.rifqipadisiliwangi.tokopaerbe.utils.Constant.information
import id.rifqipadisiliwangi.tokopaerbe.utils.Constant.paymentSucces

class NotificationAdapter(
private val itemList: List<NotificationDataItem>
) :
RecyclerView.Adapter<NotificationAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val binding = ItemNotificationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false
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

    inner class ItemViewHolder(private val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NotificationDataItem) {
            with(binding){
                tvTitleResultNotificaiton.text = item.title
                ivNotification.load(R.drawable.img_default)
                tvInformation.text = information
                tvTitleResultNotificaiton.text = paymentSucces
                tvDateNotification.text = dummyDatetime
                tvDescriptionNotification.text = dummyNotif
            }
        }
    }
}