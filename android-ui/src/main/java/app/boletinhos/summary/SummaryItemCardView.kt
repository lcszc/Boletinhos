package app.boletinhos.summary

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import app.boletinhos.R.layout as Layouts
import app.boletinhos.R.style as Styles
import app.boletinhos.databinding.SummaryItemViewBinding
import app.boletinhos.theming.createThemeAwareDrawable
import com.google.android.material.card.MaterialCardView
import kotlinx.android.parcel.Parcelize

class SummaryItemCardView(
    context: Context,
    attrs: AttributeSet? = null
): MaterialCardView(context, attrs) {
    private val binding = SummaryItemViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun bind(model: Model) = binding.run {
        val icon = context.createThemeAwareDrawable(model.iconRes)

        textItemTitle.apply {
            model.titleArg?.let { arg ->
                val titleText = resources.getString(model.titleRes, arg)
                text = titleText
            } ?: setText(model.titleRes)

            setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)
        }

        textItemDescription.setText(model.descriptionRes)
        textItemValue.text = model.textValue
    }

    @Parcelize
    data class Model(
        @DrawableRes val iconRes: Int,
        @StringRes val titleRes: Int,
        @StringRes val descriptionRes: Int,
        val titleArg: String? = null,
        val textValue: String,
        val kind: Kind
    ) : Parcelable {
        enum class Kind(val viewType: Int, @StyleRes val themeRes: Int) {
            MONTH_SUMMARY(Layouts.summary_item_view + 1, Styles.App) {
                override val isFullSpan: Boolean = true
            },

            UNPAIDS(Layouts.summary_item_view + 2, Styles.App_Bills_Unpaid),

            PAIDS(Layouts.summary_item_view + 3, Styles.App_Bills_Paid),

            OVERDUE(Layouts.summary_item_view + 4, Styles.App_Bills_Overdue);

            open val isFullSpan: Boolean = false
        }
    }
}
