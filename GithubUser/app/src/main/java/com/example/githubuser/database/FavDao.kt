package com.example.githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favUser: Fav)

    @Delete
    fun delete(favUser: Fav)

    @Query("SELECT * FROM favorite")
    fun getAllFav(): LiveData<List<Fav>>

    @Query("SELECT * fROM favorite WHERE username = :username")
    fun getFavByUser(user: Fav): LiveData<Fav>

}