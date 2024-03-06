package id.rifqipadisiliwangi.tokopaerbe.presentation.feature.wishlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import id.rifqipadisiliwangi.core.data.local.database.wishlist.WishlistKeys
import id.rifqipadisiliwangi.core.data.local.datastore.datasource.AppPreferenceDataSource
import id.rifqipadisiliwangi.core.domain.usecase.AppRoomUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WishlistViewModel(
    private val appPreferencesDataSource : AppPreferenceDataSource,
    private val appRoominteractor : AppRoomUseCase,
): ViewModel() {
    val appLayoutWishlistLiveData = appPreferencesDataSource.getAppLayoutFlowWishList().asLiveData(Dispatchers.IO)

    fun setUpLayoutWishlist(isGridLayout: Boolean){
        viewModelScope.launch {
            appPreferencesDataSource.setAppLayoutPrefWishList(isGridLayout)
        }
    }

    private val _wishlist = MutableLiveData<List<WishlistKeys>>()
    val wishlist: LiveData<List<WishlistKeys>>
        get() = _wishlist

    fun getWishlist() {
        viewModelScope.launch(Dispatchers.IO) {
            appRoominteractor.getwishlist().collect{
                _wishlist.postValue(it)
            }
        }
    }

    fun deleteWishlist(id: String){
        viewModelScope.launch(Dispatchers.IO){
            appRoominteractor.deleteWishlist(id)
        }
    }


}