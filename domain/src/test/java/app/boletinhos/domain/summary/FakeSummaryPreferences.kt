package app.boletinhos.domain.summary

class FakeSummaryPreferences : SummaryPreferences {
    private var summaryId: Long? = null

    override fun actualSummaryId(): Long {
        return summaryId ?: SummaryPreferences.NO_SUMMARY
    }

    override fun actualSummary(id: Long) {
        summaryId = id
    }
}
