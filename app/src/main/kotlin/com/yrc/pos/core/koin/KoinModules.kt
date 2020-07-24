package com.yrc.pos.core.koin

import com.yrc.pos.core.base.modules.accountRepoModule
import com.yrc.pos.core.base.modules.accountViewModelModule
import com.yrc.pos.core.base.modules.baseRepoModule
import com.yrc.pos.core.base.modules.baseViewModelModule
import com.yrc.pos.core.network.modules.apiModule
import com.yrc.pos.core.network.modules.retrofitModule
import com.yrc.pos.core.prefs.prefModule
import org.koin.core.module.Module

fun getListOfModules(): List<Module> {

    return (listOf(
        apiModule,
        prefModule,
        retrofitModule,
        accountRepoModule,
        accountViewModelModule,
        baseRepoModule,
        baseViewModelModule
    ))

}