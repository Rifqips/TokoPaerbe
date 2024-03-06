package id.rifqipadisiliwangi.core.data.network.api.datasource

import id.rifqipadisiliwangi.core.data.network.api.model.detail.ResponseDetailStore
import id.rifqipadisiliwangi.core.data.network.api.model.fulfillment.RequestFullfilment
import id.rifqipadisiliwangi.core.data.network.api.model.fulfillment.ResponseFulfillment
import id.rifqipadisiliwangi.core.data.network.api.model.history.ResponseHistory
import id.rifqipadisiliwangi.core.data.network.api.model.rating.RequestRating
import id.rifqipadisiliwangi.core.data.network.api.model.rating.ResponseRating
import id.rifqipadisiliwangi.core.data.network.api.model.review.ResponseReviewItem
import id.rifqipadisiliwangi.core.data.network.api.model.search.ResponseSearch
import id.rifqipadisiliwangi.core.data.network.api.model.store.ResponseStore
import id.rifqipadisiliwangi.core.data.network.api.service.ApplicationService

interface StoreDataSource{
    suspend fun productStore(
        searchItem:String? = null,
        brandItem:String? = null,
        lowestItem:Int? = null,
        highestItem:Int? = null,
        sortItem:String? = null,
        limitItem:Int? = null,
        pageItem:Int? = null,
    ): ResponseStore
    suspend fun searchSuggestion(query:String? = null): ResponseSearch

    suspend fun detailStore(idProduct:String? = null): ResponseDetailStore
    suspend fun reviewProduct(idProduct:String? = null): ResponseReviewItem
    suspend fun postFullFillment(body : RequestFullfilment) : ResponseFulfillment
    suspend fun getHistory() : ResponseHistory

    suspend fun userRating(userRating: RequestRating): ResponseRating

}
class StoreDataSourceImpl(
    private val service : ApplicationService
) : StoreDataSource {
    override suspend fun productStore(
        searchItem: String?,
        brandItem: String?,
        lowestItem: Int?,
        highestItem: Int?,
        sortItem: String?,
        limitItem: Int?,
        pageItem: Int?
    ): ResponseStore {
        return service.productStore(
            searchItem = searchItem,
            brandItem = brandItem,
            lowestItem = lowestItem,
            highestItem = highestItem,
            sortItem = sortItem,
            limitItem = limitItem,
            pageItem = pageItem
        )
    }

    override suspend fun searchSuggestion(query: String?): ResponseSearch {
        return service.searchSuggestion(query = query)
    }

    override suspend fun detailStore(idProduct: String?): ResponseDetailStore {
        return service.fetchDetailStore(idProduct)
    }

    override suspend fun reviewProduct(idProduct: String?): ResponseReviewItem {
        return service.fetchReviewProduct(idProduct)
    }

    override suspend fun postFullFillment(body: RequestFullfilment): ResponseFulfillment {
        return service.postFullFillment(body)
    }

    override suspend fun getHistory(): ResponseHistory {
        return service.getHistory()
    }

    override suspend fun userRating(userRating: RequestRating): ResponseRating {
        return service.userRating(userRating)
    }


}