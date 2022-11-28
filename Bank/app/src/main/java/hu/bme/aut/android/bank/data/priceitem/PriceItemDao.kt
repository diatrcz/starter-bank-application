package hu.bme.aut.android.bank.data.priceitem

import androidx.room.*

@Dao
interface PriceItemDao {
    @Query("SELECT * FROM priceitem")
    fun getAll(): List<PriceItem>

    @Query("DELETE FROM priceitem")
    fun clear()

    @Insert
    fun insert(priceItems: PriceItem) : Long

    @Update
    fun update(priceItems: PriceItem)

    @Delete
    fun delete(priceItems: PriceItem)

    @Query("SELECT SUM(price) FROM priceitem WHERE category = :category")
    fun getByCatgory(category: PriceItem.Category) : Int

}