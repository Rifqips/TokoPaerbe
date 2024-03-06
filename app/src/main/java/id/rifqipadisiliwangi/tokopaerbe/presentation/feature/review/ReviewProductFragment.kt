package id.rifqipadisiliwangi.tokopaerbe.presentation.feature.review

import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import id.rifqipadisiliwangi.core.data.network.api.model.store.ResponseItemDataStore
import id.rifqipadisiliwangi.core.utils.proceedWhen
import id.rifqipadisiliwangi.tokopaerbe.databinding.FragmentReviewProductBinding
import id.rifqipadisiliwangi.tokopaerbe.presentation.common.adapter.review.ReviewAdapter
import id.rifqipadisiliwangi.tokopaerbe.utils.Constant.review_bundle
import id.rifqipadisiliwangi.tokopaerbe.utils.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReviewProductFragment : BaseFragment<FragmentReviewProductBinding, ReviewViewModel>(FragmentReviewProductBinding::inflate) {

    private val adapterReview : ReviewAdapter by lazy {
        ReviewAdapter{}
    }

    override val viewModel: ReviewViewModel by viewModel()
    override fun initView() {
        getBundle()
        observeData()
        setUpRv()
    }

    override fun initListener() {
        with(binding){
            menuToolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun observeData(){
        viewModel.reviewProduct.observe(viewLifecycleOwner){
            with(binding){
                it.proceedWhen(
                    doOnSuccess = {
                        binding.rvReviews.isVisible = true
                        binding.layoutStateReviews.pbLoading.isVisible = false
                        binding.layoutStateReviews.tvError.isVisible = false
                        binding.rvReviews.apply {
                            layoutManager = LinearLayoutManager(context)
                            adapter = adapter
                        }
                        it.payload?.let { data ->
                            adapterReview.setData(data)
                            binding.rvReviews.smoothScrollToPosition(0)
                        }
                    },
                    doOnLoading = {
                        binding.rvReviews.isVisible = false
                        binding.layoutStateReviews.pbLoading.isVisible = true
                        binding.layoutStateReviews.tvError.isVisible = false
                    },
                )
            }

        }
    }
    private fun setUpRv(){
        binding.rvReviews.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterReview
            adapterReview.refreshList()
        }
    }

    private fun getBundle(){
        arguments?.let {
            it.getParcelable<ResponseItemDataStore>(review_bundle)?.let { detail ->
                val productId = detail.productId
                if (productId != null) {
                    viewModel.getAllReview(productId)
                }
            }
        }
    }

}