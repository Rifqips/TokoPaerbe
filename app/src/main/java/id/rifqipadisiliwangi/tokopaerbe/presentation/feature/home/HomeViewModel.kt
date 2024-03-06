package id.rifqipadisiliwangi.tokopaerbe.presentation.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.rifqipadisiliwangi.core.domain.usecase.AuthInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val authInteractor: AuthInteractor
) : ViewModel() {

    private val _isUsername = MutableLiveData<String>()
    val isUsername : LiveData<String>
        get() = _isUsername

    fun getUsernameLiveData()  {
        viewModelScope.launch(Dispatchers.IO) {
            val username = authInteractor.getUsername()
            _isUsername.postValue(username)
        }
    }
}