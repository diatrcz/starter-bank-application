package hu.bme.aut.android.bank.fragments

import hu.bme.aut.android.bank.R
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.bank.data.priceitem.PriceItem
import hu.bme.aut.android.bank.databinding.DialogNewSpendingBinding

class NewSpendingDialogFragment : DialogFragment() {
    interface NewSpendingDialogListener {
        fun onNewSpendingCreated(newItem: PriceItem)
    }

    private lateinit var listener: NewSpendingDialogListener

    private lateinit var binding: DialogNewSpendingBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewSpendingDialogListener
            ?: throw RuntimeException("Activity must implement the NewSpendingDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogNewSpendingBinding.inflate(LayoutInflater.from(context))
        binding.spCategory.adapter = ArrayAdapter(
            requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.category_items)
        )

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_spending)
            .setView(binding.root)
            .setPositiveButton(R.string.button_ok) { dialogInterface, i ->
                if (isValid()) {
                    listener.onNewSpendingCreated(getPriceItem())
                }
            }
            .setNegativeButton(R.string.button_cancel, null)
            .create()
    }

    private fun isValid() = binding.etPrice.text.isNotEmpty()

    private fun getPriceItem() = PriceItem(
        price = binding.etPrice.text.toString().toInt(),
        category = PriceItem.Category.getByOrdinal(binding.spCategory.selectedItemPosition)
            ?: PriceItem.Category.Bills,
        type = "Spending"
    )

    companion object {
        const val TAG = "NewPriceItemDialogFragment"
    }


}