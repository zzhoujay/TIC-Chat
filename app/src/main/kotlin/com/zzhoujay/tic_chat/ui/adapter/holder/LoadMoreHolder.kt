package com.zzhoujay.tic_chat.ui.adapter.holder

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.FrameLayout
import com.zzhoujay.tic_chat.R
import kotlinx.android.synthetic.main.item_load_more.view.*
import org.jetbrains.anko.onClick

/**
 * Created by zhou on 16-3-26.
 */
class LoadMoreHolder(val root: View) : RecyclerView.ViewHolder(root) {

    val cancelAction: Button
    val loadMoreAction: Button
    val layoutLoading: FrameLayout
    val layoutLoadMore: FrameLayout

    var state: State = State.ready
        set(value) {
            when (value) {
                State.loading->{
                    isLoading=true
                }
                State.ready -> {
                    loadMoreAction.setText(R.string.text_click_load_more)
                    loadMoreAction.isClickable = true
                    isLoading=false
                }
                State.nomore -> {
                    loadMoreAction.setText(R.string.text_click_no_more)
                    loadMoreAction.isClickable = false
                    isLoading=false
                }
                State.error -> {
                    loadMoreAction.setText(R.string.text_click_retry)
                    loadMoreAction.isClickable = true
                    isLoading=false
                }
            }
            field = value
            switchLayout()
        }

    var isLoading: Boolean
        get() = layoutLoading.visibility == View.VISIBLE
        set(value) {
            layoutLoading.visibility = if (value) View.VISIBLE else View.INVISIBLE
            layoutLoadMore.visibility = if (value) View.INVISIBLE else View.VISIBLE
        }

    var loadingStateChangeListener: ((state: State) -> Unit)? = null


    init {
        cancelAction = root.cancel_action
        layoutLoadMore = root.layoutLoadMore
        layoutLoading = root.layoutLoading
        loadMoreAction = root.load_more_action

        cancelAction.onClick {
            if (state == State.loading) {
                state = State.ready
            }
            loadingStateChangeListener?.invoke(state)
        }

        loadMoreAction.onClick {
            if (state == State.ready || state == State.error) {
                state = State.loading
            }
            loadingStateChangeListener?.invoke(state)
        }

    }

    fun switchLayout() {
        val showAnim: ObjectAnimator
        val hiddenAnim: ObjectAnimator
        val showView: View
        val hiddenView: View
        if (!isLoading) {
            showView = layoutLoadMore
            hiddenView = layoutLoading
        } else {
            showView = layoutLoading
            hiddenView = layoutLoadMore
        }
        showAnim = ObjectAnimator.ofFloat(showView, "alpha", 0f, 1f)
        hiddenAnim = ObjectAnimator.ofFloat(hiddenView, "alpha", 1f, 0f)
        showAnim.duration = 200
        hiddenAnim.duration = 300
        showAnim.interpolator = AccelerateDecelerateInterpolator()
        hiddenAnim.interpolator = AccelerateDecelerateInterpolator()

        val set = AnimatorSet()
        set.play(hiddenAnim).before(showAnim)

        set.start()
    }

    enum class State {
        loading, ready, nomore, error
    }
}