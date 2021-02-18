package org.sopt.santamanitto.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

object ClipBoardUtil {

    fun copy(context: Context, label: String, content: String) {
        val manager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, content)
        manager.setPrimaryClip(clip)
    }
}