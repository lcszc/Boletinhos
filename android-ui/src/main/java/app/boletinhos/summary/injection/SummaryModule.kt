package app.boletinhos.summary.injection

import app.boletinhos.summary.SummaryViewModel
import app.boletinhos.summary.picker.SummaryPickerViewModel.OnSummarySelectionChange
import dagger.Binds
import dagger.Module

@Module
abstract class SummaryModule {
    @Binds internal abstract fun provideOnSummarySelectionChange(
        impl: SummaryViewModel
    ): OnSummarySelectionChange
}
