package app.boletinhos.summary

import android.os.Parcelable
import app.boletinhos.domain.summary.Summary
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SummaryViewState(
    val isLoading: Boolean = false,
    val summary: List<SummaryItemCardView.Model>? = null
)  : Parcelable
