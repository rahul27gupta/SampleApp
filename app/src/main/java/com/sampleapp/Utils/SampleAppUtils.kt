package com.sampleapp.Utils

import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

object SampleAppUtils {

    /**
     * Animates the result screen elements with a staggered entrance effect
     */
    fun animateResultScreen(
        mainContainer: View,
        congratsView: View,
        resultsContainer: View,
        restartButton: View,
        finishButton: View
    ) {
        // Fade in main container
        mainContainer.alpha = 0f
        mainContainer.translationY = 50f
        mainContainer.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(500)
            .start()

        // Animate congratulations message
        congratsView.alpha = 0f
        congratsView.scaleX = 0.8f
        congratsView.scaleY = 0.8f
        congratsView.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(600)
            .setStartDelay(200)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()

        // Animate results container
        resultsContainer.alpha = 0f
        resultsContainer.translationY = 30f
        resultsContainer.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(400)
            .setStartDelay(500)
            .start()

        // Animate restart button
        restartButton.alpha = 0f
        restartButton.animate()
            .alpha(1f)
            .setDuration(400)
            .setStartDelay(800)
            .start()

        // Animate finish button
        finishButton.alpha = 0f
        finishButton.animate()
            .alpha(1f)
            .setDuration(400)
            .setStartDelay(1000)
            .start()
    }
}