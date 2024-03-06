package id.rifqipadisiliwangi.tokopaerbe.presentation.common.adapter.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.rifqipadisiliwangi.core.domain.model.review.DataResponseReviewItemDataResponse
import id.rifqipadisiliwangi.tokopaerbe.databinding.ItemReviewUsersBinding
import id.rifqipadisiliwangi.tokopaerbe.presentation.common.adapter.ViewHolderBinder

class ReviewAdapter(
    private val onClickListener: (DataResponseReviewItemDataResponse) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataDiffer = AsyncListDiffer(
        this,
        object : DiffUtil.ItemCallback<DataResponseReviewItemDataResponse>() {
            override fun areItemsTheSame(oldItem: DataResponseReviewItemDataResponse, newItem: DataResponseReviewItemDataResponse): Boolean {
                return oldItem.userName == newItem.userName
            }

            override fun areContentsTheSame(oldItem: DataResponseReviewItemDataResponse, newItem: DataResponseReviewItemDataResponse): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            else -> {
                LinearMenuItemViewHolder(
                    binding = ItemReviewUsersBinding.inflate(
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
        (holder as ViewHolderBinder<DataResponseReviewItemDataResponse>).bind(dataDiffer.currentList[position])
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    fun setData(data: List<DataResponseReviewItemDataResponse>) {
        dataDiffer.submitList(data)
    }

    fun refreshList() {
        notifyItemRangeChanged(0, dataDiffer.currentList.size)
    }

    class LinearMenuItemViewHolder(
        private val binding: ItemReviewUsersBinding,
        private val onClickListener: (DataResponseReviewItemDataResponse) -> Unit
    ) : RecyclerView.ViewHolder(binding.root),
        ViewHolderBinder<DataResponseReviewItemDataResponse> {
        override fun bind(item: DataResponseReviewItemDataResponse) {
            with(binding){
                ivUser.load(item.userImage)
                tvUsername.text = item.userName
                tvDescriptionReview.text = item.userReview
                ratingProduct.rating = item.userRating.toFloat()
            }

            binding.root.setOnClickListener {
                onClickListener(item)
            }
        }
    }
}