plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.norwayapplicationxml"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.norwayapplicationxml"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    //hilt
    implementation("com.google.dagger:hilt-android:2.50")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    kapt("com.google.dagger:hilt-compiler:2.50")
    //data-store
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    //image glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    //jsoup
    implementation("org.jsoup:jsoup:1.17.2")
    //splash screen
    implementation("androidx.core:core-splashscreen:1.0.1")
    //paging3
    val paging_version = "3.2.1"
    implementation("androidx.paging:paging-runtime:$paging_version")
    //facebook shimmer effect
    implementation("com.facebook.shimmer:shimmer:0.5.0")
    //youtube player
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")

    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

kapt{
    correctErrorTypes = true
}