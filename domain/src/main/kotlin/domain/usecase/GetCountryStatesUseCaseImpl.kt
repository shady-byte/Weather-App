package domain.usecase

import domain.model.CountryState
import domain.repository.StatesRepository

class GetCountryStatesUseCaseImpl(private val statesRepository: StatesRepository) :
    GetCountryStatesUseCase {
    override suspend operator fun invoke(params: String): Result<List<CountryState>> {
        return runCatching {
            statesRepository.getCountryStates(params)
        }
    }
}