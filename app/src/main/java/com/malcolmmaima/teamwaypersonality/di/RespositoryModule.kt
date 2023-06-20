package com.malcolmmaima.teamwaypersonality.di

import com.google.gson.Gson
import com.malcolmmaima.teamwaypersonality.data.datastore.AppDatasource
import com.malcolmmaima.teamwaypersonality.data.repository.AppRepository
import com.malcolmmaima.teamwaypersonality.data.repository.AppRepositoryImpl
import com.malcolmmaima.teamwaypersonality.network.MalcolmApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RespositoryModule {

    @Singleton
    @Provides
    fun provideAppRepository(
        malcolmApi: MalcolmApi,
        userPrefs: AppDatasource,
        gson: Gson
    ): AppRepository = AppRepositoryImpl(malcolmApi, userPrefs, gson)
}