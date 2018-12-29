package mohammadadib.octane

import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_results.*
import android.opengl.ETC1.getHeight
import android.view.ViewTreeObserver
import android.view.animation.DecelerateInterpolator


class ResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)
        setTitle(R.string.app_full_name)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val lower = intent.getIntExtra("lower", 0)
        val higher = intent.getIntExtra("higher", 0)
        val lowerG = intent.getDoubleExtra("lowerG", 0.0)
        val higherG = intent.getDoubleExtra("higherG", 0.0)
        val gallons = intent.getIntExtra("gallons", 0)

        var font = Typeface.createFromAsset(getAssets(), "fonts/digital.ttf")
        lowerGallons.typeface = font
        higherGallons.typeface = font

        lowerOctane.text = lower.toString()
        higherOctane.text = higher.toString()

        lowerGallons.text = String.format("%.2f", lowerG)
        higherGallons.text = String.format("%.2f", higherG)

        resultBar.getViewTreeObserver().addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                var percentLower = lowerG / gallons
                var translation = resultBar.width * percentLower
                resultBar.animate().translationX(translation.toFloat()).setInterpolator(DecelerateInterpolator()).setDuration(500)
            }
        })
    }
}