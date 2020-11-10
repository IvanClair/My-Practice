package ivan.personal.core

import android.os.SystemClock
import android.view.View

// region Safe Click

/**
 * The listener use for avoid quick click on an UI widget
 *
 * @property expectedInterval the time interval you set to prevent multiple click
 * @property onClick          callback when clicked
 */
class SafeOnClickListener(
    private val expectedInterval: Int = 500,
    private val onClick: (View?) -> Unit
) : View.OnClickListener {

    // unix time of last clicked
    private var lastTimeClicked: Long = 0

    /**
     * Override click event
     */
    override fun onClick(p0: View?) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < expectedInterval) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        onClick.invoke(p0)
    }

}

/**
 * Convenient extension for [SafeOnClickListener]
 */
infix fun View.setSafeOnClickListener(onClick: (View?) -> Unit) {
    val safeClickListener = SafeOnClickListener { onClick.invoke(it) }
    setOnClickListener(safeClickListener)
}