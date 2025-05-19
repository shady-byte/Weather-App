package domain.usecase

import domain.model.CountryState

interface GetCountryStatesUseCase {
    suspend operator fun invoke(params: String): Result<List<CountryState>>
}