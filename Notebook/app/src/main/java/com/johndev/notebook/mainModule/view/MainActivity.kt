package com.johndev.notebook.mainModule.view

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.johndev.notebook.R
import com.johndev.notebook.SearchModule.view.SearchActivity
import com.johndev.notebook.aboutModule.view.AboutActivity
import com.johndev.notebook.common.adapter.NoteAdapter
import com.johndev.notebook.common.dataAccess.NoteListener
import com.johndev.notebook.common.entities.NotesEntity
import com.johndev.notebook.databinding.ActivityMainBinding
import com.johndev.notebook.editorModule.view.EditorActivity
import com.johndev.notebook.mainModule.viewModel.MainViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), NoteListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NoteAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()
        setupViewModel()
        setupRecycler()
        setupObservers()
        setupButtons()
    }

    private fun setupToolbar() {
        binding.toolbar.fabSearh.imgButton.load(R.drawable.ic_search) {
            crossfade(true)
            placeholder(R.drawable.ic_broken_image)
        }
        binding.toolbar.fabInfo.imgButton.load(R.drawable.ic_info) {
            crossfade(true)
            placeholder(R.drawable.ic_broken_image)
        }
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            mainViewModel.getAllNotes()
        }
    }

    private fun setupObservers() {
        mainViewModel.getResult().observe(this) { notes ->
            if (notes == null || notes.isEmpty()) {
                binding.recyclerview.visibility = GONE
                binding.viewEmpty.root.visibility = VISIBLE
                Toast.makeText(this, getString(R.string.alert_not_exist_notes), Toast.LENGTH_SHORT).show()
            } else {
                binding.viewEmpty.root.visibility = GONE
                binding.recyclerview.visibility = VISIBLE
                notes.forEach { adapter.add(it) }
                Toast.makeText(this, getString(R.string.alert_loading_notes), Toast.LENGTH_SHORT).show()
            }
        }
        mainViewModel.getSnackbarMsg().observe(this) { msj ->
            Toast.makeText(this, getString(msj), Toast.LENGTH_SHORT).show()
        }
        mainViewModel.getSnackbarMsgDelete().observe(this) {
            Toast.makeText(this, getString(it), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    private fun setupButtons() {
        binding.fabNewNote.setOnClickListener {
            startActivity(Intent(this, EditorActivity::class.java))
        }
        binding.toolbar.fabSearh.cardButton.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
        binding.toolbar.fabInfo.cardButton.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }
    }

    private fun setupRecycler(notes: MutableList<NotesEntity> = mutableListOf()) {
        binding.let {
            adapter = NoteAdapter(notes, this)
            it.recyclerview.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = this@MainActivity.adapter
            }
        }
    }

    override fun onNoteLongListener(notes: NotesEntity) {
        MaterialAlertDialogBuilder(this)
            .setMessage(resources.getString(R.string.delete_message))
            .setNegativeButton(resources.getString(R.string.alert_dialog_negative_button)) { dialog, which ->

            }
            .setPositiveButton(resources.getString(R.string.alert_dialog_positive_button)) { dialog, which ->
                lifecycleScope.launch {
                    mainViewModel.deleteNote(notes)
                    adapter.remove(notes)
                }
            }
            .show()
    }

    override fun onNoteListener(notes: NotesEntity) {
        val intent = Intent(this, EditorActivity::class.java).apply {
            putExtra("id_note_value", notes.id.toInt())
        }
        startActivity(intent)
        finish()
    }
}