package com.satan.estyonetim.utils

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.satan.estyonetim.model.PaymentAttributes

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPayment(payment : PaymentAttributes)

    @Query("SELECT * FROM paymentDetail_table ORDER BY id ASC")
    fun readAllData() : LiveData<List<PaymentAttributes>>
}