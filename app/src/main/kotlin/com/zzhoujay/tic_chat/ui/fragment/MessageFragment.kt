package com.zzhoujay.tic_chat.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.ui.adapter.MessageAdapter
import kotlinx.android.synthetic.main.layout_recycler_view.*

/**
 * Created by zhou on 16-3-26.
 */
class MessageFragment : BaseFragment() {

    val messageAdapter: MessageAdapter by lazy { MessageAdapter(20) }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.layout_recycler_view, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = messageAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerView.adapter = null
    }


}