package com.reodeveloper.marvelpay.ui.amount_selector

import android.text.Spanned
import android.text.method.DigitsKeyListener

class MoneyInputFilter : DigitsKeyListener(false, true) {
    val maxDecimals = 2
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val temp = StringBuilder(dest).insert(dstart, source).toString()

        if (temp.equals(".")) {
            return "0."
        }
        with(temp) {
            if (contains(".")) {
                if (substring(indexOf(".") + 1).length > maxDecimals) return ""
            }
        }
        return super.filter(source, start, end, dest, dstart, dend)
    }
}