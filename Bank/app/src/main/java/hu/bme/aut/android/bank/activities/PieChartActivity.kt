package hu.bme.aut.android.bank.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import hu.bme.aut.android.bank.data.BankItemsDatabase
import hu.bme.aut.android.bank.data.priceitem.PriceItem
import hu.bme.aut.android.bank.databinding.ActivityPieChartBinding
import kotlin.concurrent.thread

class PieChartActivity : AppCompatActivity() {

    private var spendings = mutableListOf<Int>()

    private lateinit var binding : ActivityPieChartBinding

    private lateinit var database: BankItemsDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPieChartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = BankItemsDatabase.getDatabase(applicationContext)

        loadChart()
    }

    private fun loadChart(){

        thread {
            spendings.clear()

            spendings.add(database.PriceItemDao().getByCatgory(PriceItem.Category.Bills))
            spendings.add(database.PriceItemDao().getByCatgory(PriceItem.Category.Home))
            spendings.add(database.PriceItemDao().getByCatgory(PriceItem.Category.Transportation))
            spendings.add(database.PriceItemDao().getByCatgory(PriceItem.Category.Clothing))
            spendings.add(database.PriceItemDao().getByCatgory(PriceItem.Category.Groceries))
            spendings.add(database.PriceItemDao().getByCatgory(PriceItem.Category.Health))
            spendings.add(database.PriceItemDao().getByCatgory(PriceItem.Category.Entertainment))
            spendings.add(database.PriceItemDao().getByCatgory(PriceItem.Category.Other))

            runOnUiThread() {
                val entries: ArrayList<PieEntry> = ArrayList()
                if(spendings[0] > 0)
                    entries.add(PieEntry(spendings[0].toFloat(), "Bills"))
                if(spendings[1] > 0)
                    entries.add(PieEntry(spendings[1].toFloat(), "Home"))
                if(spendings[2] > 0)
                    entries.add(PieEntry(spendings[2].toFloat(), "Transportation"))
                if(spendings[3] > 0)
                    entries.add(PieEntry(spendings[3].toFloat(), "Clothing"))
                if(spendings[4] > 0)
                    entries.add(PieEntry(spendings[4].toFloat(), "Groceries"))
                if(spendings[5] > 0)
                    entries.add(PieEntry(spendings[5].toFloat(), "Health"))
                if(spendings[6] > 0)
                    entries.add(PieEntry(spendings[6].toFloat(), "Entertainment"))
                if(spendings[7] > 0)
                    entries.add(PieEntry(spendings[7].toFloat(), "Other"))

                val dataSet = PieDataSet(entries, "")
                dataSet.colors = listOf(
                    Color.parseColor("#184C19"),
                    Color.parseColor("#908AA4"),
                    Color.parseColor("#D7B6BC"),
                    Color.parseColor("#AD8786"),
                    Color.parseColor("#8C5349"),
                    Color.parseColor("#B99470"),
                    Color.parseColor("#582F0E"),
                    Color.parseColor("#3B1723")
                )

                val data = PieData(dataSet)
                binding.chartSpending.data = data
                binding.chartSpending.data.setValueTextSize(10f)
                binding.chartSpending.data.setValueTextColor(Color.parseColor("#ffffff"))
                binding.chartSpending.setDrawEntryLabels(false)
                binding.chartSpending.legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
                binding.chartSpending.legend.orientation = Legend.LegendOrientation.VERTICAL
                binding.chartSpending.invalidate()
            }
        }
    }
}