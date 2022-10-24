package alexbrown.x.biorhythms

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment

class DateDialogFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var dateTimeStorage: DateTimeStorage

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreate(savedInstanceState)

        dateTimeStorage = context?.let { DateTimeStorage(it, this.activity as MainActivity) }!!

        return DatePickerDialog(requireContext(), this, dateTimeStorage.savedDateTime.year, dateTimeStorage.savedDateTime.month.value, dateTimeStorage.savedDateTime.dayOfMonth)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_date_dialog, container, false)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        dateTimeStorage.saveDate(year, month, dayOfMonth)
    }
}