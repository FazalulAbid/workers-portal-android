package com.fifty.workersportal.featureauth.presentation.selectcountry

import androidx.lifecycle.ViewModel
import com.fifty.workersportal.featureauth.domain.usecase.GetCountriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectCountryViewModel @Inject constructor(
    private val getCountries: GetCountriesUseCase
) : ViewModel() {

}