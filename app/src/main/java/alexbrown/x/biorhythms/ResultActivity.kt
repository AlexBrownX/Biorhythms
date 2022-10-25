package alexbrown.x.biorhythms

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAStyle

class ResultActivity : AppCompatActivity() {

    private lateinit var dateTimeStorage: DateTimeStorage
    private lateinit var biorhythmCalculator: BiorhythmCalculator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        dateTimeStorage = DateTimeStorage(baseContext)
        biorhythmCalculator = BiorhythmCalculator(dateTimeStorage)

        val chartView = findViewById<AAChartView>(R.id.chart_view)
        val chartModel: AAChartModel = getChartModel()

        chartView.aa_drawChartWithChartModel(chartModel)
    }

    private fun getChartModel(): AAChartModel {
        val results = biorhythmCalculator.calculate()
        val chartTheme = getChartStyle()

        return AAChartModel()
            .chartType(AAChartType.Column)
            .title("Your Biorhythms")
            .axesTextColor(chartTheme.color.toString())
            .titleStyle(chartTheme)
            .dataLabelsStyle(chartTheme)
            .backgroundColor(chartTheme.backgroundColor.toString())
            .dataLabelsEnabled(true)
            .xAxisLabelsEnabled(false)
            .tooltipEnabled(false)
            .series(
                arrayOf(
                    AASeriesElement()
                        .name("Physical")
                        .data(arrayOf(results.physicalScore))
                        .color("#f60b6a")
                        .borderWidth(1)
                        .borderColor(chartTheme.color.toString()),
                    AASeriesElement()
                        .name("Emotional")
                        .data(arrayOf(results.emotionalScore))
                        .color("#00f65b")
                        .borderWidth(1)
                        .borderColor(chartTheme.color.toString()),
                    AASeriesElement()
                        .name("Intellectual")
                        .data(arrayOf(results.intellectualScore))
                        .color("#f69f05")
                        .borderWidth(1)
                        .borderColor(chartTheme.color.toString())
                    )
            )
    }

    private fun getChartStyle(): AAStyle {
        return if (isDarkMode()) {
            AAStyle()
                .backgroundColor("#000000")
                .color("#FFFFFF")
        } else {
            AAStyle()
                .backgroundColor("#FFFFFF")
                .color("#000000")
        }
    }

    private fun isDarkMode(): Boolean {
        var isDarkMode = false

        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> isDarkMode = true
            Configuration.UI_MODE_NIGHT_NO -> isDarkMode = false
        }

        return isDarkMode
    }
}
