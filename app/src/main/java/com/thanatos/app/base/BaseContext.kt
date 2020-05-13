package com.thanatos.app.base

import android.app.Application
import kotlin.properties.Delegates

class BaseContext : Application() {
    init {

    }

    companion object {
        var instance: BaseContext by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}