package ru.itis.harrypotterelixirs.di

import ru.itis.harrypotterelixirs.domain.repositories.ElixirsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.itis.harrypotterelixirs.domain.usecases.GetAllElixirs
import ru.itis.harrypotterelixirs.domain.usecases.GetElixirByName

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideGetElixirByNameUseCase(elixirsRepository: ElixirsRepository): GetElixirByName =
        GetElixirByName(elixirsRepository)

    @Provides
    fun provideGetAllElixirsUseCase(elixirsRepository: ElixirsRepository): GetAllElixirs =
        GetAllElixirs(elixirsRepository)
}