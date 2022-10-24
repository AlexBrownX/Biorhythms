package alexbrown.x.biorhythms

import android.content.Context
import java.io.File
import java.time.LocalDateTime
import java.util.*

class DateTimeStorage(context: Context) {

    private val dateFile: File = File(context.filesDir, "DateOfBirth")
    private val timeFile: File = File(context.filesDir, "TimeOfBirth")
    lateinit var savedDateTime: LocalDateTime
    var firstRun = false

    init {
        if (!dateFile.exists()) {
            firstRun = true
        }

        setDateTimeFromStorage()
    }

    fun saveDate(year: Int, month: Int, dayOfMonth: Int) {
        dateFile.writeText("$year/${month+1}/$dayOfMonth")
        setDateTimeFromStorage()
    }

    fun saveTime(hourOfDay: Int, minute: Int) {
        timeFile.writeText("$hourOfDay/$minute")
        setDateTimeFromStorage()
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

        savedDateTime = LocalDateTime.of(year, month, day, hour, minute)
    }
}