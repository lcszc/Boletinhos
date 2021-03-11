package app.boletinhos.domain.summary

interface SummaryPreferences {
    fun summaryId(): Long?
    fun summaryId(id: Long)

    companion object {
        const val CURRENT_SUMMARY_ID = "summary_id"
    }
}
