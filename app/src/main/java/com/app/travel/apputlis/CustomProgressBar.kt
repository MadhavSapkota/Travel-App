package com.elarkgroup.gwcl.appcomponent

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.widget.TextView
import com.app.travel.R


class CustomProgressBar {

    private var dialog: Dialog? = null

    fun show(context: Context): Dialog {
        return show(context, null)
    }

    fun show(context: Context?, title: CharSequence?): Dialog {
        return show(context, title, false)
    }

    fun show(context: Context?, title: CharSequence?, cancelable: Boolean): Dialog {
        return show(context, title, cancelable, null)
    }

    fun show(
            context: Context?, title: CharSequence?, cancelable: Boolean,
            cancelListener: DialogInterface.OnCancelListener?
    ): Dialog {
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.dialog_progress_bar, null)
        if (title != null) {
            val tv = view.findViewById(R.id.id_title) as TextView
            tv.text = title
        }

        dialog = Dialog(context, R.style.MyProgressDialogStyle)
        dialog!!.setContentView(view)
        dialog!!.setCancelable(cancelable)
        dialog!!.setOnCancelListener(cancelListener)
        dialog!!.show()

        return dialog as Dialog
    }

    fun getDialog(): Dialog? {
        return dialog
    }
}