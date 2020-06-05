package app.boletinhos.crashcat

import app.boletinhos.preferences.UserPreferences
import com.google.firebase.crashlytics.FirebaseCrashlytics
import javax.inject.Inject

/*
 * CrashCat is utility class to configure crashlytics
 * crashs collection based on user preferences.
 *
 * 'CrashCat' class name is based on `DataDog`. If they're a dog, we're a cat.
 * This class doesn't have much responsibilities, but in the future it can be a wrap
 * over Crashlytics and any other tool for analytics.
 */
class CrashCat @Inject constructor(
    private val userPreferences: UserPreferences,
    private val crashlytics: FirebaseCrashlytics
) {
    fun configure() {
        crashlytics.setCrashlyticsCollectionEnabled(userPreferences.isCrashReportEnabled)
    }
}