package id.rifqipadisiliwangi.core.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import id.rifqipadisiliwangi.core.data.local.database.cart.CartDao
import id.rifqipadisiliwangi.core.data.local.database.cart.CartKeys
import id.rifqipadisiliwangi.core.data.local.database.notification.NotificationDao
import id.rifqipadisiliwangi.core.data.local.database.notification.NotificationsKeys
import id.rifqipadisiliwangi.core.data.local.database.wishlist.WishlistDao
import id.rifqipadisiliwangi.core.data.local.database.wishlist.WishlistKeys
import id.rifqipadisiliwangi.core.utils.Constant.APP_DATABASE_NAME

@Database(
    entities = [CartKeys::class, WishlistKeys::class, NotificationsKeys::class],
    version = 2,
    exportSchema = false,
)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun wishlistDao(): WishlistDao
    abstract fun cartDao(): CartDao
    abstract fun notificationDao(): NotificationDao
    companion object {

        private var INSTANCE : ApplicationDatabase? = null

        val MIGRATION_1_2: Migration = object : Migration(1,2){
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE cart_keys RENAME TO cart_key")
                db.execSQL("ALTER TABLE wishlist_keys RENAME TO wishlist_key")

            }
        }


        fun getInstance(context: Context): ApplicationDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ApplicationDatabase::class.java,
                    APP_DATABASE_NAME
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}