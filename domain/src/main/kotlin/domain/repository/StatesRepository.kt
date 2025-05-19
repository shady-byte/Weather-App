package domain.repository

import domain.model.CountryState

interface StatesRepository {
    suspend fun getCountryStates(countryCode: String): List<CountryState>
}