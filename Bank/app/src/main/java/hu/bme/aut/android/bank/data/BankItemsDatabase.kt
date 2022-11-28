package hu.bme.aut.android.bank.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hu.bme.aut.android.bank.data.priceitem.PriceItem
import hu.bme.aut.android.bank.data.priceitem.PriceItemDao

@Database( entities = [PriceItem::class], version = 1)
@TypeConverters(value = [PriceItem.Category::class])
abstract class BankItemsDatabase : RoomDatabase() {
    abstract  fun PriceItemDao(): PriceItemDao


    companion object {
        fun getDatabase(applicationContext: Context): BankItemsDatabase {
            return Room.databaseBuilder(
                applicationContext,
                BankItemsDatabase::class.java,
                "bank-items"
            ).build();
        }
    }
}