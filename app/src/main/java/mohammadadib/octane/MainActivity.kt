package mohammadadib.octane

import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.view.ViewTreeObserver
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.doubleclick.PublisherAdRequest
import com.google.android.gms.ads.doubleclick.PublisherAdView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_main.*
import kotlinx.android.synthetic.main.layout_results.*


class MainActivity : AppCompatActivity() {

    lateinit var prefs: SharedPreferences
    lateinit var mPublisherAdView : PublisherAdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup
        setTitle(R.string.app_full_name)
        prefs = PreferenceManager.getDefaultSharedPreferences(this)
        loadPreferences()
        back.setOnClickListener { onBackPressed() }

        // Ads
        MobileAds.initialize(this, getString(R.string.admob_app_id))
        mPublisherAdView = findViewById(R.id.publisherAdView)
        val adRequest = PublisherAdRequest.Builder().build()
        mPublisherAdView.loadAd(adRequest)

        // Fonts
        var font = Typeface.createFromAsset(assets, "fonts/digital-7.ttf")
        gallons.typeface = font
        lowerGallons.typeface = font
        higherGallons.typeface = font

        // ViewFlipper
        viewFlipper.setInAnimation(this, R.anim.slide_up)
        viewFlipper.setOutAnimation(this, R.anim.slide_down)
        calculate.setOnClickListener { calculateResults() }
    }

    private fun setupResults(lower: Int, higher: Int, lowerG: Double, higherG: Double, gallons: Int) {
        lowerOctane.text = lower.toString()
        higherOctane.text = higher.toString()
        lowerGallons.text = String.format("%.2f", lowerG)
        higherGallons.text = String.format("%.2f", higherG)

        // Animation
        resultBar.translationX = 0f
        desiredBar.translationX = 0f
        resultBar.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                var percentLower = lowerG / gallons
                var translation = resultBar.width * percentLower
                resultBar.animate().translationX(translation.toFloat()).setInterpolator(DecelerateInterpolator())
                    .setDuration(500).startDelay = 300
                desiredBar.animate().translationX(translation.toFloat()).setInterpolator(DecelerateInterpolator())
                    .setDuration(500).startDelay = 300
            }
        })
    }

    private fun calculateResults() {
        val a = octaneA.getOctane()
        val b = octaneB.getOctane()
        val g = Integer.parseInt(gallons.text.toString())
        val o = octaneC.getOctane()

        // Sanity check
        if ((o > b && o > a) || (o < a && o < b) || g < 1) {
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show()
            return
        }

        val lower: Double = 1.0 * ((o * g) - (b * g)) / (a - b)
        val higher: Double = g - lower

        setupResults(a, b, lower, higher, g)

        prefs.edit().putInt("a", a).putInt("b", b).putInt("g", g).putInt("o", o).apply()

        viewFlipper.displayedChild = 1
    }

    override fun onBackPressed() {
        if (viewFlipper.displayedChild == 1) {
            viewFlipper.displayedChild = 0
        } else {
            super.onBackPressed()
        }
    }

    private fun loadPreferences() {
        octaneA.setOctane(prefs.getInt("a", 91))
        octaneB.setOctane(prefs.getInt("b", 100))
        gallons.setText(prefs.getInt("g", 10).toString())
        octaneC.setOctane(prefs.getInt("o", 96))
    }
}
