package com.malcolmmaima.teamwaypersonality.di

import com.malcolmmaima.teamwaypersonality.data.datastore.AppDatasource
import com.malcolmmaima.teamwaypersonality.data.repository.AppRepositoryImpl
import com.malcolmmaima.teamwaypersonality.domain.repository.AppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppRepository(
        userPrefs: AppDatasource
    ): AppRepository = AppRepositoryImpl(userPrefs)
}