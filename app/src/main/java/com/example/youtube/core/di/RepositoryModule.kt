package com.example.youtube.core.di

import com.example.youtube.domain.repository.Repository
import org.koin.dsl.module

val repositoryModule = module {
    single { Repository(get()) }
}