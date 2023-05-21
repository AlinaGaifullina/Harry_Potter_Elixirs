package ru.itis.harrypotterelixirs.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.itis.harrypotterelixirs.domain.usecases.GetElixirByName
import ru.itis.harrypotterelixirs.ui.model.ElixirDataModel
import javax.inject.Inject

data class DetailsState(
    val isLoading: Boolean = false,
    val data: ElixirDataModel = ElixirDataModel(
        "",
        "",
        "",
        "",
        "",
        emptyList(),
        emptyList(),
        ""
    ),
    val error: String? = null
)

sealed interface DetailsAction {
    object NavigateBack: DetailsAction
}
sealed interface DetailsEvent{
    data class LoadElixir(val potionName: String) : DetailsEvent
    object OnBackBtnClick : DetailsEvent
}

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getElixirByName: GetElixirByName,
) : ViewModel() {

    private val potionName: String = checkNotNull(savedStateHandle["potionName"])

    private val internalState: MutableStateFlow<DetailsState> = MutableStateFlow(DetailsState())
    val state: StateFlow<DetailsState> = internalState.asStateFlow()

    private val _action = MutableSharedFlow<DetailsAction?>()
    val action: SharedFlow<DetailsAction?>
        get() = _action.asSharedFlow()

    init {
        requestElixirByName(potionName)
    }

    fun event (detailsEvent: DetailsEvent){
        when(detailsEvent){
            is DetailsEvent.LoadElixir -> requestElixirByNameEvent(detailsEvent)
            DetailsEvent.OnBackBtnClick -> onBackBtnClick()
        }
    }

    private fun onBackBtnClick(){
        viewModelScope.launch {
            _action.emit(DetailsAction.NavigateBack)
        }
    }

    private fun requestElixirByName(name: String){
        viewModelScope.launch {
            internalState.emit(
                internalState.value.copy(
                    isLoading = true,
                    error = null
                )
            )
            runCatching {
                getElixirByName(name)
            }.onSuccess { elixirs ->
                internalState.emit(
                    internalState.value.copy(
                        data = elixirs[0],
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

    private fun requestElixirByNameEvent(event: DetailsEvent.LoadElixir){
        viewModelScope.launch {

            internalState.emit(
                internalState.value.copy(
                    isLoading = true,
                    error = null
                )
            )
            runCatching {
                getElixirByName(event.potionName)
            }.onSuccess { elixirs ->
                internalState.emit(
                    internalState.value.copy(
                        data = elixirs[0],
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