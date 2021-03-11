package app.boletinhos.domain.summary

class FakeSummaryPreferences : SummaryPreferences {
    private var summaryId: Long? = null

    override fun summaryId(): Long? {
        return summaryId
    }

    override fun summaryId(id: Long) {
        summaryId = id
    }
}
