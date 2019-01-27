package gjg.ctd.com.stopwatch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    private var time = 0
    private var timerTask: Timer? = null
    private var isRunning = false
    private var lap = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fab.setOnClickListener {
            isRunning = !isRunning

            if(isRunning){
                start()
            }
            else{
                pause()
            }
        }
        resetFab.setOnClickListener {
            reset()
        }
        lapBtn.setOnClickListener {
            recodeLapTime()
        }
    }

    private fun start(){
        fab.setImageResource(R.drawable.ic_pause_black_24dp)
        timerTask = timer(period = 10) {
            time++
            val sec = time / 100
            val mill = time % 100
            runOnUiThread {
                secTextView.text = "$sec"
                millTextView.text = "$mill"
            }
        }
    }
    private fun pause(){
        fab.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        timerTask?.cancel()
    }

    private fun reset(){
        timerTask?.cancel()
        time = 0
        isRunning = false
        fab.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        secTextView.text = "0"
        millTextView.text = "0"

        lapLayout.removeAllViews()
        lap = 1
    }

    private fun recodeLapTime(){
        val lapTime = this.time
        val textView = TextView(this)
        textView.text = " $lap LAB : ${lapTime/100}.${lapTime % 100}"
        lapLayout.addView(textView, 0)
        lap++
    }
}
