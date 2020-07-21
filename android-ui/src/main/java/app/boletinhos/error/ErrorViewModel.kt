package app.boletinhos.error

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ErrorViewModel(
    @StringRes val titleRes: Int,
    @StringRes val messageRes: Int
) : Parcelable
