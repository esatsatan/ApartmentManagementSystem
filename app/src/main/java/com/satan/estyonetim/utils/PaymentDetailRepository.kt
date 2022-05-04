package com.satan.estyonetim.utils

import androidx.lifecycle.LiveData
import com.satan.estyonetim.model.PaymentAttributes

class PaymentDetailRepository(private val userDao : UserDao) {

    val readAllData : LiveData<List<PaymentAttributes>> = userDao.readAllData()

    suspend fun addPayment(payment : PaymentAttributes){
        userDao.addPayment(payment)
    }

}