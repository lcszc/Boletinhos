package app.boletinhos.domain.summary

interface SummaryPreferences {
    fun actualSummaryId(): Long
    fun actualSummary(id: Long)

    companion object {
        const val NO_SUMMARY: Long = -1
        const val ACTUAL_SUMMARY_ID = "actual_summary_id"
    }
}
