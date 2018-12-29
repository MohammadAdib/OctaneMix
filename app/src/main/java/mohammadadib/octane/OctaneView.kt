package mohammadadib.octane

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class OctaneView : FrameLayout {

    private var octane: EditText? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.view_octane, this, true)
        octane = findViewById(R.id.input)

        if(id == R.id.octaneA) {
            setOctane(91)
            setTitle(R.string.minimum_octane_rating)
            setLight(R.drawable.green_light)
        } else if (id == R.id.octaneB) {
            setOctane(100)
            setTitle(R.string.maximum_octane_rating)
            setLight(R.drawable.red_light)
        } else if (id == R.id.octaneC) {
            setOctane(96)
            setTitle(R.string.desired_octane_rating)
            setLight(R.drawable.orange_light)
        }
    }

    fun setOctane(rating: Int) {
        octane!!.setText(Integer.toString(rating))
    }

    fun setLight(res: Int) {
        findViewById<View>(R.id.light).setBackgroundResource(res)
    }

    fun setTitle(res: Int) {
        findViewById<TextView>(R.id.title).setText(res)
    }

    fun getOctane(): Int {
        return Integer.valueOf(octane!!.text.toString())
    }
}
