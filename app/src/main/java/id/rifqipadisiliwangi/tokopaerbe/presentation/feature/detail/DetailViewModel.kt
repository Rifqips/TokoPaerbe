package id.rifqipadisiliwangi.tokopaerbe.presentation.feature.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.rifqipadisiliwangi.core.data.local.database.cart.CartKeys
import id.rifqipadisiliwangi.core.data.local.database.wishlist.WishlistKeys
import id.rifqipadisiliwangi.core.domain.model.detail.StoreDetailData
import id.rifqipadisiliwangi.core.domain.usecase.AppRoomUseCase
import id.rifqipadisiliwangi.core.domain.usecase.StoreInteractor
import id.rifqipadisiliwangi.core.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(
    private val interactor : StoreInteractor,
    private val appRoominteractor : AppRoomUseCase,
): ViewModel() {

    private val _detailStore = MutableLiveData<ResultWrapper<StoreDetailData>>()
    val detailStore : LiveData<ResultWrapper<StoreDetailData>>
        get() = _detailStore

    private val _getIsFavorite = MutableLiveData<List<WishlistKeys>>()
    val getIsFavorite : LiveData<List<WishlistKeys>>
        get() = _getIsFavorite


    private val _chartById = MutableLiveData<List<CartKeys>>()
    val chartById : MutableLiveData<List<CartKeys>>
        get() = _chartById

    fun getChartById(productId : String){
        viewModelScope.launch(Dispatchers.IO) {
            appRoominteractor.getChartById(productId).collect{
                _chartById.postValue(it)
            }
        }
    }

    fun addWishlist(
        id: String,
        productName: String,
        productPrice: Int,
        image: String,
        store: String,
        productRating: Float,
        sale: Int,
        stock: Int,
        variantName: String,
        quantity: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            appRoominteractor.invoke(id, productName, productPrice, image, store, productRating, sale, stock, variantName, quantity)
        }
    }

    fun addCartList(
        id: String,
        productName: String,
        variantName: String,
        stock: Int,
        productPrice: Int,
        quantity: Int,
        image: String,
        isChecked: Boolean,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            appRoominteractor.addCartList(id, productName, variantName, stock, productPrice, quantity, image, isChecked)
        }
    }

    fun detailStore(productId : String){
        // handling try catch on respositry, if use retrofit response. build in custom call adapter
        viewModelScope.launch(Dispatchers.IO) {
            interactor.detailStore(productId).collect{
                _detailStore.postValue(it)
            }
        }
    }

    fun checkWishlistFavorite(productId: String){
        viewModelScope.launch(Dispatchers.IO){
            appRoominteractor.getIsFavorite(productId).collect{
                _getIsFavorite.postValue(it)
            }
        }
    }
}