package com.example.viewtest

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import java.util.Random

class ColorWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        appWidgetIds?.forEach {
            updateWidget(context, appWidgetManager, it)
        }
    }

    private fun updateWidget(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        widgetId: Int
    ) {
        val remoteViews = RemoteViews(context?.packageName, R.layout.widget)
        val color = Random().nextInt()
        remoteViews.setTextViewText(R.id.label, context?.getString(R.string.label_template, color))
        remoteViews.setInt(R.id.root, "setBackgroundColor", color)
        appWidgetManager?.updateAppWidget(widgetId, remoteViews)
    }
}