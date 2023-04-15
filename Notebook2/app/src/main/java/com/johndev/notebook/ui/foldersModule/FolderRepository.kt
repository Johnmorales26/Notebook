package com.johndev.notebook.ui.FolderEntitysModule

import com.johndev.notebook.R
import com.johndev.notebook.entities.FolderEntity

class FolderRepository {

    fun getAllFolderEntitys(): List<FolderEntity> {
        return mutableListOf<FolderEntity>(
            FolderEntity(1, "Work", R.drawable.ic_work),
            FolderEntity(2, "Personal", R.drawable.ic_personal),
            FolderEntity(3, "Study", R.drawable.ic_study),
            FolderEntity(4, "Projects", R.drawable.ic_projects),
            FolderEntity(5, "Recipes", R.drawable.ic_recipes),
            FolderEntity(6, "Quick notes", R.drawable.ic_quick_notes)
        )
    }

    fun getFolderByName(nameFolder: String): FolderEntity? = getAllFolderEntitys().find { it.name == nameFolder }

}