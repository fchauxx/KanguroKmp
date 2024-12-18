package com.insurtech.kanguro.shared.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.insurtech.kanguro.shared.data.KanguroUserApiService
import com.insurtech.kanguro.shared.data.UserRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.URLProtocol
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


@Composable
public fun SharedLiveVetBottomSheet(
    modifier: Modifier = Modifier.fillMaxSize(),
    onEvent: (LiveVetEvent) -> Unit = {}
) {
    val client = remember {
        HttpClient {
            install(ContentNegotiation){
                json( Json {
                    ignoreUnknownKeys = true
                })
            }
            install(DefaultRequest){
                url {
                    headers { set("Authorization","") }
                    protocol = URLProtocol.HTTPS
                    host = "kanguro-api-staging.azurewebsites.net"
                    parameters.append("api-version", "2023-05-01")
                }
            }
        }
    }
    val viewModel = viewModel {
      LiveVetViewModel(UserRepository(KanguroUserApiService(client)))
    }
    val liveVetUiState by viewModel.state.collectAsState()

    LiveVetBottomSheetScreenContent(
        modifier = modifier,
        isLoading = liveVetUiState.isLoading,
        isError = liveVetUiState.isError,
        airvetUserDetails = liveVetUiState.airvetUserDetails,
        onEvent = onEvent
    )
}
