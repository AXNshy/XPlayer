package com.luffy.smartplay.ui.view

import android.view.ViewGroup
import android.content.*
import android.util.AttributeSet

/**
 * Created by axnshy on 16/8/9.
 */
class OneThirdOfParentLayout : ViewGroup {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
    }

    override fun onLayout(b: Boolean, i: Int, i1: Int, i2: Int, i3: Int) {}
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}