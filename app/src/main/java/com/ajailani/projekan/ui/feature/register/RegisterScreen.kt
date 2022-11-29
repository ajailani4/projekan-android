package com.ajailani.projekan.ui.feature.register

import android.app.Activity
import android.view.WindowManager
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ajailani.projekan.R
import com.ajailani.projekan.ui.common.UIState
import com.ajailani.projekan.ui.common.component.ProgressBarWithBackground
import com.ajailani.projekan.ui.theme.extraLarge
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    registerViewModel: RegisterViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val onEvent = registerViewModel::onEvent
    val registerState = registerViewModel.registerState
    val name = registerViewModel.name
    val email = registerViewModel.email
    val username = registerViewModel.username
    val password = registerViewModel.password
    val passwordVisibility = registerViewModel.passwordVisibility

    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    val context = LocalContext.current

    (context as Activity).window
        .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

    Scaffold(scaffoldState = scaffoldState) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            IconButton(
                modifier = Modifier.padding(top = 8.dp, start = 4.dp),
                onClick = onNavigateUp
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back icon"
                )
            }
            Column(
                modifier = Modifier.padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.register),
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.h1
                )
                Spacer(modifier = Modifier.height(60.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = name,
                    onValueChange = { onEvent(RegisterEvent.OnNameChanged(it)) },
                    label = {
                        Text(text = stringResource(id = R.string.name))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Badge,
                            contentDescription = "Name icon"
                        )
                    },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = email,
                    onValueChange = { onEvent(RegisterEvent.OnEmailChanged(it)) },
                    label = {
                        Text(text = stringResource(id = R.string.email))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Mail,
                            contentDescription = "Email icon"
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = username,
                    onValueChange = { onEvent(RegisterEvent.OnUsernameChanged(it)) },
                    label = {
                        Text(text = stringResource(id = R.string.username))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Username icon"
                        )
                    },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = password,
                    onValueChange = { onEvent(RegisterEvent.OnPasswordChanged(it)) },
                    label = {
                        Text(text = stringResource(id = R.string.password))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Password icon"
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { onEvent(RegisterEvent.OnPasswordVisibilityChanged) }) {
                            Icon(
                                imageVector = if (passwordVisibility) {
                                    Icons.Default.VisibilityOff
                                } else {
                                    Icons.Default.Visibility
                                },
                                contentDescription = "Password visibility icon"
                            )
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = if (passwordVisibility) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    }
                )
                Spacer(modifier = Modifier.height(30.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.extraLarge,
                    onClick = {
                        if (name.isNotEmpty() && email.isNotEmpty() &&
                            username.isNotEmpty() && password.isNotEmpty()
                        ) {
                            onEvent(RegisterEvent.Register)
                        } else {
                            coroutineScope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    context.getString(R.string.fill_the_form)
                                )
                            }
                        }
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(5.dp),
                        text = stringResource(id = R.string.register)
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                ClickableText(
                    text = buildAnnotatedString {
                        append(stringResource(id = R.string.have_account))
                        append(" ")

                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colors.primary
                            )
                        ) {
                            append(stringResource(id = R.string.login_here))
                        }
                    },
                    style = MaterialTheme.typography.body1.copy(
                        color = MaterialTheme.colors.onBackground
                    ),
                    onClick = { onNavigateToLogin() }
                )
            }
        }

        // Observe register state
        when (registerState) {
            UIState.Loading -> {
                ProgressBarWithBackground()
            }

            is UIState.Success -> {
                onNavigateToHome()
            }

            is UIState.Fail -> {
                LaunchedEffect(scaffoldState) {
                    registerState.message?.let {
                        scaffoldState.snackbarHostState.showSnackbar(it)
                    }
                }
            }

            is UIState.Error -> {
                LaunchedEffect(scaffoldState) {
                    registerState.message?.let {
                        scaffoldState.snackbarHostState.showSnackbar(it)
                    }
                }
            }

            else -> {}
        }
    }
}