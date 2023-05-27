package com.example.warungramen.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warungramen.data.RamenRepository
import com.example.warungramen.model.OrderRamen
import com.example.warungramen.model.Ramen
import com.example.warungramen.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailRamenViewModel(
    private val repository: RamenRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<OrderRamen>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<OrderRamen>>
        get() = _uiState

    fun getRamenById(ramenId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getOrderRamenById(ramenId))
        }
    }

    fun addToCart(ramen: Ramen, count: Int) {
        viewModelScope.launch {
            repository.updateOrderRamen(ramen.id, count)
        }
    }
}