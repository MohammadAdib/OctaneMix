package mohammadadib.octane

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.logging.Logger


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle(R.string.app_full_name)

        gallons.typeface = Typeface.createFromAsset(getAssets(), "fonts/digital.ttf")

        calculate.setOnClickListener { calculateResults() }
    }

    private fun calculateResults() {
        var a = octaneA.getOctane()
        var b = octaneB.getOctane()
        var g = Integer.parseInt(gallons.text.toString())
        var o = octaneC.getOctane()

        // Sanity check
        if((o >= b && o >= a) || (o <= a && o <= b)) {
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
    }
}
