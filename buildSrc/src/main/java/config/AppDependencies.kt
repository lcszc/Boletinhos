package config

object Androidx {
    const val core = "androidx.core:core-ktx:1.2.0"
    const val appCompat = "androidx.appcompat:appcompat:1.1.0"

    object Room {
        const val core = "androidx.room:room-ktx:2.2.5"
        const val compiler = "androidx.room:room-compiler:2.2.5"
        const val testing = "androidx.room:room-testing:2.2.5"
    }

    const val test = "androidx.test:core:1.0.0"
}

object Kotlinx {
    const val stbLib = "org.jetbrains.kotlin:kotlin-stdlib:1.3.70"

    object Coroutines {
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.5"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.5"
    }
}

object Testing {
    const val junit = "junit:junit:4.12"
    const val assertK = "com.willowtreeapps.assertk:assertk-jvm:0.22"
    const val robolectric = "org.robolectric:robolectric:4.3"
}

object ThreeTen {
    const val abp = "com.jakewharton.threetenabp:threetenabp:1.2.4"
}