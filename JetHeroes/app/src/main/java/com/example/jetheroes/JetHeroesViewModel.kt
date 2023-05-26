package com.example.jetheroes

import androidx.lifecycle.ViewModel
import com.example.jetheroes.data.HeroRepository
import com.example.jetheroes.model.Hero
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class JetHeroesViewModel(private val repository: HeroRepository) : ViewModel() {

    private val _groupHeroes = MutableStateFlow(
        repository.getHeroes()
            .sortedBy { it.name }
            .groupBy { it.name.first() }
    )
    val groupedHeroes: StateFlow<Map<Char,List<Hero>>> get() = _groupHeroes
}