package com.mungkive.application.ui.test

import android.R.attr.font
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mungkive.application.viewmodels.ApiTestViewModel

@Composable
fun ApiTestScreen(
    viewModel: ApiTestViewModel
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = viewModel.id,
            onValueChange = viewModel::onIdChange,
            label = { Text("ID") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = viewModel.pw,
            onValueChange = viewModel::onPwChange,
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )

        /* Login */
        Button(
            onClick = viewModel::login,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        /* Token */
        Text(
            text = "Access Token: ${viewModel.token ?: "-"}",
            style = MaterialTheme.typography.bodySmall
        )

        HorizontalDivider()

        /* API Buttons */
        Text(text = "API", style = MaterialTheme.typography.titleMedium)
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = viewModel::getProfile) { Text("Get Profile") }
            Button(onClick = viewModel::listPosts) { Text("Get Post List") }
        }

        /* API Response */
        HorizontalDivider()
        Text(text = "Result", style = MaterialTheme.typography.titleMedium)
        Text(
            text = viewModel.apiResult,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
