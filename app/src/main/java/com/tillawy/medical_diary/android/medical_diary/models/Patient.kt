package com.tillawy.medical_diary.android.medical_diary.models

import io.realm.RealmObject
import java.util.Date

/**
 * Created by mohammed on 3/2/18.
 */


enum class BloodType
constructor(val value : Int)
{
    Unknown(0),
    Apositive(1),
    Anegative(2),
    Bpositive(3),
    Bnegative (4),
    ABpositive(5),
    ABnegative(6),
    Opositive(7),
    Onegative(8)
}

open class Patient () : RealmObject(){
        var firstName : String? = null
        var fatherName : String? = null
        var lastName : String? = null
        var birthDate : Date? = null
        var age : Int? = null
        var isOrganDonor : Boolean = false
        var height : Int? = null
        var width : Int? = null
        var bloodType: Int = 0
}
