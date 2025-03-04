package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.util.ResultState

interface UseCase<in Params, out Result> {
    suspend operator fun invoke(params: Params): ResultState<Result>
}