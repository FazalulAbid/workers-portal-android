package com.fifty.workersportal.featureauth.presentation.selectcountry

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.StandardAppBar
import com.fifty.workersportal.core.presentation.component.StandardSearchTextField
import com.fifty.workersportal.core.presentation.ui.theme.MediumButtonHeight
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.util.Constants
import com.fifty.workersportal.featureauth.domain.model.Country
import com.fifty.workersportal.featureauth.presentation.component.CountryCodeItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SelectCountryCodeScreen(

) {
    Column {
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
                    value = "",
                    onValueChange = {}
                )
            }
            CompositionLocalProvider(
                LocalOverscrollConfiguration provides null
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(20) {
                        CountryCodeItem(
                            country = Country(
                                alpha2Code = Constants.DEFAULT_COUNTRY_CODE,
                                callingCode = Constants.DEFAULT_COUNTRY_CODE,
                                flagUrl = Constants.DEFAULT_COUNTRY_FLAG_URL,
                                name = Constants.DEFAULT_COUNTRY_NAME
                            ),
                            onClick = {}
                        )
                    }
                }
            }
        }
    }
}