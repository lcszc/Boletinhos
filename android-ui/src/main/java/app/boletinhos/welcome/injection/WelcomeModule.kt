package app.boletinhos.welcome.injection

import app.boletinhos.bill.add.AddBillViewModel
import app.boletinhos.welcome.WelcomeViewModel
import dagger.Binds
import dagger.Module

@Module
abstract class WelcomeModule {
    @Binds abstract fun provideOnBillCreatedListener(
        impl: WelcomeViewModel
    ): AddBillViewModel.OnBillCreatedListener
}
