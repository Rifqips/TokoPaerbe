package id.rifqipadisiliwangi.tokopaerbe.presentation.feature.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.rifqipadisiliwangi.core.domain.model.profile.RequestRegisterProfileItem
import id.rifqipadisiliwangi.core.domain.usecase.AuthInteractor
import id.rifqipadisiliwangi.core.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class RegisterProfileViewModel(
    private val interactor: AuthInteractor
) : ViewModel() {

    private val _registerProfileResult = MutableLiveData<ResultWrapper<RequestRegisterProfileItem>>()
    val registerProfileResult: LiveData<ResultWrapper<RequestRegisterProfileItem>>
        get() = _registerProfileResult

    fun userRegisterProfile(
        userName: RequestBody?,
        userImage: MultipartBody.Part?
    ){
        viewModelScope.launch(Dispatchers.IO) {
            interactor.fetchRegisterProfile(userName, userImage).collect {
                _registerProfileResult.postValue(it)
            }
        }
    }

    fun saveUsername(username : String){
        viewModelScope.launch {
            interactor.saveUsername(username)
        }
    }
    fun debugScreenView(debug : String){
        viewModelScope.launch(Dispatchers.IO) {
            interactor.debugSreenView(debug)
        }
    }
}