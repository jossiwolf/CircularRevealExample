package de.jw.circularrevealsharedelementexample.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.transition.TransitionInflater
import android.transition.TransitionSet
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AlphaAnimation
import de.jw.circularrevealsharedelementexample.R
import de.jw.circularrevealsharedelementexample.ui.setListener
import kotlinx.android.synthetic.main.activity_scrolling.*


class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Make sure to call this before setContentView
        setTransitions()
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun setTransitions() {
        // Create a new TransitionSet and inflate a transition from the resources
        val inSet = TransitionSet()
        val inflater = TransitionInflater.from(this)
        val transition = inflater
                .inflateTransition(R.transition.arc)

        // Add the inflated transition to the set and set duration and interpolator
        inSet.apply {
            addTransition(transition)
            duration = 380
            // The shared element should not move in linear motion
            interpolator = AccelerateDecelerateInterpolator()
            // Set a listener to create a circular reveal when the transition has ended
            setListener {
                onTransitionEnd {
                    reveal()
                }
            }
        }

        val outSet = TransitionSet()
                .apply {
                    addTransition(transition)
                    duration = 380
                    interpolator = AccelerateDecelerateInterpolator()
                    startDelay = 200
                }

        // Set the shared element transitions of the activity
        window.sharedElementEnterTransition = inSet
        window.sharedElementExitTransition = outSet
        window.sharedElementReturnTransition = outSet
    }

    fun reveal() {
        collapsingToolbarBackground.setBackgroundColor(Color.CYAN)

        val center = roundedImage.getCenter()
        // create the animator for this view (the start radius is zero)
        val anim = ViewAnimationUtils.createCircularReveal(collapsingToolbarBackground, center.first.toInt(),
                center.second.toInt(), 0f, collapsingToolbarBackground.width.toFloat())
                .apply {
                    duration = 400
                }

        // make the view visible and start the animation
        collapsingToolbarBackground.visibility = View.VISIBLE
        anim.start()
    }

    fun hideView() {
        val center = roundedImage.getCenter()
        // create the animator for this view (the end radius is zero)
        ViewAnimationUtils.createCircularReveal(collapsingToolbarBackground, center.first.toInt(),
                center.second.toInt(), collapsingToolbarBackground.width.toFloat(), 0f)
                .apply {
                    duration = 400
                    addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            collapsingToolbarBackground.visibility = View.INVISIBLE
                        }
                    })
                    start()
                }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        hideView()
    }

    /**
     * Extension function to get the center of a view
     * @return Pair containing (x|y)
     */
    fun View.getCenter(): Pair<Float, Float> {
        val cx = this.x + this.width / 2
        val cy = this.y + this.height / 2
        return Pair(cx, cy)
    }

}
