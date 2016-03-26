package com.zzhoujay.tic_chat.ui.adapter.holder

import android.support.v7.widget.AppCompatSpinner
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.item_spinner.view.*

/**
 * Created by zhou on 16-3-26.
 */
class SpinnerHolder(val root: View) : RecyclerView.ViewHolder(root) {
    val spinner: AppCompatSpinner

    init {
        spinner = root.spinner
    }
}