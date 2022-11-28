package hu.bme.aut.android.bank.fragments

import hu.bme.aut.android.bank.R
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.bank.data.priceitem.PriceItem
import hu.bme.aut.android.bank.databinding.DialogNewIncomeBinding


class NewIncomeDialogFragment : DialogFragment() {
    interface NewIncomeDialogListener {
        fun onNewIncomeCreated(newItem: PriceItem)
    }

    private lateinit var listener: NewIncomeDialogListener

    private lateinit var binding: DialogNewIncomeBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewIncomeDialogListener
            ?: throw RuntimeException("Activity must implement the NewIncomeDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogNewIncomeBinding.inflate(LayoutInflater.from(context))

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_income)
            .setView(binding.root)
            .setPositiveButton(R.string.button_ok) { dialogInterface, i ->
                if (isValid()) {
                    listener.onNewIncomeCreated(getPriceItem())
                }
            }
            .setNegativeButton(R.string.button_cancel, null)
            .create()
    }

    private fun isValid() = binding.etPrice.text.isNotEmpty()

    private fun getPriceItem() = PriceItem(
        price = binding.etPrice.text.toString().toInt(),
        category = PriceItem.Category.Income,
        type = "Income"
    )

    companion object {
        const val TAG = "NewIncomeDialogFragment"
    }


}