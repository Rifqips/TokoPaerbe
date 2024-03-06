package id.rifqipadisiliwangi.tokopaerbe.presentation.feature.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import id.rifqipadisiliwangi.core.domain.model.history.DataHistory
import id.rifqipadisiliwangi.core.domain.model.rating.RatingRequest
import id.rifqipadisiliwangi.core.domain.model.rating.RatingResponse
import id.rifqipadisiliwangi.core.domain.model.refresh.TokenRefreshRequest
import id.rifqipadisiliwangi.core.domain.model.refresh.TokenRefreshResponse
import id.rifqipadisiliwangi.core.domain.model.search.SearchResponse
import id.rifqipadisiliwangi.core.domain.paging.StorePagingSource
import id.rifqipadisiliwangi.core.domain.usecase.AuthInteractor
import id.rifqipadisiliwangi.core.domain.usecase.StoreInteractor
import id.rifqipadisiliwangi.core.utils.ResultWrapper
import id.rifqipadisiliwangi.tokopaerbe.presentation.common.adapter.store.StoreAdapterItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StoreListViewModel(
    private val authInteractor : AuthInteractor,
    private val storeInteractor : StoreInteractor,
) : ViewModel(){

    private val _isStateShimmer = MutableLiveData<Boolean>()
    val isStateShimmer : LiveData<Boolean>
        get() = _isStateShimmer


    private val _isUserToken = MutableLiveData<String>()
    val isUserToken: LiveData<String>
        get() = _isUserToken

    private val _isCodeStore = MutableLiveData<Int>()
    val isCodeStore : LiveData<Int>
        get() = _isCodeStore

    private val _refreshResult = MutableLiveData<ResultWrapper<TokenRefreshResponse>>()
    val refreshResult: LiveData<ResultWrapper<TokenRefreshResponse>>
        get() = _refreshResult

    private val _keySearch = MutableLiveData<String>()
    val keySearchValue : LiveData<String>
        get() = _keySearch

    private val _transactionProduct = MutableLiveData<ResultWrapper<List<DataHistory>>>()
    val transactionProduct : LiveData<ResultWrapper<List<DataHistory>>>
        get() = _transactionProduct


    private val _searchSuggestionResult = MutableLiveData<ResultWrapper<SearchResponse>>()
    val SearchSuggestionResult: LiveData<ResultWrapper<SearchResponse>>
        get() = _searchSuggestionResult

    private val _userRating = MutableLiveData<ResultWrapper<RatingResponse>>()
    val userRating : LiveData<ResultWrapper<RatingResponse>> = _userRating

    fun userRating (userRatingRequest: RatingRequest){
        viewModelScope.launch(Dispatchers.IO) {
            storeInteractor.userRating(userRatingRequest).collect{
                _userRating.postValue(it)
            }
        }
    }

    fun stateShimmer(state : Boolean){
        viewModelScope.launch(Dispatchers.IO) {  }
        _isStateShimmer.postValue(state)
    }
    fun keySearch(){
        viewModelScope.launch(Dispatchers.IO) {
            val keySearch = storeInteractor.getKeySearch()
            _keySearch.postValue(keySearch)
        }
    }

    fun saveKeySearch(searchItem: String){
        viewModelScope.launch (Dispatchers.IO){
            storeInteractor.saveKeySearch(searchItem)
        }
    }

    fun removeKeySearch(){
        viewModelScope.launch (Dispatchers.IO){
            storeInteractor.removeKeySearch()
        }
    }


    fun getToken(){
        viewModelScope.launch(Dispatchers.IO) {
            val tokenStatus = authInteractor.getUserToken()
            _isUserToken.postValue(tokenStatus)
        }
    }

    fun refreshToken(request: TokenRefreshRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            authInteractor.fetchRefreshToken(request).collect{
                _refreshResult.postValue(it)
            }
        }
    }
    fun searchSuggestion(query : String){
        viewModelScope.launch(Dispatchers.IO) {
            storeInteractor.searchSuggestion(query = query).collect {
                _searchSuggestionResult.postValue(it)
            }
        }
    }
    fun loadPostsWithKeyword(
        adapter: StoreAdapterItem,
        searchItem: String?,
        brandItem: String?,
        sortItem: String?
    ) {
        viewModelScope.launch {
            val response =  storeInteractor.productStore(
                searchItem = searchItem,
                brandItem = brandItem,
                sortItem = sortItem,
                limitItem = 50,
                pageItem = 1
            )
            _isCodeStore.postValue(response.code)
            if (response.code == 200) {
                val postsResponse = response.data
                postsResponse.let {
                    val store = it.items
                    adapter.submitData(PagingData.from(store))
                }
            }
        }
    }

    val storeList = Pager(PagingConfig(pageSize = 4)) {
        StorePagingSource(storeInteractor)
    }.liveData.cachedIn(viewModelScope)

    val appLayoutStoreLiveData = storeInteractor.getAppLayoutFlowStore().asLiveData(Dispatchers.IO)

    fun setUpLayoutStore(isGridLayout : Boolean){
        viewModelScope.launch {
            storeInteractor.setAppLayoutPrefStore(isGridLayout)
        }
    }


    fun getAllTransaction() {
        viewModelScope.launch(Dispatchers.IO) {
            storeInteractor.getHistory().collect {
                _transactionProduct.postValue(it)
            }
        }
    }

}