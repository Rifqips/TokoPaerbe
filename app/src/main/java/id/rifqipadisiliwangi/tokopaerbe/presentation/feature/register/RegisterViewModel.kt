package id.rifqipadisiliwangi.tokopaerbe.presentation.feature.register

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.rifqipadisiliwangi.core.domain.model.register.RegisterRequest
import id.rifqipadisiliwangi.core.domain.model.register.RegisterResponseItem
import id.rifqipadisiliwangi.core.domain.usecase.AuthInteractor
import id.rifqipadisiliwangi.core.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val interactor: AuthInteractor,
) : ViewModel() {

    private val _registerResult = MutableLiveData<ResultWrapper<RegisterResponseItem>>()
    val registerResult: LiveData<ResultWrapper<RegisterResponseItem>>  = _registerResult
    fun userRegister(request: RegisterRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.fetchRegister(request).collect {
                _registerResult.postValue(it)
            }
        }
    }

    fun logEventScreenView(eventName : String, bundle : Bundle){
        viewModelScope.launch(Dispatchers.IO) {
            interactor.logEvent(eventName, bundle)
        }
    }

    fun debugScreenView(debug : String){
        viewModelScope.launch(Dispatchers.IO) {
            interactor.debugSreenView(debug)
        }
    }
}