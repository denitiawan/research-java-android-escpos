[Back to Research Logs](https://github.com/denitiawan/research-java-android-escpos/blob/main/research-logs/readme.md)

|Date|Assign|
|--|--|
|22-mei-2023|[Deni Setiawan](https://github.com/denitiawan)|
# Setup EscPos with Bluetooth Adapter on Java Android Project

## Overviews
- [Requirement](#requirement)
- [Gradle](#gradle)
- [Printing Processing Flows](#printing-processing-flows)
- [Custom format receipt](#custom-format-receipt)
- [Result](#result)

## Requirement
```
    # Android -------
    compileSdk : 30
    minSdk : 19
    targetSdk : 30

    # Librarry -------
    ESCPOS-ThermalPrinter : https://github.com/DantSu/ESCPOS-ThermalPrinter-Android
    
    # IDEA -------
    Android Studio
        
    # Device -------
    Printer Thermal, escpos command & bluetooh support (Printer C58BT)
    Android phone / tab        
    
```


## Gradle
- root/setting.gradle
```
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        maven { url 'https://jitpack.io' }
        mavenCentral()
        jcenter()
    }
}
rootProject.name = "Android EscPos"
include ':app'

```

- root/build.gradle
```
buildscript {
    repositories {
        google()
        maven { url 'https://jitpack.io' }
        mavenCentral()

    }
    dependencies {
        classpath "com.android.tools.build:gradle:7.0.3"
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}

```

- root/app/build.gradle
```
plugins {
    id 'com.android.application'
}

android {
    compileSdk 30

    defaultConfig {
        applicationId "com.deni.escpos"
        minSdk 19
        targetSdk 30
        versionCode 2
        versionName "1.0.2"
        setProperty("archivesBaseName", "androidescpos" + "-v"  +  versionName )
        //or so
        archivesBaseName = "androidescpos-v$versionName"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled true
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
            versionNameSuffix "-MyNiceDebugModeName"
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
    compileSdkVersion 31
    buildToolsVersion '31.0.0'
    buildFeatures {
        viewBinding true
    }
    android {
        lintOptions {
            checkReleaseBuilds false
            // Or, if you prefer, you can continue to check for errors in release builds,
            // but continue the build even when errors are found:
            abortOnError false
        }
    }
}

dependencies {

    // appcompact
    implementation 'androidx.appcompat:appcompat:1.4.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'

    // theme
    implementation 'com.google.android.material:material:1.4.0'

    // test
    testImplementation 'junit:junit:4.+'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'

    // escpos
    implementation 'com.github.DantSu:ESCPOS-ThermalPrinter-Android:3.0.1'

}

```

## Printing Processing Flows 
source code on package : `root/app/src/main/java/com.deni.escpos/escposutils`
![image](https://github.com/denitiawan/research-java-android-escpos/assets/11941308/b8370b50-8b7c-4c21-9e62-f6c22441abc9)

## Custom format receipt
see full source code on class  :  EscPosReceiptFormat.java
```
/**
     * Format receipt custom by https://github.com/denitiawan
     *
     * @param context from Activity
     * @param printer from EscPosPrinter
     * @return
     */
    private static String formatReceiptCustom(Context context, EscPosPrinter printer, Transaction transaction) {

        return
                setEnter(center) +
                        setOutletLogo(center, context, printer) +

                        setTitle(center, "Best Brand Shop") +

                        setText(center, "Scienta Bussines Park") +
                        setText(center, "Tangerang Selatan") +
                        setText(center, "Banten") +
                        setText(center, "Indonesia") +
                        setText(center, "+6221-580-6052") +
                        setBorder() +

                        setText(left, "NPWP", left, "12.0.12.029302") +
                        setText(left, "Tanggal", left, getSimpleDateFormat().format(new Date())) +
                        setText(left, "Cetak", left, getSimpleDateFormat().format(new Date())) +
                        setText(left, "Nomor Invoice", left, "INV-000001") +
                        setText(left, "Kasir", left, "Deni Setiawan") +
                        setBorder() +

                        setItemName(left, "Bread Choco Vanilla", right, "Rp. 95.000") +
                        setItemValue(left, "4", "Rp. 25.000") +
                        setItemDiskon(left, "5.000") +

                        setItemName(left, "Milk Shake Banana", right, "Rp. 40.000") +
                        setItemValue(left, "4", "Rp. 10.000") +

                        setBorderThin() +
                        setTotal(right, "Total", right, "Rp. 135.000") +
                        setTotal(right, "Pajak", right, "Rp. 13.500") +
                        setTotal(right, "Net", right, "Rp. 150.000") +

                        setBorderThin() +
                        setTotal(right, "Tunai", right, "Rp. 200.000") +
                        setTotal(right, "Donasi", right, "Rp. 10.000") +
                        setTotal(right, "Kembali", right, "Rp. 40.000") +


                        setBorder() +
                        setCustmerBlock(left, "Customer") +
                        setEnter(center) +
                        setText(left, "Pembeli  Setia") +
                        setText(left, "Point", left, "10 Point") +

                        setBorder() +
                        setText(center, "Terimakasih") +
                        setText(center, "Telah berbelanja di tempat kami") +
                        setEnter(left) +


                        setText(center, "Scan Me") +
                        setQRCode(center, "https://github.com/denitiawan") +
                        setEnter(left) +

                        setText(center, "https://github.com/denitiawan") +
                        setEnter(center) +

                        setBarcode(center, "831254784551") +
                        setEnter(left) +

                        setText(center, "[Custom Format]") +
                        setEnter(center) +
                        setEnter(center);


    }
```

## Result
- layout - Home
- ![image](https://github.com/denitiawan/research-java-android-escpos/assets/11941308/5bcc1b92-ee42-4ba6-8606-fc1660cb6425)

- layout - Search Printer Thermal
- ![image](https://github.com/denitiawan/research-java-android-escpos/assets/11941308/32b650b6-6d76-489a-9f4b-a08bd7d80f11) 

- Layout - Success Printing Response
- ![image](https://github.com/denitiawan/research-java-android-escpos/assets/11941308/da93ea54-743c-4087-9337-a4afc3b8b56b)

- Receipt Custom Format
- ![image](https://github.com/denitiawan/research-java-android-escpos/assets/11941308/78cc9674-382e-4dd3-a643-913034ce97b7)



