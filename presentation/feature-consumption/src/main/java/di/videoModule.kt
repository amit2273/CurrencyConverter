package di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import viewmodel.VideoViewModel

val videoModule = module {
    viewModel {
        VideoViewModel(
            videoDetailsUseCase = get(),
        )
    }
}