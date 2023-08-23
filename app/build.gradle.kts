import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    // android
    id("com.android.application")
    // kotlin
    id("org.jetbrains.kotlin.android")
    // apollo
    id("com.apollographql.apollo3")
}

android {
    namespace = "com.simply.birthdayapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.simply.birthdayapp"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "SERVER_URL", gradleLocalProperties(rootDir).getProperty("SERVER_URL", "\"\""))
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

apollo {
    service("service") {
        packageName.set("com.simply.birthdayapp")
        mapScalarToKotlinString("Date")
    }
}

dependencies {
    // core
    implementation("androidx.core:core-ktx:1.10.1")
    // activity
    implementation("androidx.activity:activity-compose:1.7.2")
    // compose
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    // apollo
    implementation("com.apollographql.apollo3:apollo-adapters:3.8.2")
    implementation("com.apollographql.apollo3:apollo-runtime:3.8.2")
    // coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
    // coil
    implementation("io.coil-kt:coil-compose:2.4.0")
    // koin
    implementation("io.insert-koin:koin-android:3.4.3")
    implementation("io.insert-koin:koin-androidx-compose:3.4.6")
    // navigation
    implementation("androidx.navigation:navigation-compose:2.7.0")
    // lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.1")
}