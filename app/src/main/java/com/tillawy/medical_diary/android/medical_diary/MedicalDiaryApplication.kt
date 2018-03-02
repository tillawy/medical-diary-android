
package com.tillawy.medical_diary.android.medical_diary;

import android.app.Application

import io.realm.Realm

class MedicalDiaryApplication : Application() {

        override fun onCreate() {
                super.onCreate()
                // Initialize Realm. Should only be done once when the application starts.
                Realm.init(this)
        }

}