package com.satan.estyonetim.sqlitedatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.satan.estyonetim.model.PaymentAttributes
import com.satan.estyonetim.utils.UserDao

@Database(entities = [PaymentAttributes::class], version = 1, exportSchema = false)
abstract class PaymentDetailDatabase : RoomDatabase() {

    abstract fun userDao() : UserDao

    companion object {

        @Volatile
        private var INSTANCE : PaymentDetailDatabase? = null

        fun getDatabase(context : Context) : PaymentDetailDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PaymentDetailDatabase::class.java,
                    "payment_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }



}