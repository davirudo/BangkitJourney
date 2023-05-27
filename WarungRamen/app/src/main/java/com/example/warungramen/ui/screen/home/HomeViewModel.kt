package com.example.warungramen.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warungramen.data.RamenRepository
import com.example.warungramen.model.OrderRamen
import com.example.warungramen.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: RamenRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<OrderRamen>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<OrderRamen>>>
        get() = _uiState

    fun getAllRamens() {
        viewModelScope.launch {
            repository.getAllRamens()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { orderRamens ->
                    _uiState.value = UiState.Success(orderRamens)
                }
        }
    }
}