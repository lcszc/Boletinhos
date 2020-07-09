package config

object Androidx {
    const val core = "androidx.core:core-ktx:1.2.0"
    const val appCompat = "androidx.appcompat:appcompat:1.1.0"

    object Room {
        const val core = "androidx.room:room-ktx:2.2.5"
        const val compiler = "androidx.room:room-compiler:2.2.5"
        const val testing = "androidx.room:room-testing:2.2.5"
    }

    object Testing {
        const val core = "androidx.test:core:1.0.0"
        const val coreKtx = "androidx.test:core-ktx:1.1.0"
        const val rules = "androidx.test:rules:1.2.0"
        const val runner = "androidx.test:runner:1.1.0"
        const val orchestrator = "androidx.test:orchestrator:1.1.0"
        const val junit = "androidx.test.ext:junit:1.1.1"
    }
}

object UI {
    const val material = "com.google.android.material:material:1.2.0-beta01"
}

object Kotlinx {
    const val stbLib = "org.jetbrains.kotlin:kotlin-stdlib:1.3.70"

    object Coroutines {
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.7"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.7"
    }
}

object Testing {
    const val junit = "junit:junit:4.12"
    const val assertK = "com.willowtreeapps.assertk:assertk-jvm:0.22"
    const val robolectric = "org.robolectric:robolectric:4.3"
    const val mockK = "io.mockk:mockk:1.10.0"
}

object DI {
    const val dagger = "com.google.dagger:dagger:2.27"
    const val compiler = "com.google.dagger:dagger-compiler:2.27"
    const val javax = "javax.inject:javax.inject:1"
}

object Tools {
    const val desugaring = "com.android.tools:desugar_jdk_libs:1.0.5"
}

object GoogleServices {
    const val gradle = "com.google.gms:google-services:4.3.3"
}

object Firebase {
    object Crashlytics {
        const val gradle = "com.google.firebase:firebase-crashlytics-gradle:2.1.1"
        const val core = "com.google.firebase:firebase-crashlytics:17.0.0"
    }
}

object Espresso {
    const val core = "androidx.test.espresso:espresso-core:3.2.0"
}

object Jacoco {
    const val version = "0.8.4"
    const val core = "org.jacoco:org.jacoco.core:$version"
}

object LeakCanary {
    const val android = "com.squareup.leakcanary:leakcanary-android:2.4"
}

object SimpleStack {
    const val core = "com.github.Zhuinden:simple-stack:2.4.0"
    const val services = "com.github.Zhuinden.simple-stack-extensions:services:2.0.0"
    const val servicesKtx = "com.github.Zhuinden.simple-stack-extensions:services-ktx:2.0.0"
    const val navigator = "com.github.Zhuinden.simple-stack-extensions:navigator-ktx:2.0.0"
}