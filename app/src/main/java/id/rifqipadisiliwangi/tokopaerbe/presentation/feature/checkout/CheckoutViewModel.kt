package id.rifqipadisiliwangi.tokopaerbe.presentation.feature.checkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.rifqipadisiliwangi.core.domain.model.fulfillment.FulfillmentRequest
import id.rifqipadisiliwangi.core.domain.model.fulfillment.FulfillmentResponse
import id.rifqipadisiliwangi.core.domain.usecase.StoreInteractor
import id.rifqipadisiliwangi.core.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CheckoutViewModel(
    private val storeInteractor : StoreInteractor,
):ViewModel() {

    private val _fulfillmentResult = MutableLiveData<ResultWrapper<FulfillmentResponse>>()
    val fulfillmentResult: LiveData<ResultWrapper<FulfillmentResponse>> = _fulfillmentResult
    fun postFulfillment(request: FulfillmentRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            storeInteractor.postFullFillment(request).collect{
                _fulfillmentResult.postValue(it)
            }
        }
    }
}