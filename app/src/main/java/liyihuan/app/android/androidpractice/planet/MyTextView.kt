package liyihuan.app.android.androidpractice.planet

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import liyihuan.app.android.androidpractice.R


class MyTextView : LinearLayout {
    private var sign: String? = null
    var nickName: TextView

    constructor(context: Context) : super(context) {
        var rootView = LayoutInflater.from(context).inflate(R.layout.item_tv, this, true)
        nickName = rootView.findViewById(R.id.nickName)
    }

    public fun setSign(sign: String) {
        this.sign = sign
        nickName.text = sign
    }
}