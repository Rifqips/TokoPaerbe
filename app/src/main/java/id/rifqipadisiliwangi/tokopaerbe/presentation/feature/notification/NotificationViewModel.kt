package id.rifqipadisiliwangi.tokopaerbe.presentation.feature.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.rifqipadisiliwangi.core.data.local.database.notification.NotificationsKeys
import id.rifqipadisiliwangi.core.domain.usecase.AppRoomUseCase
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val appRoomUseCase: AppRoomUseCase
):ViewModel() {


    fun createNotification(
        notifId: Int,
        notifType: String,
        notifTitle: String,
        notifBody: String,
        notifDate: String,
        notifTime: String,
        notifImage: String,
        isChecked: Boolean
    ) {
        viewModelScope.launch {
            appRoomUseCase.createNotification(
                notifId,
                notifType,
                notifTitle,
                notifBody,
                notifDate,
                notifTime,
                notifImage,
                isChecked
            )
        }
    }

    fun getNotification(): LiveData<List<NotificationsKeys>?> {
        return appRoomUseCase.getNotifications()
    }

    fun notifIsChecked(id: Int, isChecked: Boolean) {
        return appRoomUseCase.notifIsChecked(id, isChecked)
    }
}