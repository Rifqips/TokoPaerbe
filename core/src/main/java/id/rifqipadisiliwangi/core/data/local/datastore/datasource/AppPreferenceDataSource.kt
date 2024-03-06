package id.rifqipadisiliwangi.core.data.local.datastore.datasource

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import id.rifqipadisiliwangi.core.utils.PreferenceDataStoreHelper
import kotlinx.coroutines.flow.Flow

interface AppPreferenceDataSource {
    suspend fun getUserToken(): String
    suspend fun saveUserToken(token: String)
    suspend fun getKeySearch(): String
    suspend fun saveKeySearch(search: String)
    suspend fun removeKeySearch()
    suspend fun getRefreshToken(): String
    suspend fun saveRefreshToken(token: String)
    fun getStateOnboarding(): Flow<Boolean>
    suspend fun saveStateOnboarding(isActive: Boolean)
    fun getStateDarkmode(): Flow<Boolean>
    suspend fun saveStateDarkmode(isActive: Boolean)
    fun getStateLocale(): Flow<Boolean>
    suspend fun saveStateLocale(isActive: Boolean)
    suspend fun saveFirebaseToken(firebaseToken: String)
    suspend fun getFirebaseToken(): String
    suspend fun saveUsername(username: String)
    suspend fun getUsername(): String
    suspend fun saveUserId(id: String)
    suspend fun getUserId(): String
    suspend fun deleteData()
    fun getAppLayoutFlowWishList(): Flow<Boolean>
    suspend fun setAppLayoutPrefWishList(isGridLayout: Boolean)
    fun getAppLayoutFlowStore(): Flow<Boolean>
    suspend fun setAppLayoutPrefStore(isGridLayout: Boolean)
    suspend fun setAppCheckedPrefCart(isChecked: Boolean)
    fun getAppCheckedPrefCart() :Flow<Boolean>
    suspend fun saveCountNumberCart(countNumber: String)
    suspend fun getCountNumberCart() : String
    suspend fun removeAppChekcedData()
}

class AppPreferenceDataSourceImpl(
    private val preferenceHelper: PreferenceDataStoreHelper
) : AppPreferenceDataSource {

    override suspend fun getUserToken(): String {
        return preferenceHelper.getFirstPreference(USER_TOKEN_KEY, "")
    }
    override suspend fun saveUserToken(token: String) {
        return preferenceHelper.putPreference(USER_TOKEN_KEY, token)
    }

    override suspend fun getKeySearch(): String {
        return preferenceHelper.getFirstPreference(USER_SEARCH_KEY, "")
    }

    override suspend fun saveKeySearch(search: String) {
        return preferenceHelper.putPreference(USER_SEARCH_KEY, search)
    }

    override suspend fun removeKeySearch() {
        preferenceHelper.removePreference(USER_SEARCH_KEY)
    }

    override suspend fun getRefreshToken(): String {
        return preferenceHelper.getFirstPreference(USER_TOKEN_KEY, "")
    }
    override suspend fun saveRefreshToken(token: String) {
        return preferenceHelper.putPreference(USER_TOKEN_KEY, token)
    }
    override fun getStateOnboarding(): Flow<Boolean> {
        return preferenceHelper.getPreference(STATE_ONBOARDING, false)
    }
    override suspend fun saveStateOnboarding(isActive : Boolean) {
        return preferenceHelper.putPreference(STATE_ONBOARDING, isActive)
    }

    override fun getStateDarkmode(): Flow<Boolean> {
        return preferenceHelper.getPreference(STATE_DARK_MODE, false)
    }

    override suspend fun saveStateDarkmode(isActive: Boolean) {
        return preferenceHelper.putPreference(STATE_DARK_MODE, isActive)
    }

    override fun getStateLocale(): Flow<Boolean> {
        return preferenceHelper.getPreference(STATE_LOCALE, true)
    }

    override suspend fun saveStateLocale(isActive: Boolean) {
        return preferenceHelper.putPreference(STATE_LOCALE, isActive)
    }

    override suspend fun saveFirebaseToken(firebaseToken: String) {
        return preferenceHelper.putPreference(USER_FIREBASE_TOKEN_KEY, firebaseToken)
    }
    override suspend fun getFirebaseToken(): String {
        return preferenceHelper.getFirstPreference(USER_FIREBASE_TOKEN_KEY, "")
    }

    override suspend fun saveUsername(username: String) {
        return preferenceHelper.putPreference(USERNAME_KEY, username)
    }

    override suspend fun getUsername(): String {
        return preferenceHelper.getFirstPreference(USERNAME_KEY, "")
    }

    override suspend fun saveUserId(id: String) {
        return preferenceHelper.putPreference(USER_ID_KEY, id)
    }
    override suspend fun getUserId(): String {
        return preferenceHelper.getFirstPreference(USER_ID_KEY, "")
    }

    override suspend fun deleteData() {
        preferenceHelper.removePreference(USER_TOKEN_KEY)
    }


    override suspend fun setAppLayoutPrefWishList(isGridLayout: Boolean) {
        return preferenceHelper.putPreference(PREF_GRID_LAYOUT_WISHLIST, isGridLayout)
    }

    override fun getAppLayoutFlowWishList(): Flow<Boolean> {
        return preferenceHelper.getPreference(PREF_GRID_LAYOUT_WISHLIST, false)
    }

    override fun getAppLayoutFlowStore(): Flow<Boolean> {
        return preferenceHelper.getPreference(PREF_GRID_LAYOUT_STORE, false)
    }

    override suspend fun setAppLayoutPrefStore(isGridLayout: Boolean) {
        return preferenceHelper.putPreference(PREF_GRID_LAYOUT_STORE, isGridLayout)
    }

    override suspend fun setAppCheckedPrefCart(isChecked: Boolean) {
        return preferenceHelper.putPreference(PREF_CHEKCED_CART, isChecked)
    }

    override fun getAppCheckedPrefCart(): Flow<Boolean> {
        return preferenceHelper.getPreference(PREF_CHEKCED_CART, false)
    }

    override suspend fun saveCountNumberCart(countNumber: String) {
        return preferenceHelper.putPreference(PREF_COUNT_NUMBER_CART, countNumber)
    }

    override suspend fun getCountNumberCart(): String {
        return preferenceHelper.getFirstPreference(PREF_COUNT_NUMBER_CART, "")
    }


    override suspend fun removeAppChekcedData() {
        preferenceHelper.removePreference(PREF_CHEKCED_CART)
    }


    companion object {
        val USER_ID_KEY = stringPreferencesKey("USER_ID_KEY")
        val USERNAME_KEY = stringPreferencesKey("USER_NAME_KEY")
        val USER_TOKEN_KEY = stringPreferencesKey("USER_TOKEN_KEY")
        val USER_SEARCH_KEY = stringPreferencesKey("USER_SEARCH_KEY")
        val REFRESH_TOKEN_KEY = stringPreferencesKey("REFRESH_TOKEN_KEY")
        val USER_FIREBASE_TOKEN_KEY = stringPreferencesKey("USER_FIREBASE_TOKEN_KEY")
        val STATE_ONBOARDING = booleanPreferencesKey("STATE_ONBOARDING")
        val STATE_DARK_MODE = booleanPreferencesKey("STATE_DARK_MODE")
        val STATE_LOCALE = booleanPreferencesKey("STATE_LOCALE")
        val PREF_GRID_LAYOUT_WISHLIST = booleanPreferencesKey("PREF_GRID_LAYOUT_WISHLIST")
        val PREF_GRID_LAYOUT_STORE = booleanPreferencesKey("PREF_GRID_LAYOUT_STORE")
        val PREF_CHEKCED_CART = booleanPreferencesKey("PREF_CHEKCED_CART")
        val PREF_PRICE_CART = stringPreferencesKey("PREF_PRICE_CART")
        val PREF_COUNT_NUMBER_CART = stringPreferencesKey("PREF_COUNT_NUMBER_CART")
    }
}