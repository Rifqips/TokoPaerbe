package id.rifqipadisiliwangi.tokopaerbe.presentation.feature.store

import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import id.rifqipadisiliwangi.core.data.network.api.model.store.ResponseItemDataStore
import id.rifqipadisiliwangi.core.domain.model.refresh.TokenRefreshRequest
import id.rifqipadisiliwangi.tokopaerbe.R
import id.rifqipadisiliwangi.tokopaerbe.databinding.FragmentStoreBinding
import id.rifqipadisiliwangi.tokopaerbe.presentation.common.adapter.load.LoadStateAdapter
import id.rifqipadisiliwangi.tokopaerbe.presentation.common.adapter.store.StoreAdapterItem
import id.rifqipadisiliwangi.tokopaerbe.presentation.feature.filter.FilterFragment
import id.rifqipadisiliwangi.tokopaerbe.utils.Constant.detail_bundle
import id.rifqipadisiliwangi.tokopaerbe.utils.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class StoreFragment :
    BaseFragment<FragmentStoreBinding, StoreListViewModel>(FragmentStoreBinding::inflate), FilterFragment.OnFilterListener{

    private val filterFragment: FilterFragment by lazy { FilterFragment() }
    override val viewModel: StoreListViewModel by viewModel()

    private val adapterStore: StoreAdapterItem by lazy {
        StoreAdapterItem{
            navigateToDetail(it)
        }
    }
    override fun initView(){
        if(filterFragment.isAdded) { return }
        refreshToken()
        setUpPaging()
        observeVm()
        loadPostsWithKeyword(adapter = adapterStore)
        with(binding){
            cpFilter.text = getString(R.string.string_filter)
            cpFilter.text = getString(R.string.string_filter)
            chipOne.text = getString(R.string.label)
            chipTwo.text = getString(R.string.label)
            chipThree.text = getString(R.string.label)
            layoutStateSearch.tvSearch.text = getString(R.string.string_search)
        }
    }

    override fun initListener() {
        with(binding) {
            cpFilter.setOnClickListener {
                filterFragment.setFilterListener(this@StoreFragment)
                filterFragment.show(childFragmentManager, getString(R.string.string_filter))

            }
            clSearchBar.setOnClickListener {
                viewModel.removeKeySearch()
                navigateToSearch()
            }
            rvStore.adapter = adapterStore.withLoadStateFooter(
                footer = LoadStateAdapter { adapterStore.retry() }
            )
        }
    }
    override fun onFilterApplied(
        brandItem: String?,
        sortItem: String?,
    ) {
        loadPostsWithKeyword(
            adapter = adapterStore,
            brandItem = brandItem,
            sortItem = sortItem
        )
    }

    private fun observeVm(){
        viewModel.keySearch()
        viewModel.keySearchValue.observe(viewLifecycleOwner){ keySearch ->
            when(keySearch){
                "" -> binding.layoutStateSearch.tvSearch.text = getString(R.string.string_search)
                else -> binding.layoutStateSearch.tvSearch.text = keySearch
            }
            loadPostsWithKeyword(
                adapter = adapterStore,
                searchItem = keySearch
            )
        }
        viewModel.isCodeStore.observe(viewLifecycleOwner){ code ->
            setStateError(code)
        }
    }
    private fun loadPostsWithKeyword(
        adapter: StoreAdapterItem,
        searchItem: String? = null,
        brandItem: String? = null,
        sortItem: String? = null,
    ) {
        viewModel.loadPostsWithKeyword(
            adapter,
            searchItem,
            brandItem?.lowercase(),
            sortItem?.lowercase(),
        )
    }


    private fun refreshToken(){
        viewModel.getToken()
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.isUserToken.observe(viewLifecycleOwner){ token ->
                val tokenData = TokenRefreshRequest(token)
                viewModel.refreshToken(tokenData)
                adapterStore.refresh()
                val swipeRefresh = binding.swipeRefreshLayout.isRefreshing
                if (swipeRefresh) { setUpPaging() }
            }
        }
    }

    private fun setUpPaging(){
        if (view != null){
            parentFragment?.viewLifecycleOwner?.let {
                viewModel.storeList.observe(it) { pagingData ->
                    adapterStore.submitData(lifecycle, pagingData)
                }
            }
        }
        adapterStore.addLoadStateListener { loadState ->
            with(binding){
                if (loadState.refresh is LoadState.Loading) {
                    initShimmer(false)
                    cgCategoryFilter.isInvisible = true
                    divider.isInvisible = true
                    ivChangeLayout.isInvisible = true
                    rvStore.isInvisible = true
                    swipeRefreshLayout.isRefreshing = true
                } else {
                    initShimmer(true)
                    shimmerStore.isGone = true
                    cgCategoryFilter.isVisible = true
                    swipeRefreshLayout.isRefreshing = false
                    ivChangeLayout.isInvisible = false
                    divider.isInvisible = false
                    rvStore.isInvisible = false
                    rvStore.isVisible = !binding.swipeRefreshLayout.isRefreshing
                    observeLayout()
                }
            }
        }
    }

    private fun initShimmer(stateShimmer : Boolean){
        if (view != null){
            viewModel.isStateShimmer.observe(viewLifecycleOwner){ isState ->
                when(isState){
                    true ->  {
                        binding.shimmerStore.isGone = true
                        binding.shimmerStoreGrid.isGone = stateShimmer
                    }
                    false -> {
                        binding.shimmerStore.isGone = stateShimmer
                        binding.shimmerStoreGrid.isGone = true
                    }
                }
            }
        }
    }

    private fun observeLayout() {
        with(binding){
            ivChangeLayout.setOnCheckedChangeListener  { _, isChecked ->
                viewModel.setUpLayoutStore(isChecked)
                when(isChecked){
                    true -> ivChangeLayout.chipIcon = context?.let { AppCompatResources.getDrawable(it, R.drawable.ic_grid) }
                    else -> ivChangeLayout.chipIcon = context?.let { AppCompatResources.getDrawable(it, R.drawable.ic_list_layout) }
                }
            }
            if (view != null){
                viewModel.appLayoutStoreLiveData.observe(viewLifecycleOwner) { isGridLayout ->
                    viewModel.stateShimmer(isGridLayout)
                    when(isGridLayout){
                        true -> {
                            ivChangeLayout.chipIcon = context?.let { AppCompatResources.getDrawable(it, R.drawable.ic_grid) }
                            adapterStore.toggleLayoutMode(isGridLayout)
                            rvStore.apply {
                                layoutManager = GridLayoutManager(context,2)
                                adapter = adapterStore
                            }
                        }
                        else -> {
                            ivChangeLayout.chipIcon = context?.let { AppCompatResources.getDrawable(it, R.drawable.ic_list_layout) }
                            adapterStore.toggleLayoutMode(isGridLayout)
                            rvStore.apply {
                                layoutManager = LinearLayoutManager(context)
                                adapter = adapterStore
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setStateError(code : Int){
        when(code){
            401, 402, 403, 404 ->
                binding.let {
                    it.stateErrorStore.isGone = false
                    it.stateErrorStore.setErrorMessage(
                        title = getString(R.string.string_empty),
                        description = getString(R.string.string_error_state_one),
                        btnTitle = getString(R.string.string_reset),
                        action = {}
                    )

                }
            500 ->
                binding.let {
                    it.stateErrorStore.isGone = false
                    it.stateErrorStore.setErrorMessage(
                        title = getString(R.string.string_connection),
                        description = getString(R.string.string_error_state_two),
                        btnTitle = getString(R.string.string_refresh),
                        action = {}
                    )

                }
        }
    }

    private fun navigateToDetail(storeDetail : ResponseItemDataStore){
        val bundle = Bundle()
        bundle.putParcelable(detail_bundle, storeDetail)
        activity?.supportFragmentManager?.findFragmentById(R.id.container_navigation)?.findNavController()?.navigate(R.id.action_homeFragment_to_detailProductFragment, bundle)
    }

    private fun navigateToSearch(){
        activity?.supportFragmentManager?.findFragmentById(R.id.container_navigation)?.findNavController()?.navigate(R.id.action_homeFragment_to_searchStoreFragment)
    }

}
