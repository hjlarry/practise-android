package com.commonsware.todo

import android.app.Application
import android.text.format.DateUtils
import com.commonsware.todo.repo.PrefsRepository
import com.commonsware.todo.repo.ToDoDatabase
import com.commonsware.todo.repo.ToDoRemoteDateSource
import com.commonsware.todo.repo.ToDoRepository
import com.commonsware.todo.report.RosterReport
import com.commonsware.todo.ui.SingleModelMotor
import com.commonsware.todo.ui.roster.RosterMotor
import com.github.jknack.handlebars.Handlebars
import com.github.jknack.handlebars.Helper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.time.Instant

class ToDoApp : Application() {
    private val koinModule = module {
        single { ToDoRepository(get<ToDoDatabase>().todoStore(), get(named("appScope")), get()) }
        viewModel { RosterMotor(get(), get(), androidApplication(), get(named("appScope")), get()) }
        viewModel { (modelId: String) -> SingleModelMotor(get(), modelId) }
        single { ToDoDatabase.newInstance(androidContext()) }
        single(named("appScope")) { CoroutineScope(SupervisorJob()) }
        single {
            Handlebars().apply {
                registerHelper("dateFormat", Helper<Instant> { value, _ ->
                    DateUtils.getRelativeDateTimeString(
                        androidContext(),
                        value.toEpochMilli(),
                        DateUtils.MINUTE_IN_MILLIS,
                        DateUtils.WEEK_IN_MILLIS,
                        0
                    )
                })
            }
        }
        single { RosterReport(androidContext(), get(), get(named("appScope"))) }
        single { OkHttpClient.Builder().build() }
        single { ToDoRemoteDateSource(get()) }
        single { PrefsRepository(androidContext()) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ToDoApp)
            modules(koinModule)
        }
    }
}