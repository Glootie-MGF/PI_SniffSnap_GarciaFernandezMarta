plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs")
}

android {
    namespace = "com.example.pi_sniffsnap_garciafernandezmarta"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.pi_sniffsnap_garciafernandezmarta"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        dataBinding = true
        compose = true
    }
    viewBinding{
        enable = true
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

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    // Retrofit y Moshi
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    // Coil para cargar imagenes de Internet
    implementation("io.coil-kt:coil:2.6.0")
    implementation("io.coil-kt:coil-compose:1.3.1")
    // Glide para cargar imagenes de internet en un imageView (en este caso storage)
    implementation("com.github.bumptech.glide:glide:4.16.0")
    // Para utilizar la galeria de imagenes
    implementation("androidx.activity:activity-ktx:1.9.0")
    // CameraX
    implementation ("androidx.camera:camera-camera2:1.2.0-rc01")
    implementation ("androidx.camera:camera-lifecycle:1.2.0-rc01")
    implementation ("androidx.camera:camera-view:1.2.0-rc01")
    // Para autenticación con google aparte del sdk
    implementation("com.google.android.gms:play-services-auth-base:18.0.10")
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.firebase:firebase-database:20.3.0")
    implementation("com.google.firebase:firebase-storage:20.3.0")
    implementation("androidx.activity:activity:1.9.0")
    // Para navegar entre fragments
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    // Machine Learning - TensorFlow Lite
    implementation("org.tensorflow:tensorflow-lite:2.4.0")
    implementation("org.tensorflow:tensorflow-lite-support:0.1.0")

    // Jetpack Compose
    implementation ("androidx.compose.ui:ui:1.6.7")
    // Tooling support (Previews, etc.)
    implementation ("androidx.compose.ui:ui-tooling:1.6.7")
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    implementation ("androidx.compose.foundation:foundation:1.6.7")
    // Material Design
    implementation ("androidx.compose.material:material:1.6.7")
    // Material design icons
    implementation ("androidx.compose.material:material-icons-core:1.6.7")
    implementation ("androidx.compose.material:material-icons-extended:1.6.7")
    // Integration with activities
    implementation ("androidx.activity:activity-compose:1.9.0")
    // Integration with ViewModels
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")
    implementation ("androidx.compose.ui:ui-tooling-preview")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.8.0")
    implementation ("androidx.navigation:navigation-compose:2.7.7")
    implementation(platform("androidx.compose:compose-bom:2024.05.00"))
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.material3:material3")
    // UI Tests
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.6.7")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.05.00"))
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    debugImplementation("androidx.compose.ui:ui-tooling")
}