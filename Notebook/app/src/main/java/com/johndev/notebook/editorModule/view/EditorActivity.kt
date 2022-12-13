package com.johndev.notebook.editorModule.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.load
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.johndev.notebook.R
import com.johndev.notebook.common.entities.NotesEntity
import com.johndev.notebook.databinding.ActivityEditorBinding
import com.johndev.notebook.editorModule.viewModel.EditorViewModel
import com.johndev.notebook.mainModule.view.MainActivity
import kotlinx.coroutines.launch

class EditorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditorBinding
    private lateinit var editorViewModel: EditorViewModel
    private var isModified = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()
        setupViewModel()
        setupObservers()
        val id_value = intent.getIntExtra("id_note_value", 0)
        isModified = id_value != 0
        getDataRoom(id_value)
    }

    private fun getDataRoom(id_value: Int) {
        lifecycleScope.launch {
            editorViewModel.consultNoteById(id_value.toLong())
        }
    }

    private fun setupObservers() {
        editorViewModel.getSnackbarMsg().observe(this) {
            Toast.makeText(this, getString(it), Toast.LENGTH_SHORT).show()
        }
        editorViewModel.getResultSearch().observe(this) { note ->
            if (note != null) {
                binding.etTitle.text = note.title.toString().trim().toEditable()
                binding.etBody.text = note.body.toString().trim().toEditable()
            }
        }
        editorViewModel.getSnackbarMsgUpdate().observe(this) {
            Toast.makeText(this, getString(it), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupActionBar() {
        binding.toolbar.fabReturn.imgButton.load(R.drawable.ic_arrow_back) {
            crossfade(true)
            placeholder(R.drawable.ic_broken_image)
        }
        binding.toolbar.fabSave.imgButton.load(R.drawable.ic_save) {
            crossfade(true)
            placeholder(R.drawable.ic_broken_image)
        }
        binding.toolbar.fabVisibility.imgButton.load(R.drawable.ic_visibility) {
            crossfade(true)
            placeholder(R.drawable.ic_broken_image)
        }
        binding.toolbar.fabReturn.cardButton.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setMessage(resources.getString(R.string.exit_message))
                .setNegativeButton(resources.getString(R.string.alert_dialog_negative_button)) { dialog, which ->

                }
                .setPositiveButton(resources.getString(R.string.alert_dialog_positive_button)) { dialog, which ->
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                .show()
        }
        binding.toolbar.fabSave.cardButton.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setMessage(resources.getString(R.string.save_message))
                .setNegativeButton(resources.getString(R.string.alert_dialog_negative_button)) { dialog, which ->

                }
                .setPositiveButton(resources.getString(R.string.alert_dialog_positive_button)) { dialog, which ->
                    saveData()
                }
                .show()
        }
    }

    private fun saveData() {
        if (validFields()) {
            Toast.makeText(this, getString(R.string.alert_save), Toast.LENGTH_SHORT).show()
            val titleNote = binding.etTitle.text.toString().trim()
            val bodyNote = binding.etBody.text.toString().trim()
            val note = NotesEntity(title = titleNote, body = bodyNote)
            if (isModified) {
                lifecycleScope.launch { editorViewModel.updateNote(note) }
            } else {
                lifecycleScope.launch { editorViewModel.addNote(note) }
            }
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun setupViewModel() {
        editorViewModel = ViewModelProvider(this)[EditorViewModel::class.java]
    }

    private fun validFields(): Boolean{
        var isValid = true
        //  Evaluando value A
        if (binding.etTitle.text.isNullOrEmpty()){
            binding.tilTitle.run {
                error = getString(R.string.alert_required)
                requestFocus()
            }
            isValid = false
        } else {
            binding.tilTitle.error = null
        }
        //  Evaluando value B
        if (binding.etBody.text.isNullOrEmpty()){
            binding.tilBody.run {
                error = getString(R.string.alert_required)
                requestFocus()
            }
            isValid = false
        } else {
            binding.tilBody.error = null
        }
        return isValid
    }

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

}