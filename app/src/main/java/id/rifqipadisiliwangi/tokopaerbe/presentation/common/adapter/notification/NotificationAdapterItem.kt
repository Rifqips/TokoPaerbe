package id.rifqipadisiliwangi.tokopaerbe.presentation.common.adapter.notification

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.color.MaterialColors
import id.rifqipadisiliwangi.core.data.local.database.notification.NotificationsKeys
import id.rifqipadisiliwangi.tokopaerbe.R
import id.rifqipadisiliwangi.tokopaerbe.databinding.ItemNotificationBinding


class NotificationAdapterItem(
    private val onNotificationsClick: (NotificationsKeys) -> Unit
) : ListAdapter<NotificationsKeys, NotificationAdapterItem.ListViewHolder>(
    NotificationsEntityDiffCallback()
) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemNotificationBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val notifEntity = getItem(position)
        holder.bind(notifEntity)

        holder.itemView.setOnClickListener {
            onNotificationsClick(notifEntity)
        }
    }

    class ListViewHolder(var binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n", "ResourceAsColor")
        fun bind(data: NotificationsKeys) {
            binding.apply {
                ivNotification.load(data.notifImage)
                tvInformation.text = data.notifType
                tvTitleResultNotificaiton.text = data.notifTitle
                tvDescriptionNotification.text = data.notifBody
                tvDateNotification.text = "${data.notifDate}, ${data.notifTime}"

                if (!data.isChecked) {
                    cvNotif.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.grey
                        )
                    )
                } else {
                    cvNotif.setCardBackgroundColor(
                        MaterialColors.getColor(
                            itemView,
                            android.R.attr.windowBackground
                        )
                    )
                }
            }
        }
    }

    private class NotificationsEntityDiffCallback : DiffUtil.ItemCallback<NotificationsKeys>() {
        override fun areItemsTheSame(
            oldItem: NotificationsKeys,
            newItem: NotificationsKeys
        ): Boolean {
            return oldItem.notifId == newItem.notifId
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: NotificationsKeys,
            newItem: NotificationsKeys
        ): Boolean {
            return oldItem == newItem
        }
    }
}
