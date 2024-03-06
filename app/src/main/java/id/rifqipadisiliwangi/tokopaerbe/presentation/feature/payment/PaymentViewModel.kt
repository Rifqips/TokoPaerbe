package id.rifqipadisiliwangi.tokopaerbe.presentation.feature.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.rifqipadisiliwangi.core.domain.model.payment.PaymentResponse
import id.rifqipadisiliwangi.core.domain.repository.firebase.FirebaseRepository

class PaymentViewModel(private val firebaseRepository: FirebaseRepository):ViewModel() {

    private val _remoteResult = MutableLiveData<PaymentResponse>()
    val remoteResult : LiveData<PaymentResponse> = _remoteResult

    private val _remoteResultUpdate = MutableLiveData<PaymentResponse>()
    val remoteResultUpdate : LiveData<PaymentResponse> = _remoteResultUpdate

    fun fetchData() {
        firebaseRepository.getPaymentConfig { paymentConfig ->
            _remoteResult.postValue(paymentConfig)
        }
    }

    fun fetchUpdate(){
        firebaseRepository.getUpdatePaymentConfig { updatePayment ->
            _remoteResultUpdate.postValue(updatePayment)
        }
    }
}