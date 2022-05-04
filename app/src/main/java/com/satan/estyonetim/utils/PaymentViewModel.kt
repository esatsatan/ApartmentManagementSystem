package com.satan.estyonetim.utils

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.satan.estyonetim.model.PaymentAttributes
import com.satan.estyonetim.sqlitedatabase.PaymentDetailDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PaymentViewModel(application: Application) : AndroidViewModel(application) {

     val readAllData : LiveData<List<PaymentAttributes>>
    private val repository : PaymentDetailRepository

    init {
        val userDao = PaymentDetailDatabase.getDatabase(application).userDao()
        repository = PaymentDetailRepository(userDao)
        readAllData = repository.readAllData
    }

    fun addPayment(payment : PaymentAttributes) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPayment(payment)
        }
    }


}