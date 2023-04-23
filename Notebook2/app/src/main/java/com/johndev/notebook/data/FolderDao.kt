package com.johndev.notebook.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.johndev.notebook.entities.FolderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FolderDao {

    @Query("SELECT * FROM FolderEntity")
    fun getAll(): Flow<List<FolderEntity>>

    @Query("SELECT * FROM FolderEntity WHERE name = :name")
    fun getByName(name: String): FolderEntity?

    @Insert
    fun insert(folderEntity: FolderEntity)

    @Delete
    fun delete(folderEntity: FolderEntity)

    @Update
    fun update(folderEntity: FolderEntity)

}