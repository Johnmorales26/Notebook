package com.johndev.notebook.data

import com.johndev.notebook.ui.allNotesModule.NotesViewModel
import com.johndev.notebook.ui.foldersModule.FolderViewModel

object ViewModels {

    private val InstanceNotesViewModel: NotesViewModel by lazy { NotesViewModel() }
    fun getInstanceNotesVM() = InstanceNotesViewModel

    private val InstanceFoldersViewModel: FolderViewModel by lazy { FolderViewModel() }
    fun getInstanceFoldersVM() = InstanceFoldersViewModel

}