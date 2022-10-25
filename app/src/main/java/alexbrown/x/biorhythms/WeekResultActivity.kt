package alexbrown.x.biorhythms

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAPlotLinesElement
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAStyle
import java.text.SimpleDateFormat
import java.util.*

class WeekResultActivity : AppCompatActivity() {

    private val chartLabelDateFormatter = SimpleDateFormat("dd MMM")

    private lateinit var dateTimeStorage: DateTimeStorage
    private lateinit var biorhythmCalculator: BiorhythmCalculator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_week_result)

        dateTimeStorage = DateTimeStorage(baseContext)
        biorhythmCalculator = BiorhythmCalculator()

        val sizeOffset = 3
        val startDate = dateTimeStorage.savedDateTime
        val endDate = Date()
        val results = biorhythmCalculator.calculate(startDate, endDate, sizeOffset)

        val chartView = findViewById<AAChartView>(R.id.weekly_chart_view)
        val chartTheme = getChartStyle()
        val chartModel = getChartModel(chartTheme, results)
        val chartOptions = getChartOptions(chartModel, chartTheme, results)

        chartView.aa_drawChartWithChartOptions(chartOptions)
    }

    private fun getChartOptions(chartModel: AAChartModel, chartTheme: AAStyle, results: Collection<CalculationResults>): AAOptions {
        val xAxisPlotLinesArray = arrayOf(AAPlotLinesElement()
            .color(chartTheme.color.toString())
            .dashStyle(AAChartLineDashStyleType.Solid)
            .width(2)
            .value(results.size/2)
            .zIndex(1)
        )

        val aaOptions = chartModel.aa_toAAOptions()
        aaOptions.xAxis?.plotLines(xAxisPlotLinesArray)

        return aaOptions
    }

    private fun getChartModel(chartTheme: AAStyle, results: Collection<CalculationResults>): AAChartModel {
        return AAChartModel()
            .chartType(AAChartType.Spline)
            .title("Weekly Biorhythms")
            .axesTextColor(chartTheme.color.toString())
            .titleStyle(chartTheme)
            .dataLabelsStyle(chartTheme)
            .backgroundColor(chartTheme.backgroundColor.toString())
            .dataLabelsEnabled(false)
            .tooltipEnabled(false)
            .xAxisGridLineWidth(0.5)
            .yAxisGridLineWidth(0.5)
            .yAxisMax(1)
            .yAxisMin(-1)
            .categories(results.map { calculationResults -> chartLabelDateFormatter.format(calculationResults.endDate) }.toTypedArray())
            .series(
                arrayOf(
                    AASeriesElement()
                        .name("Physical")
                        .data(results.map { calculationResults -> calculationResults.physicalScore }.toTypedArray())
                        .color("#f60b6a")
                        .borderWidth(1)
                        .borderColor(chartTheme.color.toString()),
                    AASeriesElement()
                        .name("Emotional")
                        .data(results.map { calculationResults -> calculationResults.emotionalScore }.toTypedArray())
                        .color("#00f65b")
                        .borderWidth(1)
                        .borderColor(chartTheme.color.toString()),
                    AASeriesElement()
                        .name("Intellectual")
                        .data(results.map { calculationResults -> calculationResults.intellectualScore }.toTypedArray())
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