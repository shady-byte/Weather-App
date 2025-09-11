package com.example.countrystates.di

import com.example.core_module.di.ApiKeyProvider
import com.example.core_module.presentation.utils.DEFAULT_CONFIG
import com.example.countrystates.data.remote.StatesApiService
import com.example.countrystates.data.repository.StatesRepositoryImpl
import com.example.countrystates.domain.repository.StatesRepository
import com.example.countrystates.domain.usecase.GetCountryStatesUseCase
import com.example.countrystates.presentation.states.StatesViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

const val STATES_CLIENT = "states_client"

@OptIn(ExperimentalSerializationApi::class)
val countryStatesModule = module {

    single(named(STATES_CLIENT)) {
        get<OkHttpClient>(named(DEFAULT_CONFIG)).newBuilder()
            .addInterceptor { chain ->
                val original = chain.request()
                val newRequest = original.newBuilder()
                    .header("X-CSCAPI-KEY", get<ApiKeyProvider>().statesApiKey)
                    .build()
                chain.proceed(newRequest)
            }.build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(get<ApiKeyProvider>().statesBaseUrl)
            .client(get(named(STATES_CLIENT)))
            .addConverterFactory(
                get<Json>().asConverterFactory("application/json; charset=UTF8".toMediaType())
            )
            .build()
            .create(StatesApiService::class.java)
    }

    single<StatesRepository> {
        StatesRepositoryImpl(get(), get())
    }

    factory { GetCountryStatesUseCase(get()) }

    viewModel { StatesViewModel(get()) }
}