package com.lzl.wanxiyouapp.View.ViewInterface

import android.graphics.Bitmap

/**
 * Created by LZL on 2017/8/1.
 */

interface LogonActivityInterface{
    fun getVerificationSuccess(image: Bitmap,cookie:String): Unit

    fun requestError(msg: String): Unit

    fun showProgressDialog():Unit

    fun dismissProgressDialog()

    fun onLogonSuccess()
}
