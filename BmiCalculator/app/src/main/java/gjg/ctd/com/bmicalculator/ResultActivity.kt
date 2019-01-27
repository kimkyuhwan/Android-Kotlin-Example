package gjg.ctd.com.bmicalculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_result.*
import org.jetbrains.anko.toast

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val height = intent.getStringExtra("height").toInt()
        val weight = intent.getStringExtra("weight").toInt()

        val bmi = weight / Math.pow(height / 100.0, 2.0)
        var result = ""
        when {
            bmi >= 35 -> result = "고도비만"
            bmi >= 30 -> result = "2단계 비만"
            bmi >= 25 -> result = "1단계 비만"
            bmi >= 23 -> result = "과제중"
            bmi >= 18.5 -> result = "정상"
            else -> result= "저체중"
        }
        resultTextView.text = result

        when {
            bmi >= 23 -> resultImage.setImageResource(R.drawable.ic_sentiment_very_dissatisfied_black_24dp)
            bmi >= 18.5 -> resultImage.setImageResource(R.drawable.ic_sentiment_satisfied_black_24dp)
            else -> resultImage.setImageResource(R.drawable.ic_sentiment_dissatisfied_black_24dp)
        }
        toast("bmi value = $bmi")

    }
}
