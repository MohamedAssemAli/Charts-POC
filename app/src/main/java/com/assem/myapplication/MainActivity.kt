package com.assem.myapplication

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class MainActivity : AppCompatActivity() {


    private lateinit var barChart: BarChart

    private var scoreList = ArrayList<Score>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        barChart = findViewById(R.id.barChart)

        scoreList = getScoreList()

        initBarChart()


        //now draw bar chart with dynamic data
        val entries: ArrayList<BarEntry> = ArrayList()

        //you can replace this data object with  your custom object
        for (i in scoreList.indices) {
            val score = scoreList[i]
            entries.add(BarEntry(i.toFloat(), score.score.toFloat()))
        }

        val barDataSet = BarDataSet(entries, "")

        barDataSet.setColors(
            ContextCompat.getColor(this, R.color.pending),
            ContextCompat.getColor(this, R.color.rejected),
            ContextCompat.getColor(this, R.color.in_progress),
            ContextCompat.getColor(this, R.color.complete),
        );

        val data = BarData(barDataSet)
        // remove highlight
        data.isHighlightEnabled = false
        data.setValueTextSize(10f)

        // set barChart data
        barChart.data = data

        barChart.invalidate()
    }

    private fun initBarChart() {


//        hide grid lines
        barChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = barChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        barChart.axisRight.isEnabled = false

        //remove legend
        barChart.legend.isEnabled = false


        //remove description label
        barChart.description.isEnabled = false


        //add animation
        barChart.animateY(3000)

        // to draw label on xAxis
        xAxis.position = XAxis.XAxisPosition.TOP
        xAxis.valueFormatter = MyAxisFormatter()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.typeface = Typeface.createFromAsset(this.assets, "bold_font.ttf")
        xAxis.textSize = 10f

        // handle YAxis
        val leftYAxis: YAxis = barChart.axisLeft
        leftYAxis.isEnabled = false

    }


    inner class MyAxisFormatter : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            Log.d("Asssem", "getAxisLabel: index $index")
            return if (index < scoreList.size) {
                scoreList[index].name
            } else {
                ""
            }
        }
    }


    // simulate api call
    // we are initialising it directly
    private fun getScoreList(): ArrayList<Score> {
        scoreList.add(Score("Pending", 100))
        scoreList.add(Score("Rejected", 50))
        scoreList.add(Score("In progress", 30))
        scoreList.add(Score("Complete", 20))

        return scoreList
    }
}