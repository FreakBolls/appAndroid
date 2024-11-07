plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.proyectoandroid"
    compileSdk = 34

    buildFeatures{
        viewBinding=true
    }

    defaultConfig {
        applicationId = "com.example.proyectoandroid"
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(platform("com.google.firebase:firebase-bom:33.1.1"))

    implementation("com.google.firebase:firebase-firestore:17.1.2")
    implementation("com.google.firebase:firebase-auth:21.0.1")
    implementation("com.google.firebase:firebase-analytics:20.0.3")
    implementation("com.google.firebase:firebase-storage-ktx:20.0.0")
    implementation("com.google.firebase:firebase-database-ktx:20.0.2")
    implementation("com.google.firebase:firebase-auth")
    //Coroutines - Firebase
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.4.1")

    implementation("com.squareup.retrofit2:retrofit:2.5.0")
    implementation("com.squareup.retrofit2:converter-gson:2.5.0")
    implementation("com.github.bumptech.glide:glide:4.11.0")


}