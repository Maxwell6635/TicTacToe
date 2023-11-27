package com.jackson.tictactoe.utils

import com.jackson.tictactoe.ui.gameplay.friend.viewmodel.PlayWithFriendViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module


val utilModule = module {
    factoryOf(::SharedPref)
    factory { CloseableCoroutineScope() }
}

val appModule = module {
    // ViewModel for Detail View
    viewModel { PlayWithFriendViewModel(get(), get()) }
}