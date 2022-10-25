package alexbrown.x.biorhythms

import android.content.Context
import android.widget.Button
import android.widget.TextView
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class DateTimeStorage(val context: Context, private val activity: MainActivity?) {

    constructor(context: Context) : this(context, null)

    private val dateFormatter = SimpleDateFormat("dd/MM/yyyy HH:mm")
    private val dateFile: File = File(context.filesDir, "DateOfBirth")
    private val timeFile: File = File(context.filesDir, "TimeOfBirth")
    lateinit var savedDateTime: GregorianCalendar

    var firstRun = false
    init {
        if (!dateFile.exists()) {
            firstRun = true
        }

        if (dateFile.exists()) {
            setDateTimeFromStorage()
        } else {
            savedDateTime = GregorianCalendar()
        }
    }

    fun saveDate(year: Int, month: Int, dayOfMonth: Int) {
        dateFile.writeText("$year/${month}/$dayOfMonth")
        setDateTimeFromStorage()
        displayDateOfBirth()
        enableResultButtons()
    }

    fun saveTime(hourOfDay: Int, minute: Int) {
        timeFile.writeText("$hourOfDay/$minute")
        setDateTimeFromStorage()
        displayDateOfBirth()
    }

    private fun displayDateOfBirth() {
        activity?.findViewById<TextView>(R.id.textview_date_time)!!.text = "Selected date of birth: ${dateFormatter.format(savedDateTime.time)}"
    }

    private fun enableResultButtons() {
        val dailyResultsButton = activity?.findViewById(R.id.button_show_daily_results) as Button
        dailyResultsButton.isEnabled = true

        val dailyResultsTextView = activity.findViewById(R.id.textview_daily_information) as TextView
        dailyResultsTextView.isEnabled = true

        val weeklyResultsButton = activity.findViewById(R.id.button_show_weekly_results) as Button
        weeklyResultsButton.isEnabled = true

        val weeklyResultsTextView = activity.findViewById(R.id.textview_weekly_information) as TextView
        weeklyResultsTextView.isEnabled = true

        val longTermResultsButton = activity.findViewById(R.id.button_show_long_term_results) as Button
        longTermResultsButton.isEnabled = true

        val longTermResultsTextView = activity.findViewById(R.id.textview_long_term_information) as TextView
        longTermResultsTextView.isEnabled = true
    }

    private fun setDateTimeFromStorage() {
        val calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)
        var hour = 0
        var minute = 0

        if (dateFile.exists()) {
            val savedDateString = dateFile.readText()
            year = Integer.valueOf(savedDateString.split("/")[0])
            month = Integer.valueOf(savedDateString.split("/")[1])
            day = Integer.valueOf(savedDateString.split("/")[2])
        }

        if (timeFile.exists()) {
            val savedTimeString = timeFile.readText()
            hour = Integer.valueOf(savedTimeString.split("/")[0])
            minute = Integer.valueOf(savedTimeString.split("/")[1])
        }

        savedDateTime = GregorianCalendar(year, month, day, hour, minute)
    }
}