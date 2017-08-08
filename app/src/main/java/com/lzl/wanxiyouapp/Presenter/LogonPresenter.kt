package com.lzl.wanxiyouapp.Presenter

import android.content.Context
import android.graphics.Bitmap
import com.lzl.wanxiyouapp.Moudle.LogonMoudle
import com.lzl.wanxiyouapp.Presenter.PresenterInterface.ILogonPresenter
import com.lzl.wanxiyouapp.View.ViewInterface.LogonActivityInterface

/**
 * Created by LZL on 2017/8/8.
 */
class LogonPresenter(val context: Context,val view:LogonActivityInterface):ILogonPresenter{
    val logonMoudle = LogonMoudle(context,this)

    override fun Logon(id: String?, pass: String?, cookie: String?, code: String?) {
        view.showProgressDialog()
        logonMoudle.Logon(id,pass,cookie,code)
    }

    override fun LogonWithoutCode(id: String?, pass: String?) {
        view.showProgressDialog()
        logonMoudle.LogonWithoutCode(id,pass)
    }

    override fun onLogonSuccess() {
        view.dismissProgressDialog()
        view.onLogonSuccess()
    }

    override fun onLogonError(error: String?) {
        view.dismissProgressDialog()
        view.requestError(error!!)
    }

    override fun onGetVerificationSuccess(bitmap: Bitmap?, msg: String?) {
        view.getVerificationSuccess(bitmap!!,msg!!)
    }

    override fun getVerificationImage() {
        logonMoudle.getVerificationImage()
    }
}