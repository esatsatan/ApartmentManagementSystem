package com.satan.estyonetim.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "paymentDetail_table")
data class PaymentAttributes(
    @PrimaryKey(autoGenerate = true)    // İd will be autogenerated
    val id : Int,
    var payName : String?,
    var apartNo : String?,
    var roomNo : String?,
    val time : String? )
