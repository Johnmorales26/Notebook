package com.johndev.notebook.SearchModule.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.johndev.notebook.R
import com.johndev.notebook.SearchModule.viewModel.SearchViewModel
import com.johndev.notebook.common.adapter.NoteAdapter
import com.johndev.notebook.common.dataAccess.NoteListener
import com.johndev.notebook.common.entities.NotesEntity
import com.johndev.notebook.databinding.ActivitySearchBinding
import com.johndev.notebook.editorModule.view.EditorActivity
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity(), NoteListener {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initEditText()
        setupViewModel()
        setupObservers()
        setupRecycler()
    }

    private fun setupViewModel() {
        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]
    }

    private fun setupObservers() {
        searchViewModel.getResult().observe(this) { notes ->
            if (notes == null || notes.isEmpty()) {
                binding.recyclerview.visibility = View.GONE
                binding.viewEmpty.root.visibility = View.VISIBLE
                Toast.makeText(this, getString(R.string.alert_not_exist_notes), Toast.LENGTH_SHORT).show()
            } else {
                binding.viewEmpty.root.visibility = View.GONE
                binding.recyclerview.visibility = View.VISIBLE
                notes.forEach { adapter.add(it) }
                Toast.makeText(this, getString(R.string.alert_loading_notes), Toast.LENGTH_SHORT).show()
            }
        }
        searchViewModel.getSnackbarMsg().observe(this) { message ->
            Toast.makeText(this, getString(message), Toast.LENGTH_SHORT).show()
        }
    }

    private fun initEditText() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(query: CharSequence, start: Int, before: Int, count: Int) {
                lifecycleScope.launch { searchViewModel.searchNote(query.toString().trim()) }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
        })
    }

    private fun setupRecycler(notes: MutableList<NotesEntity> = mutableListOf()) {
        binding.let {
            adapter = NoteAdapter(notes, this)
            it.recyclerview.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = this@SearchActivity.adapter
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
                    searchViewModel.deleteNote(notes)
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