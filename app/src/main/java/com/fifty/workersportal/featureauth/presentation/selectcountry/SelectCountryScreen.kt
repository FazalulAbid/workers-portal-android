package com.fifty.workersportal.featureauth.presentation.selectcountry

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.StandardAppBar
import com.fifty.workersportal.core.presentation.component.StandardSearchTextField
import com.fifty.workersportal.core.presentation.ui.theme.MediumButtonHeight
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.core.presentation.util.asString
import com.fifty.workersportal.featureauth.domain.model.Country
import com.fifty.workersportal.featureauth.presentation.component.CountryCodeItem
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SelectCountryCodeScreen(
    navController: NavController,
    viewModel: SelectCountryViewModel = hiltViewModel()
) {
    val searchText by viewModel.searchText.collectAsState()
    val countries by viewModel.countries.collectAsState()
    val state = viewModel.state.value
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.MakeToast -> {
                    Toast.makeText(context, event.uiText.asString(context), Toast.LENGTH_LONG)
                        .show()
                }

                else -> Unit
            }
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        StandardAppBar(
            showBackArrow = true,
            title = {
                Text(
                    text = stringResource(R.string.select_country),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        )
        Column {
            Box(
                modifier = Modifier.padding(
                    top = 0.dp,
                    bottom = SizeMedium,
                    start = SizeMedium,
                    end = SizeMedium
                )
            ) {
                StandardSearchTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(MediumButtonHeight),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = stringResource(R.string.search),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    trailingIcon = null,
                    hint = stringResource(R.string.search_by_country_name),
                    value = searchText,
                    onValueChange = {
                        viewModel.onEvent(SelectCountryEvent.SearchQuery(it))
                    }
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                CompositionLocalProvider(
                    LocalOverscrollConfiguration provides null
                ) {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(countries) { country ->
                            CountryCodeItem(
                                country = Country(
                                    alpha2Code = country.alpha2Code,
                                    callingCode = country.callingCode,
                                    flagUrl = country.flagUrl,
                                    name = country.name
                                ),
                                onClick = {
                                    navController.previousBackStackEntry
                                        ?.savedStateHandle
                                        ?.set("callingCode", country.callingCode)
                                    navController.previousBackStackEntry
                                        ?.savedStateHandle
                                        ?.set("flagUrl", country.flagUrl)
                                    navController.previousBackStackEntry
                                        ?.savedStateHandle
                                        ?.set("name", country.name)
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
                if (state.isLoading) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}