package com.example.helutales

//data class Quiz(
//    val title: String,
//    val questions: MutableMap<String, Question> = mutableMapOf()
//)

//import android.os.Parcel
//import android.os.Parcelable
//
//data class Quiz(val title: String, val questions: Map<String, Any>) {
//    constructor(parcel: Parcel) : this(
//        parcel.readString() ?: "",
//        parcel.readHashMap(String::class.java.classLoader) as Map<String, Any>
//    )
//
//    fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(title)
//        parcel.writeMap(questions)
//    }
//
//    fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<Quiz> {
//        override fun createFromParcel(parcel: Parcel): Quiz {
//            return Quiz(parcel)
//        }
//
//        override fun newArray(size: Int): Array<Quiz?> {
//            return arrayOfNulls(size)
//        }
//    }
//}

import android.os.Parcel
import android.os.Parcelable

class Quiz(
    val title: String,
    val questions: Map<String, Question>
) : Parcelable {
    // Implement the Parcelable interface methods here

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        // Deserialize the questions Map from the Parcel
        parcel.readHashMap(Question::class.java.classLoader) as Map<String, Question>
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        // Serialize the questions Map to the Parcel
        parcel.writeMap(questions)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Quiz> {
        override fun createFromParcel(parcel: Parcel): Quiz {
            return Quiz(parcel)
        }

        override fun newArray(size: Int): Array<Quiz?> {
            return arrayOfNulls(size)
        }
    }
}

