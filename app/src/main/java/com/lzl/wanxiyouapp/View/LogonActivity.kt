package com.lzl.wanxiyouapp.View

import android.app.Activity
import android.app.ProgressDialog
import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import butterknife.ButterKnife
import butterknife.OnClick
import com.lzl.wanxiyouapp.Presenter.LogonPresenter
import com.lzl.wanxiyouapp.Presenter.PresenterInterface.ILogonPresenter

import com.lzl.wanxiyouapp.R
import com.lzl.wanxiyouapp.View.ViewInterface.LogonActivityInterface
import kotlinx.android.synthetic.main.activity_logon.*
import org.jetbrains.anko.startActivity

/**
 * Created by LZL on 2017/8/1.
 */

class LogonActivity : AppCompatActivity(),LogonActivityInterface,View.OnClickListener {
    var progressDialog:ProgressDialog? = null
    var cookie:String = ""
    val presenter:ILogonPresenter by lazy {
        LogonPresenter(this,this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logon)
        presenter.getVerificationImage()
        logon_layout_image_verification.setOnClickListener(this)
        logon_layout_logon_btn.setOnClickListener(this)
        logon_layout_escape_logon_btn.setOnClickListener(this)
    }

    override fun getVerificationSuccess(image: Bitmap,cookie:String) {
        logon_layout_image_verification.setImageBitmap(image)
        this.cookie = cookie
    }

    override fun requestError(msg: String) {
        showSnackBar(msg)
    }

    override fun showProgressDialog() {
        if (progressDialog==null){
            progressDialog = ProgressDialog.show(this,"","正在登录请稍后",false,false)
        }
        else
            progressDialog?.show()
    }

    override fun dismissProgressDialog() {
        progressDialog?.dismiss()
    }

    override fun onLogonSuccess() {
        startActivity<MainMenuActivity>("isLogon" to true)
        finish()
    }


    override fun onClick(view: View) {
        println("onclick")
        when (view.id) {
            R.id.logon_layout_image_verification -> {
                presenter.getVerificationImage()
            }
            R.id.logon_layout_logon_btn -> {
                val id = logon_layout_id.text.toString()
                val password = logon_layout_password.text.toString()
                val verification = logon_layout_verification.text.toString()
                if(!AUTO_LOGON_MODE)
                {
                    presenter.Logon(id,password,cookie,verification)
                }
                else{
                    presenter.LogonWithoutCode(id,password)
                }
            }
            R.id.logon_layout_escape_logon_btn ->{
                startActivity<MainMenuActivity>("isLogon" to false)
                finish()
            }
        }
    }

    companion object {
        var AUTO_LOGON_MODE = false
    }
}

fun Activity.showSnackBar(msg:String){
    Snackbar.make(this.currentFocus,msg,Snackbar.LENGTH_SHORT).show()
}

