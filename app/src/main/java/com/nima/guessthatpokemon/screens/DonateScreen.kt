package com.nima.guessthatpokemon.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nima.guessthatpokemon.R
import com.nima.guessthatpokemon.util.Constants
import kotlinx.coroutines.launch

@Composable
fun DonateScreen(
    navController: NavController
) {

    val scope = rememberCoroutineScope()
    val snackBarHost = remember {
        SnackbarHostState()
    }
    val clipboard = LocalClipboardManager.current
    val context = LocalContext.current

    Box{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.donate_prompt),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 32.dp),
                textAlign = TextAlign.Center
            )

            OutlinedButton (
                onClick = {
                    scope.launch {
                        snackBarHost.showSnackbar(
                            message = context.getString(R.string.coming_soon),
                            withDismissAction = true,
                            duration = SnackbarDuration.Long,
                            actionLabel = null
                        )
                    }
                },
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 32.dp)
                    .fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.paypal_coming_soon))
            }

            OutlinedButton(
                onClick = {
                    clipboard.setText(AnnotatedString(Constants.eth_address))
                    scope.launch {
                        snackBarHost.showSnackbar(
                            message = context.getString(R.string.address_copied),
                            withDismissAction = true,
                            duration = SnackbarDuration.Long,
                            actionLabel = null
                        )
                    }
                },
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 32.dp)
                    .fillMaxWidth()

            ) {
                Text(text = stringResource(R.string.copy_ethereum))
            }

            OutlinedButton(
                onClick = {
                    clipboard.setText(AnnotatedString(Constants.btc_address))
                    scope.launch {
                        snackBarHost.showSnackbar(
                            message = context.getString(R.string.address_copied),
                            withDismissAction = true,
                            duration = SnackbarDuration.Long,
                            actionLabel = null
                        )
                    }
                },
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 32.dp)
                    .fillMaxWidth()

            ) {
                Text(text = stringResource(R.string.copy_btc_coin))
            }

            OutlinedButton(
                onClick = {
                    clipboard.setText(AnnotatedString(Constants.usdt_address))
                    scope.launch {
                        snackBarHost.showSnackbar(
                            message = context.getString(R.string.address_copied),
                            withDismissAction = true,
                            duration = SnackbarDuration.Long,
                            actionLabel = null
                        )
                    }
                },
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .padding(top = 8.dp, start = 32.dp, end = 32.dp)
                    .fillMaxWidth()

            ) {
                Text(text = stringResource(R.string.copy_tether))
            }

            OutlinedButton(
                onClick = {
                    clipboard.setText(AnnotatedString(Constants.ton_address))
                    scope.launch {
                        snackBarHost.showSnackbar(
                            message = context.getString(R.string.address_copied),
                            withDismissAction = true,
                            duration = SnackbarDuration.Long,
                            actionLabel = null
                        )
                    }
                },
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .padding(top = 8.dp, start = 32.dp, end = 32.dp)
                    .fillMaxWidth()

            ) {
                Text(text = stringResource(R.string.copy_ton))
            }

        }
        SnackbarHost(
            hostState = snackBarHost,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Snackbar(
                snackbarData = it,
                actionOnNewLine = false,
                shape = RoundedCornerShape(10.dp),
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                dismissActionContentColor = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}