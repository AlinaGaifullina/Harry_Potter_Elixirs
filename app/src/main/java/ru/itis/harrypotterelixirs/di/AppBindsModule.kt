package ru.itis.harrypotterelixirs.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.itis.harrypotterelixirs.data.repositoriesImpl.ElixirsRepositoryImpl
import ru.itis.harrypotterelixirs.domain.repositories.ElixirsRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class AppBindsModule {

    @Binds
    abstract fun bindElixirsRepositoryImpl_to_ElixirsRepository(repositoryImpl: ElixirsRepositoryImpl): ElixirsRepository
}