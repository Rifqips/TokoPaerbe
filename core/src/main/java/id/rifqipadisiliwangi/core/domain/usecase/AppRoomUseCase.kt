package id.rifqipadisiliwangi.core.domain.usecase

import androidx.lifecycle.LiveData
import id.rifqipadisiliwangi.core.data.local.database.cart.CartKeys
import id.rifqipadisiliwangi.core.data.local.database.notification.NotificationsKeys
import id.rifqipadisiliwangi.core.domain.repository.AppRoomRepository

class AppRoomUseCase(private val appRoomRepository: AppRoomRepository) {

    suspend operator fun invoke(
        id: String,
        productName: String,
        productPrice: Int,
        image: String,
        store: String,
        productRating: Float,
        sale: Int,
        stock: Int,
        variantName: String,
        quantity: Int
    ) = appRoomRepository.saveWishlist(
        id,
        productName,
        productPrice,
        image,
        store,
        productRating,
        sale,
        stock,
        variantName,
        quantity
    )

    fun getwishlist() = appRoomRepository.getWishList()
    fun getIsFavorite(productId: String) = appRoomRepository.getIsFavorite(productId)

    fun deleteWishlist(id: String) = appRoomRepository.deleteWishList(id)

    suspend fun addCartList(
        id: String,
        productName: String,
        variantName: String,
        stock: Int,
        productPrice: Int,
        quantity: Int,
        image: String,
        isChecked: Boolean,
    ) = appRoomRepository.addCartList(
        id,
        productName,
        variantName,
        stock,
        productPrice,
        quantity,
        image,
        isChecked
    )

    fun getCartList(): LiveData<List<CartKeys>> = appRoomRepository.getCartList()


    fun deleteAllCart() = appRoomRepository.deleteAllCart()
    fun deleteCartById(id: String) = appRoomRepository.deleteProduct(id)

    fun getChartById(productId: String) = appRoomRepository.getChartById(productId)

    fun quantityCart(id: String, quantity: Int) = appRoomRepository.quantityCart(id, quantity)

    fun isChecked(id: String, isChecked: Boolean) = appRoomRepository.isChecked(id, isChecked)
    fun isCheckedStatus(id: String): Boolean? = appRoomRepository.isCheckedStatus(id)

    suspend fun updateBooleanColumnAll(newValue: Boolean) =
        appRoomRepository.updateBooleanColumnAll(newValue)

    suspend fun createNotification(
        notifId: Int,
        notifType: String,
        notifTitle: String,
        notifBody: String,
        notifDate: String,
        notifTime: String,
        notifImage: String,
        isChecked: Boolean
    ) = appRoomRepository.createNotification(
        notifId,
        notifType,
        notifTitle,
        notifBody,
        notifDate,
        notifTime,
        notifImage,
        isChecked
    )

    fun getNotifications(): LiveData<List<NotificationsKeys>?> =
        appRoomRepository.getNotifications()

    fun notifIsChecked(id: Int, isChecked: Boolean) =
        appRoomRepository.notifIsChecked(id, isChecked)
}