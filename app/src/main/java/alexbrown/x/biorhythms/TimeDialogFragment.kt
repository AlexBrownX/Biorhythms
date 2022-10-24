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
import java.io.File

class TimeDialogFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreate(savedInstanceState)

        val file = File(context!!.filesDir, "TimeOfBirth")

        if (file.exists()) {
            val savedTime = file.readText()
            val savedHour = savedTime.split("/")[0]
            val savedMinutes = savedTime.split("/")[1]

            return TimePickerDialog(
                activity,
                this,
                Integer.valueOf(savedHour),
                Integer.valueOf(savedMinutes),
                DateFormat.is24HourFormat(activity)
            )
        }

        return TimePickerDialog(activity, this, 0, 0, DateFormat.is24HourFormat(activity))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_time_dialog, container, false)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val file = File(context!!.filesDir, "TimeOfBirth")
        file.writeText("$hourOfDay/$minute")

        val activity = context as MainActivity
        activity.setDateAndTimeFromFile()
        activity.runCalculation()
    }
}