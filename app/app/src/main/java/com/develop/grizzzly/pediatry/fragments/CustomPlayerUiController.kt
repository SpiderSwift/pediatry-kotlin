package com.develop.grizzzly.pediatry.fragments

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.android.synthetic.main.player_ui.view.*

internal class CustomPlayerUiController(
    private val context: Context,
    private val playerUi: View,
    private val youTubePlayer: YouTubePlayer,
    private val youTubePlayerView: YouTubePlayerView
) : AbstractYouTubePlayerListener(), YouTubePlayerFullScreenListener {

    private var panel: View? = null

    private val playerTracker: YouTubePlayerTracker = YouTubePlayerTracker()

    init {
        youTubePlayer.addListener(playerTracker)
        initViews(playerUi)
    }

    private fun initViews(playerUi: View) {
        panel = playerUi.panel
    }

    override fun onReady(youTubePlayer: YouTubePlayer) {
    }

    override fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerConstants.PlayerState) {
        if (state == PlayerConstants.PlayerState.PLAYING || state == PlayerConstants.PlayerState.PAUSED || state == PlayerConstants.PlayerState.VIDEO_CUED)
            panel!!.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
        else if (state == PlayerConstants.PlayerState.BUFFERING)
            panel!!.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
    }

    override fun onYouTubePlayerEnterFullScreen() {
        val viewParams = playerUi.layoutParams
        viewParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        viewParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        playerUi.layoutParams = viewParams
    }

    override fun onYouTubePlayerExitFullScreen() {
        val viewParams = playerUi.layoutParams
        viewParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        viewParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        playerUi.layoutParams = viewParams
    }
}