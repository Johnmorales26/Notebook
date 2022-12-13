package com.johndev.notebook.aboutModule.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import com.johndev.notebook.R
import com.johndev.notebook.common.adapter.SocialMediaAdapter
import com.johndev.notebook.common.dataAccess.OnSocialMedia
import com.johndev.notebook.common.dataAccess.getAllSocialMedia
import com.johndev.notebook.common.entities.SocialMediaEntity
import com.johndev.notebook.databinding.ActivityAboutBinding


class AboutActivity : AppCompatActivity(), OnSocialMedia {

    private lateinit var binding: ActivityAboutBinding
    private lateinit var adapter: SocialMediaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initProfilePicture()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.let {
            adapter = SocialMediaAdapter(getAllSocialMedia(), this)
            it.recyclerview.apply {
                layoutManager = GridLayoutManager(context, 4, LinearLayoutManager.VERTICAL,false)
                adapter = this@AboutActivity.adapter
            }
        }
    }

    private fun initProfilePicture() {
        binding.imgProfile.load(R.drawable.img_profile) {
            crossfade(true)
            placeholder(R.drawable.ic_broken_image)
            transformations(CircleCropTransformation())
        }
    }

    override fun onClick(socialMediaEntity: SocialMediaEntity) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(socialMediaEntity.url.trim()))
        startActivity(intent)
    }
}