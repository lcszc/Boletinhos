package app.boletinhos.domain.summary

interface SummaryPreferences {
    fun actualSummaryId(): Long
    fun actualSummary(id: Long)

    companion object {
        const val NO_SUMMARY: Long = -1
    }
}