package com.yrc.pos.core.base.modules

import com.yrc.pos.core.base.models.AccountRepo
import com.yrc.pos.features.login.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val accountViewModelModule = module {
    viewModel { LoginViewModel(get()) }
}

val accountRepoModule = module {
    factory { AccountRepo(get(), get()) }
}