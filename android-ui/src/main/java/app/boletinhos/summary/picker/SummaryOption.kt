package app.boletinhos.summary.picker

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SummaryOption(
    val id: Long,
    val monthName: String,
    val year: Int,
    val isSelected: Boolean = false
): Parcelable {
    override fun toString(): String = "$monthName\n$year"
}
