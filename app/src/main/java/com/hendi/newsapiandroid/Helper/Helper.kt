@file:Suppress("DEPRECATION")

package com.hendi.newsapiandroid.Helper

import android.app.ActionBar
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.widget.Toast
import com.hendi.newsapiandroid.R
import es.dmoral.toasty.Toasty
import java.text.SimpleDateFormat
import java.util.*

private val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
private val outputFormat = SimpleDateFormat("dd-MM-yyyy")

fun mProgress(progress : Dialog) : Dialog {
    progress.setContentView(R.layout.progress)
    progress.window!!.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT)
    progress.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    progress.setCancelable(false)
    progress.show()

    return progress
}

fun mIsNetworkAvailable(mContext: Context): Boolean {
    val connectivityManager = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activateNetwokInfo = connectivityManager.activeNetworkInfo
    return activateNetwokInfo != null && activateNetwokInfo.isConnected
}

fun mConvertToDate(dateString: String): Date {
    return inputFormat.parse(dateString)!!
}

fun mPrintDate(date: Date): String{
    return outputFormat.format(date)
}

fun mConvertDate(dateString: String): String{
    val date = mConvertToDate(dateString)
    return mPrintDate(date)
}

fun mToastyOk(mContext: Context, text: String, duration : Int = Toast.LENGTH_SHORT) = Toasty.success(mContext,text,duration).show()
fun mToastyWarning(mContext: Context,text: String,duration : Int = Toast.LENGTH_SHORT) = Toasty.warning(mContext,text,duration).show()
fun mToastyError(mContext: Context,text: String,duration : Int = Toast.LENGTH_SHORT) = Toasty.error(mContext,text,duration).show()