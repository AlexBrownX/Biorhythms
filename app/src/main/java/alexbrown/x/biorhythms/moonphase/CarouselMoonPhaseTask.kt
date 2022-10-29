package alexbrown.x.biorhythms.moonphase

import alexbrown.x.biorhythms.R
import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.helper.widget.Carousel
import com.caverock.androidsvg.SVG
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import kotlin.collections.ArrayList

class CarouselMoonPhaseTask(private val view: View) {

    private val dateFormat = SimpleDateFormat("EEEE dd MMMM")
    private val tag = "CarouselMoonPhaseTask"
    private val apiUrl = "https://www.icalendar37.net/lunar/api/"
    private val getMethod = "GET"
    private val contentType = "Content-Type"
    private val accept = "Accept"
    private val applicationJson = "application/json"
    private val today = GregorianCalendar()
    private val month = today.get(Calendar.MONTH) + 1
    private val year = today.get(Calendar.YEAR)
    private val size = 200
    private val shadeColor = "000000"
    private val colors = arrayOf("f60b6a", "00f65b", "f69f05")

    init {
        val backgroundExecutor = Executors.newSingleThreadScheduledExecutor()
        val handler = Handler(Looper.getMainLooper())

        backgroundExecutor.execute {
            val carousel = view.findViewById<Carousel>(R.id.carousel_moon_phase)
            val progressBar = view.findViewById<ProgressBar>(R.id.moon_phase_progress_bar)
            val moonPhaseTextView = view.findViewById<TextView>(R.id.textview_moon_phase_name)
            val moonPhaseDetails = requestMoonPhaseData()
            val drawables = getDrawables(moonPhaseDetails!!)

            handler.post {
                setupCarousel(view, carousel, drawables, moonPhaseDetails)
                carousel.jumpToIndex(GregorianCalendar().get(Calendar.DATE).minus(1))
                moonPhaseTextView.text = getMoonPhaseText(moonPhaseDetails, GregorianCalendar().get(Calendar.DATE).minus(1))
                progressBar.visibility = View.INVISIBLE
                carousel.refresh()
            }
        }
    }

    private fun setupCarousel(
        view: View,
        carousel: Carousel,
        images: List<Drawable>,
        moonPhaseDetails: Map<String, MoonPhaseDetail>
    ) {
        carousel.setAdapter(object : Carousel.Adapter {
            override fun count(): Int {
                return images.size
            }

            override fun populate(view: View, index: Int) {
                if (view is ImageView) {
                    view.setImageDrawable(images[index])
                }
            }

            override fun onNewItem(index: Int) {
                val moonPhaseTextView = view.findViewById<TextView>(R.id.textview_moon_phase_name)
                moonPhaseTextView.text = getMoonPhaseText(moonPhaseDetails, index)
            }
        })
    }

    private fun getMoonPhaseText(moonPhaseDetails: Map<String, MoonPhaseDetail>, index: Int): String {
        val selectedMoonPhase = moonPhaseDetails[(index + 1).toString()]

        if (selectedMoonPhase != null) {
            val calendar = GregorianCalendar()
            calendar.set(Calendar.DATE, index + 1)

            return "${dateFormat.format(calendar.time)}\n${selectedMoonPhase.phaseName} ${formatLightingPercentage(selectedMoonPhase.lighting)} %"
        }

        return ""
    }

    private fun getDrawables(moonPhaseDetails: Map<String, MoonPhaseDetail>): List<Drawable> {
        return ArrayList(moonPhaseDetails.values).map { moonPhaseDetail ->
            val svgStringRaw = moonPhaseDetail.svg

            val linkStartIndex = svgStringRaw.indexOf("<a")
            val linkEndIndex = svgStringRaw.indexOf("</a>")

            val svgPartOne = svgStringRaw.substring(0, linkStartIndex)
            val svgPartTwo = svgStringRaw.substring(linkEndIndex + 4, svgStringRaw.length)

            val svgString = "$svgPartOne$svgPartTwo".replace("A -", "A ")

            val svg = SVG.getFromString(svgString)
            val drawable = PictureDrawable(svg.renderToPicture())
            drawable
        }
    }

    private fun requestMoonPhaseData(): Map<String, MoonPhaseDetail>? {
        try {
            val url = getUrl()
            val connection = getConnection(url)
            val jsonResponse = readResponse(connection)
            val moonPhases = convertJsonToObject(jsonResponse)
            return moonPhases.phase
        } catch (e: Exception) {
            Log.e(tag, "Error retrieving moon phase", e)
        }

        return null
    }

    private fun readResponse(connection: HttpURLConnection): String {
        BufferedReader(
            InputStreamReader(
                connection.inputStream,
                Charsets.UTF_8.name()
            )
        ).use { bufferedReader ->
            val response = StringBuilder()
            var responseLine: String?

            while (bufferedReader.readLine().also { responseLine = it } != null) {
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
        val urlParams = "?lang=en&month=$month&year=$year&size=$size&lightColor=%23${colors[(0..2).random()]}&shadeColor=%23$shadeColor&texturize=false"
        return URL("$apiUrl$urlParams")
    }

    private fun convertJsonToObject(jsonResponse: String): MoonPhaseResponse {
        return Gson().fromJson(jsonResponse, MoonPhaseResponse::class.java)
    }

    private fun formatLightingPercentage(input: Double) = "%.${2}f".format(input)
}


