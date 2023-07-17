package com.fifty.fixitnow.featureauth.presentation.selectcountry

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.fixitnow.core.presentation.util.UiEvent
import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.core.util.UiText
import com.fifty.fixitnow.featureauth.domain.model.Country
import com.fifty.fixitnow.featureauth.domain.usecase.GetCountriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SelectCountryViewModel @Inject constructor(
    private val getCountries: GetCountriesUseCase
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private var _state = mutableStateOf(SelectCountryState())
    val state: State<SelectCountryState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _countries = MutableStateFlow(listOf<Country>())
    val countries = searchText
        .debounce(100L)
        .combine(_countries) { text, countries ->
            if (text.isBlank()) {
                countries
            } else {
                countries.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _countries.value
        )

    init {
        loadCountries()
    }

    fun onEvent(event: SelectCountryEvent) {
        when (event) {
            is SelectCountryEvent.SearchQuery -> {
                _searchText.value = event.query
            }

            is SelectCountryEvent.SelectCountry -> {

            }
        }
    }

    private fun loadCountries() {
        viewModelScope.launch {
            _state.value = state.value.copy(
                isLoading = true
            )
            when (val result = getCountries()) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        isLoading = false
                    )
                    _countries.value = result.data ?: emptyList()
                }

                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.MakeToast(
                            uiText = result.uiText ?: UiText.unknownError()
                        )
                    )
                    _state.value = state.value.copy(
                        isLoading = false
                    )
                }
            }
        }
    }
}