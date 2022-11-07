package alexbrown.x.biorhythms.settings

import alexbrown.x.biorhythms.R
import alexbrown.x.biorhythms.utils.DateTimeStorage
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DateDialogFragment(private val dateTimeStorage: DateTimeStorage) : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreate(savedInstanceState)

        return DatePickerDialog(
            requireContext(),
            this,
            dateTimeStorage.savedDateTime.get(Calendar.YEAR),
            dateTimeStorage.savedDateTime.get(Calendar.MONTH),
            dateTimeStorage.savedDateTime.get(Calendar.DATE))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_date_dialog, container, false)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        dateTimeStorage.saveDate(year, month, dayOfMonth)
    }
}