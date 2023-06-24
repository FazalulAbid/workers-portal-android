package com.fifty.workersportal.featureworker.presentation.postsamplework

import android.Manifest
import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.compose.ui.window.DialogProperties
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.CameraPermissionTextProvider
import com.fifty.workersportal.core.presentation.component.PermissionDialog
import com.fifty.workersportal.core.presentation.component.SecondaryHeader
import com.fifty.workersportal.core.presentation.component.StandardAppBar
import com.fifty.workersportal.core.presentation.component.StandardBottomSheet
import com.fifty.workersportal.core.presentation.component.StandardTextField
import com.fifty.workersportal.core.presentation.ui.theme.MediumButtonHeight
import com.fifty.workersportal.core.presentation.ui.theme.SizeLarge
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.SizeSmall
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.core.presentation.util.makeToast
import com.fifty.workersportal.core.util.contentUriToFileUri
import com.fifty.workersportal.core.util.openAppSettings
import com.fifty.workersportal.featureworker.presentation.component.SelectImageSourceButton
import com.fifty.workersportal.featureworker.util.SampleWorkError
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.state.StateDialog
import com.maxkeppeler.sheets.state.models.ProgressIndicator
import com.maxkeppeler.sheets.state.models.State
import com.maxkeppeler.sheets.state.models.StateConfig
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class, ExperimentalCoilApi::class)
@Composable
fun PostSampleWorkScreen(
    onNavigateUp: () -> Unit,
    imageLoader: ImageLoader,
    previousBackStackEntry: NavBackStackEntry?,
    viewModel: PostSampleWorkViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var showImageSourceSelectorSheet by remember { mutableStateOf(true) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val state = viewModel.state.value
    val imageSourceSheetState = rememberModalBottomSheetState()
    val addImageLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.image_add_lottie)
    )
    val addImageLottieProgress by animateLottieCompositionAsState(
        composition = addImageLottieComposition, iterations = LottieConstants.IterateForever
    )


    val permissionsToRequest = arrayOf(
        Manifest.permission.CAMERA
    )

    val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            permissionsToRequest.forEach { permission ->
                viewModel.onPermissionResult(
                    permission = permission,
                    isGranted = perms[permission] == true
                )
            }
        }
    )

    val pickImageGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        if (it == null) return@rememberLauncherForActivityResult
        val fileUri = contentUriToFileUri(context = context, it)
        fileUri?.let {
            viewModel.onEvent(PostSampleWorkEvent.PickImage(fileUri))
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.MakeToast -> {

                }

                is UiEvent.Navigate -> {
//                    onNavigate(event.route)
                }

                UiEvent.NavigateUp -> {
                    previousBackStackEntry?.savedStateHandle?.set("isSampleWorkAdded", true)
                    onNavigateUp()
                }

                else -> {}
            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.errorFlow.collectLatest { error ->
            when (error) {
                SampleWorkError.NoImageError -> {
                    makeToast(R.string.please_select_an_image_to_post, context)
                }
            }
        }
    }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StandardAppBar(
            onNavigateUp = onNavigateUp,
            showBackArrow = true,
            title = {
                Text(
                    text = stringResource(R.string.post_your_work),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            navActions = {
                IconButton(
                    onClick = {
                        viewModel.onEvent(PostSampleWorkEvent.Post)
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_done),
                        contentDescription = stringResource(id = R.string.post_your_work),
                        tint = if (viewModel.postImageState.value != null) {
                            MaterialTheme.colorScheme.primary
                        } else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            if (viewModel.postImageState.value != null) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clickable {
                            showImageSourceSelectorSheet = true
                        },
                    painter = rememberImagePainter(
                        data = viewModel.postImageState.value,
                        imageLoader = imageLoader
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clickable {
                            showImageSourceSelectorSheet = true
                        },
                    contentAlignment = Alignment.Center
                ) {
                    LottieAnimation(
                        modifier = Modifier.size(200.dp),
                        composition = addImageLottieComposition,
                        progress = addImageLottieProgress
                    )
                }
            }
            Column(
                modifier = Modifier
                    .offset(y = -SizeMedium)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topEnd = SizeMedium, topStart = SizeMedium))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = SizeMedium),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.height(SizeLarge))
                SecondaryHeader(
                    text = stringResource(R.string.enter_work_details),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                Spacer(modifier = Modifier.height(SizeMedium))
                StandardTextField(
                    basicTextFieldModifier = Modifier
                        .height(MediumButtonHeight)
                        .fillMaxWidth(),
                    hint = stringResource(R.string.what_is_your_work),
                    value = viewModel.titleState.value.text,
                    onValueChange = {
                        viewModel.onEvent(
                            PostSampleWorkEvent.EnterPostTitle(it)
                        )
                    },
                    maxLength = 200,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                        capitalization = KeyboardCapitalization.Sentences
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Next) }
                    )
                )
                Spacer(modifier = Modifier.height(SizeMedium))
                StandardTextField(
                    basicTextFieldModifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = MediumButtonHeight, max = (3f * MediumButtonHeight))
                        .padding(vertical = SizeSmall),
                    hint = stringResource(R.string.what_do_you_want_to_talk_about_it),
                    value = viewModel.descriptionState.value.text,
                    onValueChange = {
                        viewModel.onEvent(
                            PostSampleWorkEvent.EnterPostDescription(it)
                        )
                    },
                    singleLine = false,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                        capitalization = KeyboardCapitalization.Sentences
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    )
                )
            }
        }
    }

    if (showImageSourceSelectorSheet) {
        StandardBottomSheet(
            sheetState = imageSourceSheetState,
            onDismiss = {
                showImageSourceSelectorSheet = false
            }
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(SizeMedium)
            ) {
                SecondaryHeader(text = stringResource(R.string.select_image_source))
                Spacer(modifier = Modifier.height(SizeLarge))
                Row(
                    Modifier
                        .fillMaxWidth()
                ) {
                    SelectImageSourceButton(
                        icon = painterResource(id = R.drawable.ic_camera),
                        text = stringResource(id = R.string.camera),
                        containerColor = MaterialTheme.colorScheme.primary,
                        onClick = {
                            multiplePermissionResultLauncher.launch(
                                permissionsToRequest
                            )
                            coroutineScope.launch {
                                imageSourceSheetState.hide()
                                showImageSourceSelectorSheet = false
                            }
                        }
                    )
                    Spacer(modifier = Modifier.width(SizeLarge))
                    SelectImageSourceButton(
                        icon = painterResource(id = R.drawable.ic_gallery),
                        text = stringResource(id = R.string.gallery),
                        containerColor = MaterialTheme.colorScheme.secondary,
                        onClick = {
                            pickImageGalleryLauncher.launch("image/*")
                            coroutineScope.launch {
                                imageSourceSheetState.hide()
                                showImageSourceSelectorSheet = false
                            }
                        }
                    )
                }
                Spacer(modifier = Modifier.height(SizeLarge))
            }
        }
    }

    val fetchingDataLoadingState = State.Loading(
        stringResource(id = R.string.posting_your_work),
        ProgressIndicator.Circular()
    )
    if (state.isLoading) {
        StateDialog(
            properties = DialogProperties(
                dismissOnClickOutside = false,
                dismissOnBackPress = false
            ),
            state = rememberUseCaseState(visible = true),
            config = StateConfig(state = fetchingDataLoadingState)
        )
    }

    viewModel.visiblePermissionDialogQueue.reversed().forEach { permission ->
        PermissionDialog(
            permissionTextProvider = when (permission) {
                Manifest.permission.CAMERA -> CameraPermissionTextProvider()
                else -> {
                    return@forEach
                }
            },
            isPermanentlyDeclined = !ActivityCompat.shouldShowRequestPermissionRationale(
                context as Activity,
                permission
            ),
            onDismiss = viewModel::dismissPermissionDialog,
            onOkClick = {
                viewModel.dismissPermissionDialog()
                multiplePermissionResultLauncher.launch(
                    arrayOf(permission)
                )
            },
            onGotoAppSettingsClick = {
                context.openAppSettings()
                viewModel.dismissPermissionDialog()
            }
        )
    }
}