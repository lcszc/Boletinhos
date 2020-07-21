package app.boletinhos.summary

import android.os.Parcelable
import app.boletinhos.error.ErrorViewModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SummaryViewState(
    val isLoading: Boolean = false,
    val isActionAndSummaryVisible: Boolean = false,
    val summary: List<SummaryItemCardView.Model>? = null,
    val error: ErrorViewModel? = null
)  : Parcelable
