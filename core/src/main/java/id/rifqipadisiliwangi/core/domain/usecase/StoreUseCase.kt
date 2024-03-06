package id.rifqipadisiliwangi.core.domain.usecase

import id.rifqipadisiliwangi.core.domain.model.detail.StoreDetailData
import id.rifqipadisiliwangi.core.domain.model.fulfillment.FulfillmentRequest
import id.rifqipadisiliwangi.core.domain.model.fulfillment.FulfillmentResponse
import id.rifqipadisiliwangi.core.domain.model.history.DataHistory
import id.rifqipadisiliwangi.core.domain.model.rating.RatingRequest
import id.rifqipadisiliwangi.core.domain.model.rating.RatingResponse
import id.rifqipadisiliwangi.core.domain.model.review.DataResponseReviewItemDataResponse
import id.rifqipadisiliwangi.core.domain.model.search.SearchResponse
import id.rifqipadisiliwangi.core.domain.model.store.ResponseStoreItem
import id.rifqipadisiliwangi.core.domain.repository.StoreRepository
import id.rifqipadisiliwangi.core.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow
interface StoreUseCase {
    suspend fun productStore(
        searchItem:String? = null,
        brandItem:String? = null,
        lowestItem:Int? = null,
        highestItem:Int? = null,
        sortItem:String? = null,
        limitItem:Int? = null,
        pageItem:Int? = null,
    ): ResponseStoreItem
    suspend fun postFullFillment(body : FulfillmentRequest) : Flow<ResultWrapper<FulfillmentResponse>>
    suspend fun detailStore(productId : String) : Flow<ResultWrapper<StoreDetailData>>
    suspend fun searchSuggestion(query : String) : Flow<ResultWrapper<SearchResponse>>
    suspend fun reviewProduct(productId : String) : Flow<ResultWrapper<List<DataResponseReviewItemDataResponse>>>

    suspend fun getHistory() : Flow<ResultWrapper<List<DataHistory>>>

    suspend fun setAppCheckedPrefCart(isChecked: Boolean)
     fun getAppCheckedPrefCart() :Flow<Boolean>
    suspend fun saveCountNumberCart(countNumber: String)
    suspend fun getCountNumberCart() : String
    suspend fun removeAppChekcedData()

    suspend fun getKeySearch(): String
    suspend fun saveKeySearch(search: String)
    suspend fun removeKeySearch()
    fun getAppLayoutFlowStore(): Flow<Boolean>
    suspend fun setAppLayoutPrefStore(isGridLayout: Boolean)

    suspend fun userRating(userRating: RatingRequest): Flow<ResultWrapper<RatingResponse>>

}

class StoreInteractor(private val storeRepository : StoreRepository) : StoreUseCase{
    override suspend fun productStore(
        searchItem: String?,
        brandItem: String?,
        lowestItem: Int?,
        highestItem: Int?,
        sortItem: String?,
        limitItem: Int?,
        pageItem: Int?
    ): ResponseStoreItem {
        return storeRepository.productStore(searchItem, brandItem, lowestItem, highestItem, sortItem,limitItem, pageItem)
    }

    override suspend fun postFullFillment(body: FulfillmentRequest): Flow<ResultWrapper<FulfillmentResponse>> {
        return storeRepository.postFullFillment(body)
    }

    override suspend fun detailStore(productId: String): Flow<ResultWrapper<StoreDetailData>> {
        return storeRepository.detailStore(productId)
    }

    override suspend fun searchSuggestion(query: String): Flow<ResultWrapper<SearchResponse>> {
        return storeRepository.searchSuggestion(query)
    }

    override suspend fun reviewProduct(productId: String): Flow<ResultWrapper<List<DataResponseReviewItemDataResponse>>> {
        return storeRepository.reviewProduct(productId)
    }

    override suspend fun getHistory(): Flow<ResultWrapper<List<DataHistory>>> {
        return storeRepository.getHistory()
    }

    override suspend fun setAppCheckedPrefCart(isChecked: Boolean) {
        return storeRepository.setAppCheckedPrefCart(isChecked)
    }

    override fun getAppCheckedPrefCart(): Flow<Boolean> {
        return storeRepository.getAppCheckedPrefCart()
    }

    override suspend fun saveCountNumberCart(countNumber: String) {
        return storeRepository.saveCountNumberCart(countNumber)
    }

    override suspend fun getCountNumberCart(): String {
        return storeRepository.getCountNumberCart()
    }

    override suspend fun removeAppChekcedData() {
        return storeRepository.removeAppChekcedData()
    }

    override suspend fun getKeySearch(): String {
        return storeRepository.getKeySearch()
    }

    override suspend fun saveKeySearch(search: String) {
        return storeRepository.saveKeySearch(search)
    }

    override suspend fun removeKeySearch() {
        return storeRepository.removeKeySearch()
    }

    override fun getAppLayoutFlowStore(): Flow<Boolean> {
        return storeRepository.getAppLayoutFlowStore()
    }
    override suspend fun setAppLayoutPrefStore(isGridLayout: Boolean) {
        return storeRepository.setAppLayoutPrefStore(isGridLayout)
    }

    override suspend fun userRating(userRating: RatingRequest): Flow<ResultWrapper<RatingResponse>> {
        return storeRepository.userRating(userRating)
    }
}