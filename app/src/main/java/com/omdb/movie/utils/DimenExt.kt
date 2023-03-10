package com.omdb.movie.utils

import android.content.res.Resources

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Float.dp: Float
    get() = (this * Resources.getSystem().displayMetrics.density)

val Int.sp: Int
    get() = (this * Resources.getSystem().displayMetrics.scaledDensity).toInt()

val Float.sp: Float
    get() = (this * Resources.getSystem().displayMetrics.scaledDensity)
