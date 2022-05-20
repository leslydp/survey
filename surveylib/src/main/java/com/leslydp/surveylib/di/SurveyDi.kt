package com.leslydp.surveylib.di

import com.leslydp.surveylib.source.remote.api.SurveyApi
import com.leslydp.surveylib.source.remote.repository.SurveyRemoteSource
import com.leslydp.surveylib.source.remote.repository.SurveyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SurveyDi {
    @Singleton
    @Provides
    internal fun providesSurveyRepository(remoteSource: SurveyRemoteSource): SurveyRepository {
        return  SurveyRepository(remoteSource = remoteSource)
    }

    @Provides
    internal fun providesSurveyRemoteSource(surveyApi: SurveyApi): SurveyRemoteSource{
        return  SurveyRemoteSource(api = surveyApi)
    }

    @Provides
    internal fun providesSurveyApi(): SurveyApi {
        return SurveyApi()
    }
}
