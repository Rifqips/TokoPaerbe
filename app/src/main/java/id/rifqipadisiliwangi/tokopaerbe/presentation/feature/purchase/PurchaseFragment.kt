package id.rifqipadisiliwangi.tokopaerbe.presentation.feature.purchase

import id.rifqipadisiliwangi.tokopaerbe.databinding.FragmentPurchaseBinding
import id.rifqipadisiliwangi.tokopaerbe.presentation.feature.home.HomeViewModel
import id.rifqipadisiliwangi.tokopaerbe.utils.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class PurchaseFragment : BaseFragment<FragmentPurchaseBinding, HomeViewModel>(FragmentPurchaseBinding::inflate){

    override val viewModel: HomeViewModel by viewModel()

    override fun initView() {
        TODO("Not yet implemented")
    }

    override fun initListener() {
        TODO("Not yet implemented")
    }
}