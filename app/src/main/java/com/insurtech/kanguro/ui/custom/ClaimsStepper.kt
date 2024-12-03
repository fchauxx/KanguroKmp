package com.insurtech.kanguro.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.insurtech.kanguro.R
import com.insurtech.kanguro.common.enums.ClaimStatus
import com.insurtech.kanguro.domain.coverage.Claim

private const val RADIUS = 70f
private const val INNER_RADIUS = 60f
private const val CY = RADIUS
private const val SPAN = 30

class ClaimsStepper @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.defaultState
) : View(context, attrs, defStyleAttr) {

    var claim: Claim? = null

    private val completedSteps
        get() = claim?.getStatusAsNumber() ?: 0

    private val subtitles = listOf(
        ClaimStatus.Submitted,
        ClaimStatus.Assigned,
        ClaimStatus.InReview,
        ClaimStatus.Closed
    )

    private val paint = Paint().apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.neutral_background)
    }

    private val innerPaint = Paint().apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.positive_medium)
    }

    private val innerWarningPaint = Paint().apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.warning_medium)
    }

    private val positiveColor = ContextCompat.getColor(context, R.color.positive_medium)
    private val warningColor = ContextCompat.getColor(context, R.color.warning_medium)

    private val numberTextColor = ContextCompat.getColor(context, R.color.secondary_light)

    private val markedDrawable = ContextCompat.getDrawable(context, R.drawable.ic_marked)
    private val markedDrawableAlert = ContextCompat.getDrawable(context, R.drawable.ic_warning)

    private val textPaint = Paint()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val steps = 4
        val cellSize = (width / steps).toFloat()

        for (i in 0 until steps) {
            val cx = (cellSize / 2) + cellSize * i

            drawStepCircle(canvas, cx)

            if (i != 0) {
                drawBridgeStart(canvas, cellSize, i)
            }

            if (i < steps - 1) {
                drawBridgeEnd(canvas, cx, cellSize, i)
            }

            drawStepSubtitle(canvas, cx, i)

            val coordinates = getMarkedBridgeCoordinates(cellSize, i)

            if (i >= completedSteps) {
                drawStepNumber(canvas, cx, i)

                val claimHasPendency =
                    claim?.hasPendingCommunications != null && claim?.hasPendingCommunications == true

                if (i == completedSteps && !claimHasPendency) {
                    drawMarkedBridge(canvas, coordinates, innerPaint)
                }
            } else {
                if (shouldShowWarningMark(i)) {
                    drawStepWithPendency(canvas, cx)
                    if (i != 0) {
                        drawMarkedBridge(canvas, coordinates, getGradient(coordinates))
                    }
                } else {
                    drawCompletedStep(canvas, cx)
                    if (i != 0) {
                        drawMarkedBridge(canvas, coordinates, innerPaint)
                    }
                }
            }
        }
    }

    private fun shouldShowWarningMark(i: Int): Boolean {
        val currentStep = i + 1

        return claim?.hasPendingCommunications == true &&
            (currentStep) == completedSteps
    }

    private fun drawStepCircle(canvas: Canvas?, cx: Float) {
        canvas?.drawCircle(cx, CY, RADIUS, paint)
    }

    private fun drawBridgeStart(canvas: Canvas?, cellSize: Float, i: Int) {
        val barHeight = resources.getDimension(R.dimen.bar_size)
        canvas?.drawRect(
            cellSize * i,
            CY + (barHeight / 2),
            (cellSize * i) + ((cellSize - (RADIUS * 2)) / 2),
            CY - (barHeight / 2),
            paint
        )
    }

    private fun drawBridgeEnd(canvas: Canvas?, cx: Float, cellSize: Float, i: Int) {
        val barHeight = resources.getDimension(R.dimen.bar_size)
        canvas?.drawRect(
            cx + RADIUS - SPAN,
            CY + (barHeight / 2),
            cellSize + (cellSize * i) + (SPAN * 2),
            CY - (barHeight / 2),
            paint
        )
    }

    private fun drawCompletedStep(canvas: Canvas?, cx: Float) {
        canvas?.drawCircle(cx, CY, INNER_RADIUS, innerPaint)
        drawMarked(markedDrawable, canvas, cx)
    }

    private fun drawStepWithPendency(canvas: Canvas?, cx: Float) {
        canvas?.drawCircle(cx, CY, INNER_RADIUS, innerWarningPaint)
        drawMarked(markedDrawableAlert, canvas, cx)
    }

    private fun drawMarked(markedDrawable: Drawable?, canvas: Canvas?, cx: Float) {
        if (markedDrawable != null && canvas != null) {
            val intCX = cx.toInt()
            val intCY = CY.toInt()
            val halfWidth = markedDrawable.intrinsicWidth / 2
            val halfHeight = markedDrawable.intrinsicHeight / 2
            val left = intCX - halfWidth
            val right = intCX + halfWidth
            val top = intCY - halfHeight
            val bottom = intCY + halfHeight

            markedDrawable.setBounds(
                left,
                top,
                right,
                bottom
            )

            markedDrawable.draw(canvas)
        }
    }

    private fun drawMarkedBridge(canvas: Canvas?, coordinates: Pair<Float, Float>, paint: Paint) {
        val innerBarHeight = resources.getDimension(R.dimen.inner_bar_size)
        canvas?.drawRoundRect(
            coordinates.first,
            CY + (innerBarHeight / 2),
            coordinates.second,
            CY - (innerBarHeight / 2),
            16f,
            16f,
            paint
        )
    }

    private fun getMarkedBridgeCoordinates(cellSize: Float, currentStep: Int): Pair<Float, Float> {
        val halfInnerRadius = INNER_RADIUS / 2
        val left = cellSize * currentStep - cellSize / 2 + INNER_RADIUS - 5F

        val right = if (currentStep != completedSteps) {
            cellSize * currentStep + cellSize / 2 - INNER_RADIUS + 5F
        } else {
            cellSize * currentStep + (halfInnerRadius / 2)
        }

        return left to right
    }

    private fun getGradient(coordinates: Pair<Float, Float>): Paint {
        return Paint().apply {
            style = Paint.Style.FILL
            shader = LinearGradient(
                coordinates.first,
                0F,
                coordinates.second,
                0F,
                positiveColor,
                warningColor,
                Shader.TileMode.MIRROR
            )
        }
    }

    private fun drawStepNumber(canvas: Canvas?, cx: Float, i: Int) {
        setNumberTextPaint()
        val text = (i + 1).toString()
        val textWidth = textPaint.measureText(text)
        val textHeight = textPaint.textSize

        canvas?.drawText(text, cx - (textWidth / 2), CY + (textHeight / 3), textPaint)
    }

    private fun drawStepSubtitle(canvas: Canvas?, cx: Float, i: Int) {
        setSubtitleTextPaint()
        val text = context.getString(subtitles[i].value)
        val textWidth = textPaint.measureText(text)
        val textHeight = height.toFloat() - (textPaint.textSize / 2)
        canvas?.drawText(text, cx - (textWidth / 2), textHeight, textPaint)
    }

    private fun setNumberTextPaint() {
        textPaint.apply {
            style = Paint.Style.FILL
            color = numberTextColor
            textSize = resources.getDimension(R.dimen.font_md)
            typeface = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                resources.getFont(R.font.museo_sans_bold)
            } else {
                ResourcesCompat.getFont(context, R.font.museo_sans_bold)
            }
        }
    }

    private fun setSubtitleTextPaint() {
        textPaint.apply {
            textPaint.textSize = resources.getDimension(R.dimen.font_xs)
        }
    }
}
