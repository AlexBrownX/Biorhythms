package alexbrown.x.biorhythms.biorhythm

import alexbrown.x.biorhythms.R
import alexbrown.x.biorhythms.utils.DateTimeStorage
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAPlotLinesElement
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAStyle
import java.text.SimpleDateFormat
import java.util.*

class WeeklyResultFragment : Fragment() {

    private val chartLabelDateFormatter = SimpleDateFormat("dd MMM")

    private lateinit var dateTimeStorage: DateTimeStorage
    private lateinit var biorhythmCalculator: BiorhythmCalculator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dateTimeStorage = arguments?.getSerializable("DateTimeStorage") as DateTimeStorage
        biorhythmCalculator = arguments?.getSerializable("BiorhythmCalculator") as BiorhythmCalculator
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weekly_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sizeOffset = 3
        val startDate = dateTimeStorage.savedDateTime.time
        val endDate = Date()
        val results = biorhythmCalculator.calculate(startDate, endDate, sizeOffset)

        val chartView = view.findViewById<AAChartView>(R.id.weekly_chart_view)
        val chartTheme = getChartStyle()
        val chartModel = getChartModel(chartTheme, results)
        val chartOptions = getChartOptions(chartModel, chartTheme, results)

        chartView.aa_drawChartWithChartOptions(chartOptions)
    }

    private fun getChartOptions(chartModel: AAChartModel, chartTheme: AAStyle, results: Collection<CalculationResults>): AAOptions {
        val xAxisPlotLinesArray = arrayOf(
            AAPlotLinesElement()
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