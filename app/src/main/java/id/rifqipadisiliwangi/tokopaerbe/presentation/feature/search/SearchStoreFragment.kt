package id.rifqipadisiliwangi.tokopaerbe.presentation.feature.search

import android.text.Editable
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.doBeforeTextChanged
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import id.rifqipadisiliwangi.core.utils.proceedWhen
import id.rifqipadisiliwangi.tokopaerbe.R
import id.rifqipadisiliwangi.tokopaerbe.databinding.FragmentSearchStoreBinding
import id.rifqipadisiliwangi.tokopaerbe.presentation.common.adapter.search.SearchAdapter
import id.rifqipadisiliwangi.tokopaerbe.presentation.feature.store.StoreListViewModel
import id.rifqipadisiliwangi.tokopaerbe.utils.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchStoreFragment :
    BaseFragment<FragmentSearchStoreBinding, StoreListViewModel>(FragmentSearchStoreBinding::inflate) {

    override val viewModel: StoreListViewModel by viewModel()


    override fun initView() {
        binding.etSearchEdit.hint = getString(R.string.string_search)
    }

    override fun initListener() {
        with(binding){
            etSearchEdit.doBeforeTextChanged { text, _, _, _ ->
                searchSuggestion(text.toString())
                rvListSuggestion.isGone = etSearchEdit.text.toString().length <= 1
            }
            etSearchInput.setStartIconOnClickListener {
                rvListSuggestion.isGone = true
            }
            etSearchInput.setEndIconOnClickListener {
                etSearchEdit.text?.clear()
                rvListSuggestion.isGone = true
            }
        }
    }

    private fun searchSuggestion(searchItem: String){
        viewModel.searchSuggestion(searchItem)
        viewModel.SearchSuggestionResult.observe(viewLifecycleOwner){
            with(binding){
                it.proceedWhen(
                    doOnLoading = {
                        binding.layoutStateDetail.pbLoading.isVisible = true
                        binding.layoutStateDetail.tvError.isVisible = false
                    },
                    doOnSuccess = { it ->
                        binding.layoutStateDetail.pbLoading.isVisible = false
                        binding.layoutStateDetail.tvError.isVisible = true
                        rvListSuggestion.layoutManager = LinearLayoutManager(context)
                        rvListSuggestion.adapter =
                            it.payload?.let { it1 -> SearchAdapter(it1.data){
                                setEditTextSearch(it)
                                viewModel.saveKeySearch(it)
                                findNavController().popBackStack(R.id.searchStoreFragment,true)
                            } }
                    },
                )
            }
        }
    }

    private fun setEditTextSearch(content: String) {
        val editableContent = Editable.Factory.getInstance().newEditable(content)
        binding.etSearchEdit.text = editableContent
        binding.rvListSuggestion.isGone = true
    }
}