package com.zzhoujay.tic_chat.ui.adapter.holder

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.FrameLayout
import com.zzhoujay.tic_chat.util.SimpleAnimatorListener
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
    var isLoading: Boolean
        get() = layoutLoading.visibility == View.VISIBLE
        set(value) {
            layoutLoading.visibility = if (value) View.VISIBLE else View.INVISIBLE
            layoutLoadMore.visibility = if (value) View.INVISIBLE else View.VISIBLE
        }

    var loadingStateChangeListener: ((isLoading: Boolean) -> Unit)? = null

    val switchAnimatorListener: SimpleAnimatorListener by lazy {
        object : SimpleAnimatorListener() {
            override fun onAnimationEnd(animation: Animator?) {
                isLoading = !isLoading
            }
        }
    }

    init {
        cancelAction = root.cancel_action
        layoutLoadMore = root.layoutLoadMore
        layoutLoading = root.layoutLoading
        loadMoreAction = root.load_more_action

        cancelAction.onClick {
            switchLayout()
            loadingStateChangeListener?.invoke(isLoading)
        }

        loadMoreAction.onClick {
            switchLayout()
            loadingStateChangeListener?.invoke(isLoading)
        }

    }

    fun switchLayout() {
        val showAnim: ObjectAnimator
        val hiddenAnim: ObjectAnimator
        val showView: View
        val hiddenView: View
        if (isLoading) {
            showView = layoutLoadMore
            hiddenView = layoutLoading
        } else {
            showView = layoutLoading
            hiddenView = layoutLoadMore
        }
        showAnim = ObjectAnimator.ofFloat(showView, "alpha", 0f, 1f)
        hiddenAnim = ObjectAnimator.ofFloat(hiddenView, "alpha", 1f, 0f)
        showAnim.duration = 500
        hiddenAnim.duration = 500
        showAnim.interpolator = AccelerateDecelerateInterpolator()
        hiddenAnim.interpolator = AccelerateDecelerateInterpolator()

        hiddenAnim.addListener(switchAnimatorListener)

        val set = AnimatorSet()
        set.play(hiddenAnim).before(showAnim)

        set.start()
    }
}