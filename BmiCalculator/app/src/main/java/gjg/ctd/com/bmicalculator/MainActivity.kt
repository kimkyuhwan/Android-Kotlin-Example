package gjg.ctd.com.bmicalculator

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadData()
        resultBtn.setOnClickListener {
            /*
            val intent = Intent(this,ResultActivity::class.java)
            intent.putExtra("weight",editTextWeight.text.toString())
            intent.putExtra("height",editTextHeight.text.toString())
            startActivity(intent)*/
            saveData(editTextHeight.text.toString().toInt(),
                editTextWeight.text.toString().toInt())
            startActivity<ResultActivity>(
                "weight" to editTextWeight.text.toString(),
                "height" to editTextHeight.text.toString()
            )
        }
    }

    private fun saveData(height : Int, weight : Int){
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()

        editor
            .putInt("KEY_HEIGHT",height)
            .putInt("KEY_WEIGHT",weight)
            .apply()
    }
    private fun loadData(){
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val height = pref.getInt("KEY_HEIGHT",0)
        val weight = pref.getInt("KEY_WEIGHT",0)
        if(height !=0 && weight !=0 ){
            editTextHeight.setText(height.toString())
            editTextWeight.setText(weight.toString())
        }
    }
}
