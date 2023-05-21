package ru.itis.harrypotterelixirs.ui.screens.elixirs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.itis.harrypotterelixirs.domain.usecases.GetAllElixirs
import ru.itis.harrypotterelixirs.ui.model.ElixirDataModel
import javax.inject.Inject

data class ElixirsState(
    val isLoading: Boolean = false,
    val data: List<ElixirDataModel> = emptyList(),
    val error: String? = null
)

sealed interface ElixirsEvent{
    object LoadElixirs : ElixirsEvent
}

@HiltViewModel
class ElixirsViewModel @Inject constructor(
    private val getAllElixirs: GetAllElixirs,
) : ViewModel() {

    private val internalState: MutableStateFlow<ElixirsState> = MutableStateFlow(
        ElixirsState()
    )
    val state: StateFlow<ElixirsState> = internalState.asStateFlow()

    fun event (elixirsEvent: ElixirsEvent){
        when(elixirsEvent){
            ElixirsEvent.LoadElixirs -> requestAllElixirs()
        }
    }
    init {
        requestAllElixirs()
    }

    private fun requestAllElixirs() {
        viewModelScope.launch {
            internalState.emit(
                internalState.value.copy(
                    isLoading = true,
                    error = null
                )
            )
            runCatching {
                getAllElixirs()
            }.onSuccess { elixirs ->
                internalState.emit(
                    internalState.value.copy(
                        data = elixirs,
                        isLoading = false,
                        error = null
                    )
                )
            }.onFailure { ex ->
                internalState.emit(
                    internalState.value.copy(
                        isLoading = false,
                        error = ex.toString()
                    )
                )
            }
        }
    }
}