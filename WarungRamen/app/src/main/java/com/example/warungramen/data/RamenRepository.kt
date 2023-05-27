package com.example.warungramen.data

import com.example.warungramen.model.RamenDataSource
import com.example.warungramen.model.OrderRamen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class RamenRepository {

    private val orderRamens = mutableListOf<OrderRamen>()

    init {
        if (orderRamens.isEmpty()) {
            RamenDataSource.dummyRamens.forEach {
                orderRamens.add(OrderRamen(it, 0))
            }
        }
    }

    fun getAllRamens(): Flow<List<OrderRamen>> {
        return flowOf(orderRamens)
    }

    fun getOrderRamenById(ramenId: Long): OrderRamen {
        return orderRamens.first {
            it.ramen.id == ramenId
        }
    }

    fun updateOrderRamen(ramenId: Long, newCountValue: Int): Flow<Boolean> {
        val index = orderRamens.indexOfFirst { it.ramen.id == ramenId }
        val result = if (index >= 0) {
            val orderRamen = orderRamens[index]
            orderRamens[index] =
                orderRamen.copy(ramen = orderRamen.ramen, count = newCountValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedOrderRamens(): Flow<List<OrderRamen>> {
        return getAllRamens()
            .map { orderRamens ->
                orderRamens.filter { orderRamen ->
                    orderRamen.count != 0
                }
            }
    }

    companion object {
        @Volatile
        private var instance: RamenRepository? = null

        fun getInstance(): RamenRepository =
            instance ?: synchronized(this) {
                RamenRepository().apply {
                    instance = this
                }
            }
    }
}