package id.rifqipadisiliwangi.core.domain.repository

import androidx.lifecycle.LiveData
import id.rifqipadisiliwangi.core.data.local.database.cart.CartDao
import id.rifqipadisiliwangi.core.data.local.database.cart.CartKeys
import id.rifqipadisiliwangi.core.data.local.database.notification.NotificationDao
import id.rifqipadisiliwangi.core.data.local.database.notification.NotificationsKeys
import id.rifqipadisiliwangi.core.data.local.database.wishlist.WishlistDao
import id.rifqipadisiliwangi.core.data.local.database.wishlist.WishlistKeys
import kotlinx.coroutines.flow.Flow

interface AppRoomRepository {

    suspend fun saveWishlist(
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
    )
    fun getWishList(): Flow<List<WishlistKeys>>
    fun getIsFavorite(productId : String): Flow<List<WishlistKeys>>

    fun deleteWishList(id: String)

    fun deleteAllWishlist()

    suspend fun addCartList(
        id: String,
        productName: String,
        variantName: String,
        stock: Int,
        productPrice: Int,
        quantity: Int,
        image: String,
        isChecked: Boolean,
    )

    fun getCartList(): LiveData<List<CartKeys>>
    fun deleteAllCart()
    fun deleteProduct(id: String)
    fun getChartById(productId : String): Flow<List<CartKeys>>

    fun quantityCart(id: String, quantity: Int)
    fun isChecked(id: String, isChecked: Boolean)
    fun checkAll(isChecked: Boolean)

    suspend fun updateBooleanColumnAll(newValue: Boolean)
    fun isCheckedStatus(id: String): Boolean?


    suspend fun createNotification(
        notifId: Int,
        notifType: String,
        notifTitle: String,
        notifBody: String,
        notifDate: String,
        notifTime: String,
        notifImage: String,
        isChecked: Boolean
    )

    fun getNotifications(): LiveData<List<NotificationsKeys>?>

    fun notifIsChecked(id: Int, isChecked: Boolean)

}

class AppRoomRepositoryImpl(
    private val wishlistDao: WishlistDao,
    private val cartlistDao: CartDao,
    private val notification : NotificationDao
) : AppRoomRepository{
    override suspend fun saveWishlist(
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
    ) {
        return wishlistDao.addWishList(id, productName, productPrice, image, store, productRating, sale, stock, variantName, quantity)
    }

    override fun getWishList():  Flow<List<WishlistKeys>>{
        return wishlistDao.getWishList()
    }

    override fun getIsFavorite(productId: String): Flow<List<WishlistKeys>> {
        return wishlistDao.getIsFavorite(productId)
    }

    override fun deleteWishList(id: String) {
        return wishlistDao.deleteWishList(id)
    }

    override fun deleteAllWishlist() {
        return wishlistDao.deleteAllWishlist()
    }

    override fun deleteAllCart() {
        return cartlistDao.deleteAllCart()
    }

    override fun deleteProduct(id: String) {
        return cartlistDao.deleteProduct(id)
    }

    override fun getChartById(productId: String): Flow<List<CartKeys>> {
        return cartlistDao.getCartById(productId)
    }

    override fun quantityCart(id: String, quantity: Int) {
        return cartlistDao.quantityCart(id, quantity)
    }

    override fun isChecked(id: String, isChecked: Boolean) {
        return cartlistDao.isChecked(id, isChecked)
    }

    override fun checkAll(isChecked: Boolean) {
        return cartlistDao.checkAll(isChecked)
    }

    override suspend fun updateBooleanColumnAll(newValue: Boolean) {
        return cartlistDao.updateBooleanColumnAll(newValue)
    }

    override fun isCheckedStatus(id: String): Boolean? {
        return cartlistDao.isCheckedStatus(id)
    }

    override suspend fun addCartList(
        id: String,
        productName: String,
        variantName: String,
        stock: Int,
        productPrice: Int,
        quantity: Int,
        image: String,
        isChecked: Boolean
    ) {
        return cartlistDao.addCartList(id, productName, variantName, stock, productPrice, quantity, image, isChecked)
    }

    override fun getCartList(): LiveData<List<CartKeys>> {
        return cartlistDao.getCartList()
    }
    override suspend fun createNotification(
        notifId: Int,
        notifType: String,
        notifTitle: String,
        notifBody: String,
        notifDate: String,
        notifTime: String,
        notifImage: String,
        isChecked: Boolean
    ) {
        return notification.createNotification(notifId, notifType, notifTitle, notifBody, notifDate, notifTime, notifImage, isChecked)
    }

    override fun getNotifications(): LiveData<List<NotificationsKeys>?> {
        return notification.getNotifications()
    }

    override fun notifIsChecked(id: Int, isChecked: Boolean) {
        return notification.notifIsChecked(id, isChecked)
    }
}