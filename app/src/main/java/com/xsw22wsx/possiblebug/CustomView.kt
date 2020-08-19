package com.xsw22wsx.possiblebug

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

class CustomView : View {

    // When this line is commented the view renders properly in the layout editor
    var property: Int by CustomProperty(0)

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) = canvas?.drawCircle(
        width/2F,
        height/2F,
        min(width, height)/2F,
        Paint().apply { color = Color.RED }
    ) ?: Unit
}