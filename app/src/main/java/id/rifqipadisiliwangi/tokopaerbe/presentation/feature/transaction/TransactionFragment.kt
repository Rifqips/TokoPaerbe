package id.rifqipadisiliwangi.tokopaerbe.presentation.feature.transaction

import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import id.rifqipadisiliwangi.core.utils.proceedWhen
import id.rifqipadisiliwangi.tokopaerbe.databinding.FragmentTransactionBinding
import id.rifqipadisiliwangi.tokopaerbe.presentation.common.adapter.transaction.TransactionAdapter
import id.rifqipadisiliwangi.tokopaerbe.presentation.feature.store.StoreListViewModel
import id.rifqipadisiliwangi.tokopaerbe.utils.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class TransactionFragment : BaseFragment<FragmentTransactionBinding, StoreListViewModel>(FragmentTransactionBinding::inflate) {

    override val viewModel: StoreListViewModel by viewModel()

    private val adapterTransaction : TransactionAdapter by lazy {
        TransactionAdapter{}
    }

    override fun initView() {
        setUpRv()
        observeData()
    }

    override fun initListener() {}

    private fun observeData(){
        viewModel.getAllTransaction()
        viewModel.transactionProduct.observe(viewLifecycleOwner){
            it.proceedWhen(
                doOnSuccess = {
                    binding.rvTransaction.isVisible = true
                    binding.layoutStateTransaction.pbLoading.isVisible = false
                    binding.layoutStateTransaction.tvError.isVisible = false
                    it.payload?.let { data ->
                        adapterTransaction.setData(data)
                        binding.rvTransaction.smoothScrollToPosition(0)
                    }
                },
                doOnLoading = {
                    binding.rvTransaction.isVisible = false
                    binding.layoutStateTransaction.pbLoading.isVisible = true
                    binding.layoutStateTransaction.tvError.isVisible = false
                },
                doOnError = {},
            )
        }
    }


    private fun setUpRv(){
        binding.rvTransaction.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterTransaction
            adapterTransaction.refreshList()
        }
    }

}