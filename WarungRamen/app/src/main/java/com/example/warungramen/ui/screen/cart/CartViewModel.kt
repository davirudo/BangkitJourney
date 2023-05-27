package com.example.warungramen.ui.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warungramen.data.RamenRepository
import com.example.warungramen.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: RamenRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<CartState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<CartState>>
        get() = _uiState

    fun getAddedOrderRamens() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedOrderRamens()
                .collect { orderRamen ->
                    val totalRequiredPoint =
                        orderRamen.sumOf { it.ramen.requiredPrice * it.count }
                    _uiState.value = UiState.Success(CartState(orderRamen, totalRequiredPoint))
                }
        }
    }

    fun updateOrderRamen(ramenId: Long, count: Int) {
        viewModelScope.launch {
            repository.updateOrderRamen(ramenId, count)
                .collect { isUpdated ->
                    if (isUpdated) {
                        getAddedOrderRamens()
                    }
                }
        }
    }
}