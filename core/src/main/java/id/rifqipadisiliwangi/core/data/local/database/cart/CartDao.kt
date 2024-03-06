package id.rifqipadisiliwangi.core.data.local.database.cart

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("SELECT isChecked FROM cart_key WHERE productId = :id")
    fun isCheckedStatus(id: String): Boolean?

    @Query("UPDATE cart_key SET isChecked = :isChecked WHERE productId = :id ")
    fun isChecked(id: String, isChecked: Boolean)

    @Query("UPDATE cart_key SET isChecked = :newValue")
    suspend fun updateBooleanColumnAll(newValue: Boolean)

    @Query("UPDATE cart_key SET quantity = :quantity WHERE productId = :id ")
    fun quantityCart(id: String, quantity: Int)

    @Query("UPDATE cart_key SET isChecked = :isChecked")
    fun checkAll(isChecked: Boolean)

    @Query(
        "INSERT OR REPLACE INTO cart_key (productId, " +
                "productName," +
                " variantName, " +
                "stock, " +
                "productPrice, " +
                "quantity, " +
                "image, " +
                "isChecked) values (:id, :productName, :variantName, :stock, :productPrice, :quantity, :image, :isChecked)"
    )
    fun addCartList(
        id: String,
        productName: String,
        variantName: String,
        stock: Int,
        productPrice: Int,
        quantity: Int,
        image: String,
        isChecked: Boolean,
    )

    @Query("DELETE FROM cart_key WHERE productId = :id")
    fun deleteProduct(id: String)

    @Query("DELETE FROM cart_key")
    fun deleteAllCart()

    @Delete
    suspend fun deleteAllCheckedProduct(cartEntity: List<CartKeys>)

    @Query("SELECT * FROM cart_key")
    fun getCartList(): LiveData<List<CartKeys>>

    @Query("SELECT * FROM cart_key WHERE productId = :id")
    fun getCartById(id: String): Flow<List<CartKeys>>

}