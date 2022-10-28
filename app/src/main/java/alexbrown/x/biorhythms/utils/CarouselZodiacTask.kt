package alexbrown.x.biorhythms.utils

import alexbrown.x.biorhythms.R
import alexbrown.x.biorhythms.model.Zodiac
import alexbrown.x.biorhythms.model.ZodiacBound
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.helper.widget.Carousel
import com.caverock.androidsvg.SVG
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors

class CarouselZodiacTask(private val view: View, private val dateTimeStorage: DateTimeStorage) {

    private val tag = "CarouselZodiacTask"
    private val dateFormat = SimpleDateFormat("dd MMMM")
    private val colors = arrayOf("#f60b6a", "#00f65b", "#f69f05")

    private val zodiacs = arrayOf(
        Zodiac.CAPRICORN,
        Zodiac.AQUARIUS,
        Zodiac.PISCES,
        Zodiac.ARIES,
        Zodiac.TAURUS,
        Zodiac.GEMINI,
        Zodiac.CANCER,
        Zodiac.LEO,
        Zodiac.VIRGO,
        Zodiac.LIBRA,
        Zodiac.SCORPIO,
        Zodiac.SAGITTARIUS
    )

    private val today = GregorianCalendar()

    private val capricorn1Start = GregorianCalendar(today.get(Calendar.YEAR) - 1, Calendar.DECEMBER, 22)
    private val capricorn1End = GregorianCalendar(today.get(Calendar.YEAR), Calendar.JANUARY, 19)

    private val aquariusStart = GregorianCalendar(today.get(Calendar.YEAR), Calendar.JANUARY, 20)
    private val aquariusEnd = GregorianCalendar(today.get(Calendar.YEAR), Calendar.FEBRUARY, 18)

    private val piscesStart = GregorianCalendar(today.get(Calendar.YEAR), Calendar.FEBRUARY, 19)
    private val piscesEnd = GregorianCalendar(today.get(Calendar.YEAR), Calendar.MARCH, 20)

    private val ariesStart = GregorianCalendar(today.get(Calendar.YEAR), Calendar.MARCH, 21)
    private val ariesEnd = GregorianCalendar(today.get(Calendar.YEAR), Calendar.APRIL, 19)

    private val taurusStart = GregorianCalendar(today.get(Calendar.YEAR), Calendar.APRIL, 20)
    private val taurusEnd = GregorianCalendar(today.get(Calendar.YEAR), Calendar.MAY, 20)

    private val geminiStart = GregorianCalendar(today.get(Calendar.YEAR), Calendar.MAY, 21)
    private val geminiEnd = GregorianCalendar(today.get(Calendar.YEAR), Calendar.JUNE, 20)

    private val cancerStart = GregorianCalendar(today.get(Calendar.YEAR), Calendar.JUNE, 21)
    private val cancerEnd = GregorianCalendar(today.get(Calendar.YEAR), Calendar.JULY, 22)

    private val leoStart = GregorianCalendar(today.get(Calendar.YEAR), Calendar.JULY, 23)
    private val leoEnd = GregorianCalendar(today.get(Calendar.YEAR), Calendar.AUGUST, 22)

    private val virgoStart = GregorianCalendar(today.get(Calendar.YEAR), Calendar.AUGUST, 23)
    private val virgoEnd = GregorianCalendar(today.get(Calendar.YEAR), Calendar.SEPTEMBER, 22)

    private val libraStart = GregorianCalendar(today.get(Calendar.YEAR), Calendar.SEPTEMBER, 23)
    private val libraEnd = GregorianCalendar(today.get(Calendar.YEAR), Calendar.OCTOBER, 22)

    private val scorpioStart = GregorianCalendar(today.get(Calendar.YEAR), Calendar.OCTOBER, 23)
    private val scorpioEnd = GregorianCalendar(today.get(Calendar.YEAR), Calendar.NOVEMBER, 21)

    private val sagittariusStart = GregorianCalendar(today.get(Calendar.YEAR), Calendar.NOVEMBER, 22)
    private val sagittariusEnd = GregorianCalendar(today.get(Calendar.YEAR), Calendar.DECEMBER, 21)

    private val capricorn2Start = GregorianCalendar(today.get(Calendar.YEAR) , Calendar.DECEMBER, 22)
    private val capricorn2End = GregorianCalendar(today.get(Calendar.YEAR) + 1, Calendar.JANUARY, 19)

    private val zodiacBounds = arrayOf(
        ZodiacBound(capricorn1Start, capricorn1End, Zodiac.CAPRICORN),
        ZodiacBound(aquariusStart, aquariusEnd, Zodiac.AQUARIUS),
        ZodiacBound(piscesStart, piscesEnd, Zodiac.PISCES),
        ZodiacBound(ariesStart, ariesEnd, Zodiac.ARIES),
        ZodiacBound(taurusStart, taurusEnd, Zodiac.TAURUS),
        ZodiacBound(geminiStart, geminiEnd, Zodiac.GEMINI),
        ZodiacBound(cancerStart, cancerEnd, Zodiac.CANCER),
        ZodiacBound(leoStart, leoEnd, Zodiac.LEO),
        ZodiacBound(virgoStart, virgoEnd, Zodiac.VIRGO),
        ZodiacBound(libraStart, libraEnd, Zodiac.LIBRA),
        ZodiacBound(scorpioStart, scorpioEnd, Zodiac.SCORPIO),
        ZodiacBound(sagittariusStart, sagittariusEnd, Zodiac.SAGITTARIUS),
        ZodiacBound(capricorn2Start, capricorn2End, Zodiac.CAPRICORN)
    )

    init {
        val backgroundExecutor = Executors.newSingleThreadScheduledExecutor()
        val handler = Handler(Looper.getMainLooper())

        backgroundExecutor.execute {
            val carousel = view.findViewById<Carousel>(R.id.carousel)
            val progressBar = view.findViewById<ProgressBar>(R.id.zodiac_progress_bar)
            val zodiacTextView = view.findViewById<TextView>(R.id.textview_zodiac_info)
            val drawables = getDrawables()
            val birthZodiacBound = getBirthZodiacBound()

            handler.post {
                setupCarousel(carousel, drawables)
                carousel.jumpToIndex(zodiacs.indexOf(birthZodiacBound!!.zodiac))
                zodiacTextView.text = "${birthZodiacBound.zodiac.zodiacName}\n${dateFormat.format(birthZodiacBound.startDate.time)} - ${dateFormat.format(birthZodiacBound.endDate.time)}"
                progressBar.visibility = View.INVISIBLE
                carousel.refresh()
            }
        }
    }

    private fun setupCarousel(carousel: Carousel, images: List<Drawable>) {
        carousel.setAdapter(object : Carousel.Adapter {
            override fun count(): Int {
                return zodiacs.size
            }

            override fun populate(view: View, index: Int) {
                if (view is ImageView) {
                    view.setImageDrawable(images[index])
                }
            }

            override fun onNewItem(index: Int) {
                val zodiacBound = getZodiacBoundFromIndex(index)

                val zodiacTextView = view.findViewById<TextView>(R.id.textview_zodiac_info)
                zodiacTextView.text = "${zodiacBound!!.zodiac.zodiacName}\n${dateFormat.format(zodiacBound.startDate.time)} - ${dateFormat.format(zodiacBound.endDate.time)}"
            }
        })
    }

    private fun getZodiacBoundFromIndex(index: Int): ZodiacBound? {
        val zodiac = zodiacs[index]

        for (zodiacBound in zodiacBounds) {
            if (zodiacBound.zodiac == zodiac) {
                return zodiacBound
            }
        }

        return null
    }

    private fun getBirthZodiacBound(): ZodiacBound? {
        val birthDateThisYear = dateTimeStorage.savedDateTime
        birthDateThisYear.set(Calendar.YEAR, today.get(Calendar.YEAR))

        for (zodiacBound in zodiacBounds) {
            if (zodiacBound.startDate.before(birthDateThisYear) && zodiacBound.endDate.after(birthDateThisYear)) {
                return zodiacBound
            }
        }

        return null
    }

    private fun getDrawables(): List<Drawable> {
        return zodiacs.map { zodiac ->
            var lineColor = "#000000"

            if (isDarkMode()) {
                lineColor = "#FFFFFF"
            }

            val modified = zodiac.zodiacSvgString.replace("<lineColor>", lineColor).replace("<fillColor>", colors[(0..2).random()])
            val svg = SVG.getFromString(modified)
            val drawable = PictureDrawable(svg.renderToPicture())

            drawable
        }
    }

    private fun isDarkMode(): Boolean {
        var isDarkMode = false

        when (view.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> isDarkMode = true
            Configuration.UI_MODE_NIGHT_NO -> isDarkMode = false
        }

        return isDarkMode
    }
}


