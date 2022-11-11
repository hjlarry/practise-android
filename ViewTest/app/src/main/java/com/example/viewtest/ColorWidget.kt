package com.example.viewtest

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import java.util.Random


private const val ACTION_REFRESH = "refresh"
private const val EXTRA_APP_WIDGET_ID = "appWidgetId"

class ColorWidget : AppWidgetProvider() {
    private val random = Random()
    override fun onUpdate(
        context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?
    ) {
        appWidgetIds?.forEach {
            updateWidget(context, appWidgetManager, it)
        }
    }

    private fun updateWidget(
        context: Context?, appWidgetManager: AppWidgetManager?, widgetId: Int
    ) {
        val remoteViews = RemoteViews(context?.packageName, R.layout.widget)
        val color = random.nextInt()
        remoteViews.setTextViewText(R.id.label, context?.getString(R.string.label_template, color))
        remoteViews.setInt(R.id.root, "setBackgroundColor", color)


        val refreshIntent =
            Intent(context, ColorWidget::class.java).setAction(ACTION_REFRESH).putExtra(
                EXTRA_APP_WIDGET_ID, widgetId
            )
        val refreshPI = PendingIntent.getBroadcast(
            context, widgetId, refreshIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        remoteViews.setOnClickPendingIntent(R.id.refresh, refreshPI)


        appWidgetManager?.updateAppWidget(widgetId, remoteViews)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == ACTION_REFRESH && intent.hasExtra(EXTRA_APP_WIDGET_ID)) {
            val appWidgetManager = context?.getSystemService(AppWidgetManager::class.java)
            updateWidget(context, appWidgetManager, intent.getIntExtra(EXTRA_APP_WIDGET_ID, -1))
        } else {
            super.onReceive(context, intent)
        }
    }
}