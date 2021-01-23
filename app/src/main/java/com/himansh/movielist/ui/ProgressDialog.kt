package com.himansh.movielist.ui

import android.app.ProgressDialog
import android.content.Context

object ProgressDialog {
    private lateinit var pDialog: ProgressDialog

    fun initialise(context: Context) {
        pDialog = ProgressDialog(context)
        pDialog.setMessage("Loading...")
    }

    fun show() {
        pDialog.show()
    }

    fun dismiss() {
        pDialog.dismiss()
    }

}