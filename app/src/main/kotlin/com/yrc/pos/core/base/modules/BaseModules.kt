package com.yrc.pos.core.base.modules

import com.yrc.pos.core.base.models.BaseRepo
import com.yrc.pos.core.base.viewmodels.BaseViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val baseViewModelModule = module {
    viewModel { BaseViewModel(get()) }
}

val baseRepoModule = module {
    factory { BaseRepo(get()) }
}