package alexbrown.x.biorhythms

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.io.File
import java.util.*


class DateDialogFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreate(savedInstanceState)

        val file = File(context!!.filesDir, "DateOfBirth")

        if (file.exists()) {
            val savedDate = file.readText()
            val savedYear = savedDate.split("/")[0]
            val savedMonth = savedDate.split("/")[1]
            val savedDay = savedDate.split("/")[2]

            return DatePickerDialog(requireContext(), this, Integer.valueOf(savedYear), Integer.valueOf(savedMonth), Integer.valueOf(savedDay))
        }

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_date_dialog, container, false)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val file = File(context!!.filesDir, "DateOfBirth")
        file.writeText("$year/$month/$dayOfMonth")

        val activity = context as MainActivity
        activity.setDateAndTimeFromFile()
        activity.runCalculation()
    }
}