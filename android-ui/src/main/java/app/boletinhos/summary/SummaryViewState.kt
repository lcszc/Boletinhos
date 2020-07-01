package app.boletinhos.summary

import app.boletinhos.domain.summary.Summary

data class SummaryViewState(
    val isLoading: Boolean = false,
    val summary: Summary? = null
)
