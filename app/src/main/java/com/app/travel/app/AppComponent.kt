package com.app.travel.app

import android.content.Context

import dagger.Component
import dagger.android.AndroidInjectionModule


@AppScope
@Component(modules=[AndroidInjectionModule::class, AppModule::class,NetworkModule::class])
interface AppComponent {
    fun context(): Context
    fun webservice(): Webservice


}
