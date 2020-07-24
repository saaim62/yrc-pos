package com.yrc.pos.core.providers

import android.annotation.SuppressLint
import android.content.Context
import com.kaopiz.kprogresshud.KProgressHUD

object ProgressDialogProvider {

    @SuppressLint("StaticFieldLeak")
    private lateinit var progressHud: KProgressHUD

    fun show(context: Context) {
        try {
            progressHud = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show()
        }
        catch (e : Exception){
            e.printStackTrace()
        }
    }

    fun dismiss() {
        if(progressHud != null) { progressHud.dismiss() }
    }
}