package mohammadadib.octane

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.logging.Logger
import android.preference.PreferenceManager
import com.google.android.gms.ads.MobileAds


class MainActivity : AppCompatActivity() {

    lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle(R.string.app_full_name)
        prefs = PreferenceManager.getDefaultSharedPreferences(this)
        loadPreferences()

        gallons.typeface = Typeface.createFromAsset(getAssets(), "fonts/digital-7.ttf")

        calculate.setOnClickListener { calculateResults() }
    }

    private fun calculateResults() {
        var a = octaneA.getOctane()
        var b = octaneB.getOctane()
        var g = Integer.parseInt(gallons.text.toString())
        var o = octaneC.getOctane()

        // Sanity check
        if((o > b && o > a) || (o < a && o < b) || g < 1) {
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show()
            return
        }

        var lower: Double = 1.0 * ((o * g) - (b * g)) / (a - b)
        var higher: Double = g - lower

        var intent = Intent(this, ResultsActivity::class.java)
        intent.putExtra("lower", a)
        intent.putExtra("higher", b)
        intent.putExtra("lowerG", lower)
        intent.putExtra("higherG", higher)
        intent.putExtra("gallons", g)
        startActivity(intent)

        prefs.edit().putInt("a", a).putInt("b", b).putInt("g", g).putInt("o", o).apply()
    }

    private fun loadPreferences() {
        octaneA.setOctane(prefs.getInt("a", 91))
        octaneB.setOctane(prefs.getInt("b", 100))
        gallons.setText(prefs.getInt("g", 10).toString())
        octaneC.setOctane(prefs.getInt("o", 96))
    }
}
