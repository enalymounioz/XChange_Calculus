package com.casper.currencyconverterfixer.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.core.content.ContextCompat
import android.view.View
import android.widget.Toast
import android.app.AlertDialog
import com.google.android.material.snackbar.Snackbar


class ClassAlertDialog(var context:Context) {



    fun snackBarMsg(view: View, msg :String= "No Internet Connection"){
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).setAction("Action", null).show()
    }

    fun toast(msg:String, duration: Int = Toast.LENGTH_LONG){
        Toast.makeText(context, msg, duration).show()
    }


}