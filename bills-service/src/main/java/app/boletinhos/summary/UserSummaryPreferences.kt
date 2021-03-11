package app.boletinhos.summary

import android.content.SharedPreferences
import androidx.core.content.edit
import app.boletinhos.domain.summary.SummaryPreferences
import app.boletinhos.domain.summary.SummaryPreferences.Companion.CURRENT_SUMMARY_ID
import javax.inject.Inject

@common.AppScope
internal class UserSummaryPreferences @Inject constructor(
    private val prefs: SharedPreferences
) : SummaryPreferences {
    override fun summaryId(): Long? {
        val id = prefs.getLong(CURRENT_SUMMARY_ID, NO_ID)
        return if (id == NO_ID) null else id
    }

    override fun summaryId(id: Long) {
        prefs.edit {
            putLong(CURRENT_SUMMARY_ID, id)
        }
    }

    private companion object {
        private const val NO_ID = -1L
    }
}
