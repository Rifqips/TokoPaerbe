package id.rifqipadisiliwangi.core.domain.model.transaction

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class TransactionDataClass(
    val invoiceId: String,
    val StatusValue: String,
    val tanggalValue: String,
    val waktuValue: String,
    val metodePembayaranValue: String,
    val totalPembayaranValue: Int
) : Parcelable

@Keep
@Parcelize
data class ItemTransaction(
    val itemTransaction: List<TransactionDataClass>
) : Parcelable
