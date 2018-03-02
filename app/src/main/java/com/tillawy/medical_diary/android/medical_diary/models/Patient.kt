package com.tillawy.medical_diary.android.medical_diary.models

import io.realm.RealmObject


/**
 * Created by mohammed on 3/2/18.
 */



open class Patient (

        var firstName : String? = null,
        var fatherName : String? = null,
        var lastName : String? = null
) :
        RealmObject() {

}
