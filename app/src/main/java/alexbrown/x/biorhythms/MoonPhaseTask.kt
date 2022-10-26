package alexbrown.x.biorhythms

import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import java.util.concurrent.Executors

class MoonPhaseTask(private val view: View) {

    init {
        val backgroundExecutor = Executors.newSingleThreadScheduledExecutor()
        val handler = Handler(Looper.getMainLooper())

        backgroundExecutor.execute {
            println("background executor start")
            val moonPhaseDetail = downloadMoonPhaseData()

            handler.post {
                val imageView: ImageView = view.findViewById(R.id.imageview_moon_phase) as ImageView
//                val svg = SVG

                /*
                SVG svg = SVGParser.getSVGFromResource(getResources(), R.drawable.example);
 // The following is needed because of image accelaration in some devices, such as Samsung
 imageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
 imageView.setImageDrawable(svg.createPictureDrawable());
                 */

                println("handler ui executor")
            }

            println("background executor end")
        }
    }

    private fun downloadMoonPhaseData(): MoonPhaseDetail? {
        try {
            val url = getUrl()
            val con: HttpURLConnection = url.openConnection() as HttpURLConnection
            con.requestMethod = "GET"
            con.doOutput = true
            con.setRequestProperty("Content-Type", "application/json")
            con.setRequestProperty("Accept", "application/json")

            BufferedReader(InputStreamReader(con.inputStream, "utf-8")).use { bufferedReader ->
                val response = StringBuilder()
                var responseLine: String?

                while (bufferedReader.readLine().also { responseLine = it } != null) {
                    response.append(responseLine!!.trim { it <= ' ' })
                }

                val testModel = Gson().fromJson(response.toString(), MoonPhaseResponse::class.java)
                return testModel.phase[Date().date.toString()]!!
            }
        } catch (e: Exception) {
            println("*** Error retrieving moon phase")
            TODO()
        }
    }

    private fun getUrl(): URL {
        val month = Date().month + 1
        val year = Date().year + 1900
        val size = 100
        val lightColor = "FFFF88"
        val shadeColor = "000000"

        val urlParams = "?lang=en&month=$month&year=$year&size=$size&lightColor=%23$lightColor&shadeColor=%23$shadeColor&texturize=false";

        return URL("https://www.icalendar37.net/lunar/api/$urlParams")
    }
}