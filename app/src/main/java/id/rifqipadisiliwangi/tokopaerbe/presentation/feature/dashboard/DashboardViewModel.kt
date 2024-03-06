package id.rifqipadisiliwangi.tokopaerbe.presentation.feature.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import id.rifqipadisiliwangi.core.domain.usecase.AuthInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val authInteractor: AuthInteractor
):ViewModel() {

    val appThemeLiveData = authInteractor.getStateDarkmode().asLiveData(Dispatchers.IO)
    var appLocaleLiveData = authInteractor.getStateLocale().asLiveData(Dispatchers.IO)


    private val _isUserRefreshToken = MutableLiveData<Boolean>()
    val isUserRefreshToken: LiveData<Boolean>
        get() = _isUserRefreshToken
    fun setThemeDarkmode(isActive : Boolean){
        viewModelScope.launch {
            authInteractor.saveStateDarkmode(isActive)
        }
    }

    fun setLocale(isActive : Boolean){
        viewModelScope.launch {
            authInteractor.saveStateLocale(isActive)
        }
    }

    fun deleteData(){
        viewModelScope.launch(Dispatchers.IO) {
            authInteractor.deleteData()
        }
    }

    fun getRefreshToken(){
        viewModelScope.launch(Dispatchers.IO) {
            val refreshTokenStatus = authInteractor.getRefreshToken().firstOrNull() != null
            _isUserRefreshToken.postValue(refreshTokenStatus)
        }
    }
}