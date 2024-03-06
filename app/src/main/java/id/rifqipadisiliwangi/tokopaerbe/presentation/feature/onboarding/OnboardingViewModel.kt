package id.rifqipadisiliwangi.tokopaerbe.presentation.feature.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import id.rifqipadisiliwangi.core.domain.usecase.AuthInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val authInteractor: AuthInteractor
): ViewModel() {

    val appOnboardingLiveData = authInteractor.getStateOnboarding().asLiveData(Dispatchers.IO)

    private val _isUserToken = MutableLiveData<Boolean>()
    val isUserToken: LiveData<Boolean>
        get() = _isUserToken


    fun setStateOnboarding(isActive : Boolean){
        viewModelScope.launch {
            authInteractor.saveStateOnboarding(isActive)
        }
    }

    fun getToken(){
        viewModelScope.launch(Dispatchers.IO) {
            val tokenStatus = authInteractor.getUserToken().firstOrNull() != null
            _isUserToken.postValue(tokenStatus)
        }
    }
}