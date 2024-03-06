package id.rifqipadisiliwangi.core.data.local.database.wishlist

import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@androidx.room.Dao
interface WishlistDao {

    @Query(
        "INSERT OR REPLACE INTO wishlist_key (productId, " +
                "productName," +
                "productPrice, " +
                "image, " +
                "store, " +
                "productRating, " +
                "sale, " +
                "stock, " +
                "variantName, " +
                "quantity) values (:id, :productName, :productPrice, :image, :store, :productRating, :sale, :stock, :variantName, :quantity)"
    )
    fun addWishList(
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

    @Query("DELETE FROM wishlist_key WHERE productId = :id")
    fun deleteWishList(id: String)

    @Query("DELETE FROM wishlist_key")
    fun deleteAllWishlist()

    @Query("SELECT * FROM wishlist_key")
    fun getWishList(): Flow<List<WishlistKeys>>

    @Query("SELECT * FROM wishlist_key WHERE productId = :id")
    fun getIsFavorite(id: String): Flow<List<WishlistKeys>>

    @Query("SELECT * FROM wishlist_key WHERE productId = :id")
    suspend fun getWishlistForDetail(id: String): WishlistKeys?
}