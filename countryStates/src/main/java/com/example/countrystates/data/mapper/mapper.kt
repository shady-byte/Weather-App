package com.example.countrystates.data.mapper

import com.example.countrystates.data.remote.dto.CountryStateDto
import com.example.countrystates.domain.model.CountryState

fun CountryStateDto.mapToCountryState(): CountryState = CountryState(
    name = name,
    iso2 = iso2
)