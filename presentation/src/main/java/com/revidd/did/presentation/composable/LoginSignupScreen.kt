package com.revidd.did.presentation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.revidd.did.presentation.R
import com.revidd.did.presentation.state.SignInIntent
import com.revidd.did.presentation.state.SignInUiState

@Composable
fun LoginSignupScreen(
    signInUiState: SignInUiState,
    intent: (SignInIntent) -> Unit
) {

    val emailField = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.horizontalGradient(
                    listOf(Color(0xFF0B1220), Color(0xFF02040A))
                )
            )
            .padding(48.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 32.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Sign in using Email address",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.height(24.dp))

            TvTextField(
                label = "Enter Email ID"
            ) {
                emailField.value = it
            }

            Spacer(Modifier.height(16.dp))

            TvTextField(
                label = "Enter Password",
                isPassword = true
            ) {
                password.value = it
            }

            Spacer(Modifier.height(24.dp))

            TvPrimaryButton("SIGN IN")
            {
                intent.invoke(
                    SignInIntent.SignIn(
                        email = emailField.value,
                        password = password.value
                    )
                )
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Are you new to Revidd? Sign up now",
                color = Color(0xFF4FC3F7),
                fontSize = 14.sp
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Forgot Password?",
                color = Color.Gray,
                fontSize = 14.sp
            )

            Spacer(Modifier.height(24.dp))

            Row {
                SocialIcon(R.drawable.ic_launcher_foreground)
                Spacer(Modifier.width(16.dp))
                SocialIcon(R.drawable.ic_launcher_foreground)
            }
        }

        LaunchedEffect(Unit) {
            intent.invoke(SignInIntent.SignInQrCode)
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Using QR Code",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.height(24.dp))

            AsyncImage(
                model = signInUiState.qrCode,
                contentDescription = "QR Code",
                modifier = Modifier.size(220.dp)
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Scan the QR code using your mobile device\nand login instantly.",
                color = Color.Gray,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }
    }

    if (signInUiState.isLoading) {
        FullScreenLoader()
    }
}

@Composable
fun FullScreenLoader() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

