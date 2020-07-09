package app.boletinhos.summary

import android.content.SharedPreferences
import androidx.core.content.edit
import app.boletinhos.domain.summary.SummaryPreferences
import common.AppScope
import javax.inject.Inject

@AppScope
internal class UserSummaryPreferences @Inject constructor(
    private val prefs: SharedPreferences
) : SummaryPreferences {
    override fun actualSummaryId(): Long {
        return prefs.getLong(ACTUAL_SUMMARY_ID, -1)
    }

    override fun actualSummary(id: Long) {
        prefs.edit {
            putLong(ACTUAL_SUMMARY_ID, id)
        }
    }

    companion object {
        const val ACTUAL_SUMMARY_ID = "actual_summary_id"
    }
}