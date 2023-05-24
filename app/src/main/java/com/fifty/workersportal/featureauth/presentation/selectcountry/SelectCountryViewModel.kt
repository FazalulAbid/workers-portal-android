package com.fifty.workersportal.featureauth.presentation.selectcountry

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.featureauth.domain.usecase.GetCountriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectCountryViewModel @Inject constructor(
    private val getCountries: GetCountriesUseCase
) : ViewModel() {

    private val _state = mutableStateOf(SelectCountryState())
    val state: State<SelectCountryState> = _state

    init {
        loadCountries()
    }

    private fun loadCountries() {
        viewModelScope.launch {
            _state.value = state.value.copy(
                isLoading = true
            )
            val result = getCountries()
            when (result) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        countries = result.data ?: emptyList(),
                        isLoading = false
                    )
                }

                is Resource.Error -> {
                    _state.value = state.value.copy(
                        countries = result.data ?: emptyList(),
                        isLoading = false
                    )
                }
            }
        }
    }
}