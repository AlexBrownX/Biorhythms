package alexbrown.x.biorhythms.fragments

import alexbrown.x.biorhythms.R
import alexbrown.x.biorhythms.model.CalculationResults
import alexbrown.x.biorhythms.utils.BiorhythmCalculator
import alexbrown.x.biorhythms.utils.DateTimeStorage
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBindings
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAPlotLinesElement
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAStyle
import java.util.*

class DailyResultFragment : Fragment() {

    private lateinit var dateTimeStorage: DateTimeStorage
    private lateinit var biorhythmCalculator: BiorhythmCalculator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dateTimeStorage = DateTimeStorage(requireContext())
        biorhythmCalculator = BiorhythmCalculator()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_daily_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val startDate = dateTimeStorage.savedDateTime.time
        val endDate = Date()
        val results = biorhythmCalculator.calculate(startDate, endDate)

        val chartView = view.findViewById<AAChartView>(R.id.daily_chart_view)
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