package com.kurotkin.ajetpack

import android.content.Context
import java.net.URI


class UriValidator(var context: Context) {

    fun validate(uri: String): String? {
        var resId: Int = R.string.nothing
        if (isUrl(uri)) {
            resId = R.string.url
        } else if (isFile(uri)) {
            resId = R.string.file
        }
        return context.getString(resId)
    }

    private fun isUrl(uri: String): Boolean {
        return "http" == URI.create(uri).getScheme()
    }

    private fun isFile(uri: String): Boolean {
        return "file" == URI.create(uri).getScheme()
    }
}