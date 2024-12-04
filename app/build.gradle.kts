plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
    kotlin("plugin.serialization") version "1.9.24"
}

android {
    namespace = "com.pardeep.yogify"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.pardeep.yogify"
        minSdk = 27
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
    buildFeatures {
        viewBinding = true
        dataBinding=true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    packagingOptions{

        exclude ("META-INF/DEPENDENCIES")
        exclude ("META-INF/LICENSE")
        exclude ("META-INF/LICENSE.txt")
        exclude ("META-INF/NOTICE")
        exclude ("META-INF/NOTICE.txt")

    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.firebase.database)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //adding dependency for image slider
    implementation("com.github.denzcoskun:ImageSlideshow:0.1.2")
    //adding animated navigaton bottom bar dependency
    implementation( "nl.joery.animatedbottombar:library:1.1.0")

    //dependency for lottie animation
    implementation("com.airbnb.android:lottie:6.5.2")

    // adding dependency of dot indicator
    implementation("com.tbuonomo:dotsindicator:5.0")

    // viewpager2 dependency
    implementation("androidx.viewpager2:viewpager2:1.1.0")

    //dependency for Carousal layout
    implementation ("com.google.android.material:material:1.9.0")
    implementation ("com.github.bumptech.glide:glide:4.16.0")

    //shimmer effect
    implementation("com.facebook.shimmer:shimmer:0.5.0")

    implementation("com.vanniktech:android-image-cropper:4.5.0")
    implementation ("com.github.bumptech.glide:glide:4.16.0")

    implementation(platform("io.github.jan-tennert.supabase:bom:3.0.2"))
//    implementation("io.github.jan-tennert.supabase:postgrest-kt")
//    implementation("io.github.jan-tennert.supabase:auth-kt")
    implementation ("io.github.jan-tennert.supabase:storage-kt:3.0.2")
//    implementation("io.github.jan-tennert.supabase:realtime-kt")
    implementation("io.ktor:ktor-client-apache5:3.0.1")



}