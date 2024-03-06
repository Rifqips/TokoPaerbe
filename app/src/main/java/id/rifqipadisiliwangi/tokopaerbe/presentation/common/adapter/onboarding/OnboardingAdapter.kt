package id.rifqipadisiliwangi.tokopaerbe.presentation.common.adapter.onboarding


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.rifqipadisiliwangi.tokopaerbe.databinding.ImageContainerOnboardingBinding

class OnboardingAdapter(
    private val imageList: ArrayList<Int>
) : RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val binding = ImageContainerOnboardingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OnboardingViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        val currentItem = imageList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    inner class OnboardingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding: ImageContainerOnboardingBinding? = ImageContainerOnboardingBinding.bind(itemView)

        fun bind(imageResource: Int) {
            binding?.apply {
                onboardingImage.setImageResource(imageResource)
            }
        }
    }
}