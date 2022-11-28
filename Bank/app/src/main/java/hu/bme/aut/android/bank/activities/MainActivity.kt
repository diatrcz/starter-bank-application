package hu.bme.aut.android.bank.activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.SubMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.android.bank.R
import hu.bme.aut.android.bank.adapter.BankAdapter
import hu.bme.aut.android.bank.data.BankItemsDatabase
import hu.bme.aut.android.bank.data.priceitem.PriceItem
import hu.bme.aut.android.bank.databinding.ActivityMainBinding
import hu.bme.aut.android.bank.fragments.NewIncomeDialogFragment
import hu.bme.aut.android.bank.fragments.NewSpendingDialogFragment
import hu.bme.aut.android.bank.fragments.NewWarningPriceDialogFragment
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity(), BankAdapter.PriceItemClickListener,
    NewSpendingDialogFragment.NewSpendingDialogListener, NewIncomeDialogFragment.NewIncomeDialogListener,
    NewWarningPriceDialogFragment.NewWarningPriceListener {
    private lateinit var binding: ActivityMainBinding

    private lateinit var database: BankItemsDatabase
    private lateinit var adapter: BankAdapter

    private var warningPrice: Int = 0
    private var warningString: String = "warningPrice"

    private var balance: Long = 0;
    private var balanceString: String = "balance"

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        setTheme(R.style.Theme_Bank);

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setTitle("")

        database = BankItemsDatabase.getDatabase(applicationContext)

        val sharedPref = this?.getPreferences(Context.MODE_PRIVATE)

        if (sharedPref != null) {
            if(!sharedPref.contains(warningString)) {
                val edit = sharedPref.edit()
                edit.putInt(warningString, 0)
                edit.apply()

            }
            else
                warningPrice = sharedPref.getInt(warningString, 0)

            if(!sharedPref.contains(balanceString)){
                val edit = sharedPref.edit()
                edit.putLong(balanceString, 0)
                edit.apply()
            }
            else
                balance = sharedPref.getLong(balanceString, 0)
        }

        binding.balance.text = "Balance: " + balance.toString()

        binding.clear.setOnClickListener {
            clearAll()
        }

        binding.graph.setOnClickListener {
            startActivity(Intent(this, PieChartActivity::class.java))
        }

        initRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val toolbarMenu: Menu = binding.toolbar2.menu
        menuInflater.inflate(R.menu.menu_toolbar, toolbarMenu)
        for (i in 0 until toolbarMenu.size()) {
            val menuItem: MenuItem = toolbarMenu.getItem(i)
            menuItem.setOnMenuItemClickListener { item -> onOptionsItemSelected(item) }
            if (menuItem.hasSubMenu()) {
                val subMenu: SubMenu = menuItem.subMenu
                for (j in 0 until subMenu.size()) {
                    subMenu.getItem(j)
                        .setOnMenuItemClickListener { item -> onOptionsItemSelected(item) }
                }
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_style_spending -> {
                NewSpendingDialogFragment().show(
                    supportFragmentManager,
                    NewSpendingDialogFragment.TAG
                )
                true
            }
            R.id.menu_style_income -> {
                NewIncomeDialogFragment().show(
                    supportFragmentManager,
                    NewIncomeDialogFragment.TAG
                )
                true
            }
            R.id.change_warning_amount -> {
                NewWarningPriceDialogFragment().show(
                    supportFragmentManager,
                    NewWarningPriceDialogFragment.TAG
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun initRecyclerView() {
        adapter = BankAdapter()
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.adapter = adapter
        loadItemsInBackground()
    }

    private fun loadItemsInBackground() {
        thread {
            val items = database.PriceItemDao().getAll()
            runOnUiThread {
                adapter.update(items)
            }
        }
    }

    override fun onItemChanged(item: PriceItem) {
        thread {
            database.PriceItemDao().update(item)
            Log.d("MainActivity", "PriceItem update was successful")
        }
    }

    override fun onNewSpendingCreated(newItem: PriceItem) {
        thread {
            val insertId = database.PriceItemDao().insert(newItem)
            newItem.id = insertId
            runOnUiThread {
                adapter.addItem(newItem)
            }
        }
        updateBalance(newItem)
    }

    override fun onNewIncomeCreated(newItem: PriceItem) {
        thread {
            val insertId = database.PriceItemDao().insert(newItem)
            newItem.id = insertId
            runOnUiThread {
                adapter.addItem(newItem)
            }
        }
        updateBalance(newItem)
    }

    override fun onWarningPriceChanged(newPrice: Int) {
        val sharedPref = this?.getPreferences(Context.MODE_PRIVATE)

        if (sharedPref != null) {
            warningPrice = newPrice
            val edit = sharedPref.edit()
            edit.putInt(warningString, newPrice)
            edit.commit()
        }

        val snackbar = Snackbar.make(binding.root, "Warning price changed to ${warningPrice}", Snackbar.LENGTH_SHORT)
        snackbar.view.setBackgroundColor(Color.parseColor("#4E3413"))
        snackbar.setTextColor(Color.parseColor("#ffffff"))
        snackbar.show()

    }

    private fun updateBalance(newItem: PriceItem) {
        if(newItem.type.equals("Spending")) {
            balance -= newItem.price
            if(balance < warningPrice) {
                val snackbar = Snackbar.make(binding.root, R.string.warn_message, Snackbar.LENGTH_SHORT)
                snackbar.view.setBackgroundColor(Color.parseColor("#4E3413"))
                snackbar.setTextColor(Color.parseColor("#ffffff"))
                snackbar.show()
            }
        }
        else
            balance += newItem.price

        val sharedPref = this?.getPreferences(Context.MODE_PRIVATE)
        if(sharedPref != null) {
            val edit = sharedPref.edit()
            edit.putLong(balanceString, balance)
            edit.commit()
        }
        binding.balance.text = "Balance: " + balance.toString()
    }

    private fun clearAll() {

        thread {
            database.PriceItemDao().clear()
            runOnUiThread {
                adapter.clear()
            }
        }

        balance = 0;
        val sharedPref = this?.getPreferences(Context.MODE_PRIVATE)
        if(sharedPref != null) {
            val edit = sharedPref.edit()
            edit.putLong(balanceString, balance)
            edit.commit()
        }
        binding.balance.text = "Balance: " + balance.toString()

    }

}