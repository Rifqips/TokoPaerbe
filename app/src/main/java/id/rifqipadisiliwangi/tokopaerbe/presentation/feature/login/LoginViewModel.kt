package id.rifqipadisiliwangi.tokopaerbe.presentation.feature.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.rifqipadisiliwangi.core.domain.model.login.LoginRequest
import id.rifqipadisiliwangi.core.domain.model.login.LoginResponse
import id.rifqipadisiliwangi.core.domain.usecase.AuthInteractor
import id.rifqipadisiliwangi.core.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val interactor: AuthInteractor
) : ViewModel() {

    private val _loginResult = MutableLiveData<ResultWrapper<LoginResponse>>()
    val loginResult: LiveData<ResultWrapper<LoginResponse>> = _loginResult

    fun userLogin(request: LoginRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.fetchLogin(request).collect{
                _loginResult.postValue(it)
            }
        }
    }
    fun debugScreenView(debug : String){
        viewModelScope.launch(Dispatchers.IO) {
            interactor.debugSreenView(debug)
        }
    }
}