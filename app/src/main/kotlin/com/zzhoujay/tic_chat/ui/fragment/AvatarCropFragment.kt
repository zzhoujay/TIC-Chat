package com.zzhoujay.tic_chat.ui.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.zzhoujay.tic_chat.App
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.common.Configuration
import com.zzhoujay.tic_chat.ui.activity.AvatarCropActivity
import com.zzhoujay.tic_chat.util.BitmapKit
import com.zzhoujay.tic_chat.util.NumKit
import com.zzhoujay.tic_chat.util.progress
import kotlinx.android.synthetic.main.fragment_avatar_crop.*
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread
import java.io.File

/**
 * Created by zhou on 16-4-11.
 */
class AvatarCropFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_avatar_crop, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val uri = arguments.getParcelable<Uri>(IMAGE_URI)
        Glide.with(this).load(uri).asBitmap().into(target)
    }

    val target: SimpleTarget<Bitmap> by lazy {
        object : SimpleTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap?, glideAnimation: GlideAnimation<in Bitmap>?) {
                cropImageView.imageBitmap = resource
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.action_avatar_crop, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_done) {
            progress(false, getString(R.string.alert_please_wait)) {
                async() {
                    val file = File(App.app.cacheDir, "${Configuration.Temp.avatar_temp}${NumKit.randomNums(5)}.jpg")
                    BitmapKit.saveBitmapToLocal(file, cropImageView.rectBitmap)
                    val uri = Uri.fromFile(file)
                    uiThread {
                        dismiss()
                        notice<AvatarCropActivity> {
                            val intent = Intent()
                            intent.data = uri
                            setResult(Activity.RESULT_OK, intent)
                            finish()
                        }
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val IMAGE_URI = "image_uri"
    }


}