package com.fifty.fixitnow.featurechat.presentation.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.presentation.component.StandardAppBar
import com.fifty.fixitnow.core.presentation.ui.theme.ScaffoldBottomPaddingValue
import com.fifty.fixitnow.core.util.Screen
import com.fifty.fixitnow.featurechat.presentation.component.ChatItem

@Composable
fun ChatScreen(
    onNavigateUp: () -> Unit = {},
    onNavigate: (String) -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = ScaffoldBottomPaddingValue),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            StandardAppBar(
                showBackArrow = false,
                title = {
                    Text(
                        text = stringResource(R.string.your_chats),
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            )
            LazyColumn() {
                items(20) {
                    ChatItem(
                        onClick = {
                            onNavigate(Screen.MessageScreen.route)
                        },
                        onProfilePictureClick = {}
                    )
                }
            }
        }
    }
}