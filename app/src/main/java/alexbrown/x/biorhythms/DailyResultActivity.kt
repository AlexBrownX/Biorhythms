package alexbrown.x.biorhythms

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAPlotLinesElement
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAStyle
import java.util.*

class DailyResultActivity : AppCompatActivity() {

    private lateinit var dateTimeStorage: DateTimeStorage
    private lateinit var biorhythmCalculator: BiorhythmCalculator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_result)

        dateTimeStorage = DateTimeStorage(baseContext)
        biorhythmCalculator = BiorhythmCalculator()

        val startDate = dateTimeStorage.savedDateTime.time
        val endDate = Date()
        val results = biorhythmCalculator.calculate(startDate, endDate)

        val chartView = findViewById<AAChartView>(R.id.daily_chart_view)
        val chartTheme = getChartStyle()
        val chartModel = getChartModel(chartTheme, results)
        val chartOptions = getChartOptions(chartModel, chartTheme)

        chartView.aa_drawChartWithChartOptions(chartOptions)
    }

    private fun getChartOptions(chartModel: AAChartModel, chartTheme: AAStyle): AAOptions {
        val yAxisPlotLinesArray = arrayOf(
            AAPlotLinesElement()
            .color(chartTheme.color.toString())
            .dashStyle(AAChartLineDashStyleType.Solid)
            .width(2)
            .value(0)
            .zIndex(1)
        )

        val aaOptions = chartModel.aa_toAAOptions()
        aaOptions.yAxis?.plotLines(yAxisPlotLinesArray)

        return aaOptions
    }

    private fun getChartModel(chartTheme: AAStyle, results: CalculationResults): AAChartModel {
        return AAChartModel()
            .chartType(AAChartType.Column)
            .title("Daily Biorhythms")
            .axesTextColor(chartTheme.color.toString())
            .titleStyle(chartTheme)
            .dataLabelsStyle(chartTheme)
            .backgroundColor(chartTheme.backgroundColor.toString())
            .dataLabelsEnabled(true)
            .xAxisLabelsEnabled(false)
            .tooltipEnabled(false)
            .xAxisGridLineWidth(0.5)
            .yAxisGridLineWidth(0.5)
            .yAxisMax(1)
            .yAxisMin(-1)
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
