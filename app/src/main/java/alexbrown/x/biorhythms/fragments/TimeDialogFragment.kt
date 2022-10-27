package alexbrown.x.biorhythms.fragments

import alexbrown.x.biorhythms.MainActivity
import alexbrown.x.biorhythms.R
import alexbrown.x.biorhythms.utils.DateTimeStorage
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class TimeDialogFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    private lateinit var dateTimeStorage: DateTimeStorage

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreate(savedInstanceState)

        dateTimeStorage = context?.let { DateTimeStorage(it, this.activity as MainActivity) }!!

        return TimePickerDialog(activity, this, dateTimeStorage.savedDateTime.get(Calendar.HOUR), dateTimeStorage.savedDateTime.get(Calendar.MINUTE), DateFormat.is24HourFormat(activity))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_time_dialog, container, false)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        dateTimeStorage.saveTime(hourOfDay, minute)
    }
}