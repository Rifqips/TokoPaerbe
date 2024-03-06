package id.rifqipadisiliwangi.tokopaerbe.presentation.feature.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.rifqipadisiliwangi.core.domain.model.review.DataResponseReviewItemDataResponse
import id.rifqipadisiliwangi.core.domain.usecase.StoreInteractor
import id.rifqipadisiliwangi.core.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReviewViewModel(
    private val interactor: StoreInteractor
):ViewModel() {

    private val _reviewProduct = MutableLiveData<ResultWrapper<List<DataResponseReviewItemDataResponse>>>()
    val reviewProduct: LiveData<ResultWrapper<List<DataResponseReviewItemDataResponse>>>
        get() = _reviewProduct

    fun getAllReview(idProduct: String) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.reviewProduct(idProduct).collect {
                _reviewProduct.postValue(it)
            }
        }
    }

}