package com.johndev.notebook.common.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.johndev.notebook.common.dataAccess.NoteListener
import com.johndev.notebook.common.entities.NotesEntity
import com.johndev.notebook.R
import com.johndev.notebook.common.dataAccess.OnSocialMedia
import com.johndev.notebook.common.entities.SocialMediaEntity
import com.johndev.notebook.databinding.ItemNoteBinding
import com.johndev.notebook.databinding.ItemSocialMediaBinding

class SocialMediaAdapter(var soocialList: List<SocialMediaEntity>, var listener: OnSocialMedia) :
    RecyclerView.Adapter<SocialMediaAdapter.ViewHolder>(){

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_social_media, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val social = soocialList[position]
        with(holder) {
            binding.apply {
                setListener(social)
                imgSocialMedia.load(social.img) {
                    crossfade(true)
                    placeholder(R.drawable.ic_broken_image)
                }
                tvSocialMedia.text = social.name.toString().trim()
            }
        }
    }

    override fun getItemCount(): Int = soocialList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ItemSocialMediaBinding.bind(view)

        fun setListener(socialMedia: SocialMediaEntity){
            binding.root.setOnClickListener {
                listener.onClick(socialMedia)
            }
        }
    }
}