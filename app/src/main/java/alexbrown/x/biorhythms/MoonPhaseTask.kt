package alexbrown.x.biorhythms

import android.graphics.drawable.PictureDrawable
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.caverock.androidsvg.SVG
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import java.util.concurrent.Executors

class MoonPhaseTask(private val view: View) {

    private val tag = "MoonPhaseTask"
    private val apiUrl = "https://www.icalendar37.net/lunar/api/"
    private val getMethod = "GET"
    private val contentType = "Content-Type"
    private val accept = "Accept"
    private val applicationJson = "application/json"
    private val today = GregorianCalendar()
    private val month = today.get(Calendar.MONTH) + 1
    private val year = today.get(Calendar.YEAR)
    private val size = 200
    private val lightColor = "f69f05"
    private val shadeColor = "000000"

    init {
        val backgroundExecutor = Executors.newSingleThreadScheduledExecutor()
        val handler = Handler(Looper.getMainLooper())

        backgroundExecutor.execute {
            val moonPhaseDetail = requestMoonPhaseData()
            val svgString = moonPhaseDetail!!.svg

            val linkStartIndex = svgString.indexOf("<a")
            val linkEndIndex = svgString.indexOf("</a>")

            val svgPartOne = svgString.substring(0, linkStartIndex)
            val svgPartTwo = svgString.substring(linkEndIndex + 4, svgString.length)
            val authorUrl = svgString.substring(linkStartIndex, linkEndIndex + 4)

            val svg = SVG.getFromString("$svgPartOne$svgPartTwo")
            val drawable = PictureDrawable(svg.renderToPicture())

            handler.post {
                val moonPhaseImageView = view.findViewById(R.id.imageview_moon_phase) as ImageView
                moonPhaseImageView.setImageDrawable(drawable)

                val moonPhaseTextView = view.findViewById(R.id.textview_moon_phase_description) as TextView
                moonPhaseTextView.text = "Today's moon phase: ${moonPhaseDetail.phaseName} ${formatLightingPercentage(moonPhaseDetail.lighting)}%"
            }
        }
    }

    private fun requestMoonPhaseData(): MoonPhaseDetail? {
        try {
            val url = getUrl()
            val connection = getConnection(url)
            val jsonResponse = readResponse(connection)
            val monthMoonPhases = convertJsonToObject(jsonResponse)
            return monthMoonPhases.phase["29"]
//            return monthMoonPhases.phase[GregorianCalendar().get(Calendar.DATE).toString()]
        } catch (e: Exception) {
            Log.e(tag, "Error retrieving moon phase", e)
        }

        return null
    }

    private fun readResponse(connection: HttpURLConnection): String {
        BufferedReader(InputStreamReader(connection.inputStream, Charsets.UTF_8.name())).use { bufferedReader ->
            val response = StringBuilder()
            var responseLine: String?

            while (bufferedReader.readLine().also { it -> responseLine = it } != null) {
                response.append(responseLine!!.trim { it <= ' ' })
            }

            return response.toString()
        }
    }

    private fun getConnection(url: URL): HttpURLConnection {
        val con: HttpURLConnection = url.openConnection() as HttpURLConnection
        con.requestMethod = getMethod
        con.doOutput = true
        con.setRequestProperty(contentType, applicationJson)
        con.setRequestProperty(accept, applicationJson)
        return con
    }

    private fun getUrl(): URL {
        val urlParams = "?lang=en&month=$month&year=$year&size=$size&lightColor=%23$lightColor&shadeColor=%23$shadeColor&texturize=false";
        return URL("$apiUrl$urlParams")
    }

    private fun convertJsonToObject(jsonResponse: String): MoonPhaseResponse {
        return Gson().fromJson(jsonResponse, MoonPhaseResponse::class.java)
    }

    private fun formatLightingPercentage(input: Double) = "%.${2}f".format(input)
}


