package hu.bme.aut.android.bank.data.priceitem

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "priceitem")
class PriceItem (
    @ColumnInfo(name = "id")  @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "price") var price: Int,
    @ColumnInfo(name = "category") var category: Category,
    @ColumnInfo(name = "type") var type: String
    ) {

    enum class Category {
        Bills, Transportation,
        Groceries, Health, Home,
        Clothing, Entertainment, Other,
        Income;
        companion object {
            @JvmStatic
            @TypeConverter
            fun getByOrdinal(ordinal: Int): Category? {
                var ret: Category? = null
                for(cat in values()) {
                    if(cat.ordinal == ordinal) {
                        ret = cat
                        break
                    }
                }
                return ret
            }

            @JvmStatic
            @TypeConverter
            fun toInt(category: Category): Int {
                return category.ordinal
            }
        }
    }
}