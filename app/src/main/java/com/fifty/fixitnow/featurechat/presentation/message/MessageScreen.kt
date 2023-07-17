package com.fifty.fixitnow.featurechat.presentation.message

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.presentation.component.StandardAppBar
import com.fifty.fixitnow.core.presentation.component.StandardTextField
import com.fifty.fixitnow.core.presentation.ui.theme.ExtraSmallProfilePictureHeight
import com.fifty.fixitnow.core.presentation.ui.theme.MediumButtonHeight
import com.fifty.fixitnow.core.presentation.ui.theme.SizeExtraSmall
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium
import com.fifty.fixitnow.core.presentation.ui.theme.SizeSmall
import com.fifty.fixitnow.core.util.Constants
import com.fifty.fixitnow.featurechat.presentation.component.OwnMessage
import com.fifty.fixitnow.featurechat.presentation.component.RemoteMessage
import com.fifty.fixitnow.featurechat.presentation.component.SuggestMessageItem

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MessageScreen(
    onNavigateUp: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardAppBar(
            onNavigateUp = onNavigateUp,
            showBackArrow = true,
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.plumber_profile),
                        contentDescription = null,
                        Modifier
                            .clip(CircleShape)
                            .size(ExtraSmallProfilePictureHeight),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(SizeMedium))
                    Text(
                        text = "Fazalul Abid",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                    )
                }
            }
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
            ) {
                items(20) {
                    RemoteMessage(
                        message = "Hello there!",
                        formattedTime = "",
                        color = MaterialTheme.colorScheme.surface,
                        textColor = MaterialTheme.colorScheme.onSurface
                    )
                    OwnMessage(
                        message = "Hii, here I am",
                        formattedTime = "",
                        color = MaterialTheme.colorScheme.primary,
                        textColor = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
            Column(
                modifier = Modifier.padding(vertical = SizeMedium)
            ) {
                CompositionLocalProvider(
                    LocalOverscrollConfiguration provides null
                ) {
                    LazyRow() {
                        items(5) {
                            Spacer(modifier = Modifier.width(SizeMedium))
                            SuggestMessageItem(text = "Hello, there!")
                            if (it == 5-1) {
                                Spacer(modifier = Modifier.width(SizeMedium))
                            }
                        }
                    }
                }
                Column(
                    Modifier.padding(horizontal = SizeMedium)
                ) {
                    Spacer(modifier = Modifier.height(SizeMedium))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        StandardTextField(
                            modifier = Modifier.weight(1f),
                            basicTextFieldModifier = Modifier
                                .heightIn(min = MediumButtonHeight)
                                .padding(vertical = SizeExtraSmall),
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_message_filled),
                                    contentDescription = "",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            value = "Hello",
                            onValueChange = {

                            },
                            hint = "Type your message here...",
                            maxLength = Constants.MESSAGE_LENGTH,
                            singleLine = false
                        )
                        Spacer(modifier = Modifier.width(SizeSmall))
                        IconButton(onClick = {}) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_send_filled),
                                contentDescription = stringResource(R.string.send_message),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}