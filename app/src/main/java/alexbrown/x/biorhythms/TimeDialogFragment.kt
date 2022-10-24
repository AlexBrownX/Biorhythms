package alexbrown.x.biorhythms

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment

class TimeDialogFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    private lateinit var dateTimeStorage: DateTimeStorage
    private lateinit var mainActivity: MainActivity

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreate(savedInstanceState)

        mainActivity = context as MainActivity
        dateTimeStorage = mainActivity.dateTimeStorage

        return TimePickerDialog(activity, this, dateTimeStorage.savedDateTime.hour, dateTimeStorage.savedDateTime.minute, DateFormat.is24HourFormat(activity))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_time_dialog, container, false)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        dateTimeStorage.saveTime(hourOfDay, minute)
        mainActivity.displayDateOfBirth()
        mainActivity.runCalculation()
    }
}