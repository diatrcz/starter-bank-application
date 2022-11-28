package hu.bme.aut.android.bank.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.bank.databinding.DialogChangeWarningPriceBinding

class NewWarningPriceDialogFragment : DialogFragment() {

    interface NewWarningPriceListener {
        fun onWarningPriceChanged(newPrice: Int)
    }

    private lateinit var binding: DialogChangeWarningPriceBinding

    private lateinit var listener: NewWarningPriceListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewWarningPriceDialogFragment.NewWarningPriceListener
            ?: throw RuntimeException("Activity must implement the NewWarningPriceDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogChangeWarningPriceBinding.inflate(LayoutInflater.from(context))

        return AlertDialog.Builder(requireContext())
            .setTitle(hu.bme.aut.android.bank.R.string.new_warning_price)
            .setView(binding.root)
            .setPositiveButton(hu.bme.aut.android.bank.R.string.button_ok) { dialogInterface, i ->
                if (isValid()) {
                    listener.onWarningPriceChanged(getPrice())
                }
            }
            .setNegativeButton(hu.bme.aut.android.bank.R.string.button_cancel, null)
            .create()
    }

    private fun isValid() = binding.etPrice.text.isNotEmpty()

    private fun getPrice() = binding.etPrice.text.toString().toInt()

    companion object {
        const val TAG = "NewWarningPriceDialogFragment"
    }

}