package id.rifqipadisiliwangi.core.domain.repository

import id.rifqipadisiliwangi.core.data.local.datastore.datasource.AppPreferenceDataSource
import id.rifqipadisiliwangi.core.data.network.api.datasource.StoreDataSourceImpl
import id.rifqipadisiliwangi.core.data.network.api.model.fulfillment.RequestFullfilment
import id.rifqipadisiliwangi.core.data.network.api.model.fulfillment.toResponseFulfillment
import id.rifqipadisiliwangi.core.data.network.api.model.rating.RequestRating
import id.rifqipadisiliwangi.core.domain.model.detail.StoreDetailData
import id.rifqipadisiliwangi.core.domain.model.fulfillment.FulfillmentRequest
import id.rifqipadisiliwangi.core.domain.model.fulfillment.FulfillmentResponse
import id.rifqipadisiliwangi.core.domain.model.history.DataHistory
import id.rifqipadisiliwangi.core.domain.model.rating.RatingRequest
import id.rifqipadisiliwangi.core.domain.model.rating.RatingResponse
import id.rifqipadisiliwangi.core.domain.model.review.DataResponseReviewItemDataResponse
import id.rifqipadisiliwangi.core.domain.model.search.SearchResponse
import id.rifqipadisiliwangi.core.domain.model.store.ResponseStoreItem
import id.rifqipadisiliwangi.core.utils.DataMapper.toDetailStoreMapping
import id.rifqipadisiliwangi.core.utils.DataMapper.toHistoryList
import id.rifqipadisiliwangi.core.utils.DataMapper.toRatingMapper
import id.rifqipadisiliwangi.core.utils.DataMapper.toResponseStore
import id.rifqipadisiliwangi.core.utils.DataMapper.toReviewList
import id.rifqipadisiliwangi.core.utils.DataMapper.toSearchResponse
import id.rifqipadisiliwangi.core.utils.ResultWrapper
import id.rifqipadisiliwangi.core.utils.proceedFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart

interface StoreRepository {

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

class StoreRepositoryImpl(
    private val apiDataSource : StoreDataSourceImpl,
    private val appPreferenceDataSource: AppPreferenceDataSource

) : StoreRepository {
    override suspend fun productStore(
        searchItem: String?,
        brandItem: String?,
        lowestItem: Int?,
        highestItem: Int?,
        sortItem: String?,
        limitItem: Int?,
        pageItem: Int?
    ): ResponseStoreItem {
        return apiDataSource.productStore(searchItem, brandItem, lowestItem, highestItem, sortItem, limitItem, pageItem).toResponseStore()
    }

    override suspend fun postFullFillment(body: FulfillmentRequest): Flow<ResultWrapper<FulfillmentResponse>> {
        return proceedFlow {
            val dataReq = RequestFullfilment(body.payment, body.items)
            val fulfillmentResult = apiDataSource.postFullFillment(dataReq)
            fulfillmentResult.toResponseFulfillment()
        }.catch {
            emit(ResultWrapper.Error(Exception(it)))
        }.onStart {
            emit(ResultWrapper.Loading())
        }
    }

    override suspend fun detailStore(productId: String): Flow<ResultWrapper<StoreDetailData>> {
        return proceedFlow {
            apiDataSource.detailStore(idProduct = productId).data.toDetailStoreMapping()
        }.catch {
            emit(ResultWrapper.Error(Exception(it)))
        }.onStart {
            emit(ResultWrapper.Loading())
        }
    }

    override suspend fun searchSuggestion(query: String): Flow<ResultWrapper<SearchResponse>> {
        return proceedFlow {
            apiDataSource.searchSuggestion(query = query).toSearchResponse()
        }.catch {
            emit(ResultWrapper.Error(Exception(it)))
        }.onStart {
            emit(ResultWrapper.Loading())
        }
    }

    override suspend fun reviewProduct(productId: String): Flow<ResultWrapper<List<DataResponseReviewItemDataResponse>>> {
        return proceedFlow {
            apiDataSource.reviewProduct(idProduct = productId).data.toReviewList()
        }.catch {
            emit(ResultWrapper.Error(Exception(it)))
        }.onStart {
            emit(ResultWrapper.Loading())
        }
    }

    override suspend fun getHistory(): Flow<ResultWrapper<List<DataHistory>>> {
        return proceedFlow {
            apiDataSource.getHistory().data.toHistoryList()
        }.catch {
            emit(ResultWrapper.Error(Exception(it)))
        }.onStart {
            emit(ResultWrapper.Loading())
        }
    }

    override suspend fun setAppCheckedPrefCart(isChecked: Boolean) {
        return appPreferenceDataSource.setAppCheckedPrefCart(isChecked)
    }

    override fun getAppCheckedPrefCart(): Flow<Boolean> {
        return appPreferenceDataSource.getAppCheckedPrefCart()
    }

    override suspend fun saveCountNumberCart(countNumber: String) {
        return appPreferenceDataSource.saveCountNumberCart(countNumber)
    }

    override suspend fun getCountNumberCart(): String {
        return appPreferenceDataSource.getCountNumberCart()
    }

    override suspend fun removeAppChekcedData() {
        return appPreferenceDataSource.removeAppChekcedData()
    }

    override suspend fun getKeySearch(): String {
        return appPreferenceDataSource.getKeySearch()
    }

    override suspend fun saveKeySearch(search: String) {
        return appPreferenceDataSource.saveKeySearch(search)
    }

    override suspend fun removeKeySearch() {
        return appPreferenceDataSource.removeKeySearch()
    }

    override fun getAppLayoutFlowStore(): Flow<Boolean> {
        return appPreferenceDataSource.getAppLayoutFlowStore()
    }


    override suspend fun setAppLayoutPrefStore(isGridLayout: Boolean) {
        return appPreferenceDataSource.setAppLayoutPrefStore(isGridLayout)
    }

    override suspend fun userRating(userRating: RatingRequest): Flow<ResultWrapper<RatingResponse>> {
        return proceedFlow {
            val dataReq = RequestRating(userRating.invoiceId, userRating.rating, userRating.review)
            apiDataSource.userRating(dataReq).toRatingMapper()
        }.catch {
            emit(ResultWrapper.Error(Exception(it)))
        }.catch {
            emit((ResultWrapper.Loading()))
        }
    }
}