package id.rifqipadisiliwangi.tokopaerbe.presentation.feature.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.rifqipadisiliwangi.core.data.local.database.cart.CartKeys
import id.rifqipadisiliwangi.core.domain.usecase.AppRoomUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(
    private val appRoomUseCase: AppRoomUseCase,
):ViewModel() {

    private val _isCheckedUpdated = MutableLiveData<Boolean>()
    val isCheckedUpdated: LiveData<Boolean> get() = _isCheckedUpdated

    private val _cartList = MutableLiveData<List<CartKeys>>()
    val cartList : LiveData<List<CartKeys>> =_cartList

    private val _priceList = MutableLiveData<Int>()
    val priceList : LiveData<Int> =_priceList

    fun setUpPrice(price : Int){
        viewModelScope.launch(Dispatchers.IO) {
            _priceList.postValue(price)
        }
    }

    fun getCartList(){
        viewModelScope.launch {
            appRoomUseCase.getCartList().observeForever {
                _cartList.postValue(it)
            }
        }
    }

    fun deleteAllcart(){
        viewModelScope.launch(Dispatchers.IO) {
            appRoomUseCase.deleteAllCart()
        }
    }

    fun deleteCartById(id : String) {
        viewModelScope.launch(Dispatchers.IO){
            appRoomUseCase.deleteCartById(id)
        }
    }

    fun getCartProduct(): LiveData<List<CartKeys>> {
        return appRoomUseCase.getCartList()
    }

    fun isChecked(id: String, isChecked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            appRoomUseCase.isChecked(id, isChecked)
        }
    }


    fun updateBooleanColumnAll(newvalue : Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            appRoomUseCase.updateBooleanColumnAll(newvalue)
        }
    }
}