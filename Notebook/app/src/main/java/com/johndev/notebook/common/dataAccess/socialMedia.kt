package com.johndev.notebook.common.dataAccess

import com.johndev.notebook.R
import com.johndev.notebook.common.entities.SocialMediaEntity

fun getAllSocialMedia(): List<SocialMediaEntity> {
    return listOf(
        SocialMediaEntity(1, "Facebook", "https://www.facebook.com/jonh.mt.54/", R.drawable.ic_facebook),
        SocialMediaEntity(2, "Telegram", "https://t.me/JohnMorales26", R.drawable.ic_telegram),
        SocialMediaEntity(3, "Play Store", "https://play.google.com/store/apps/developer?id=Jonatan+Morales", R.drawable.ic_play_store),
        SocialMediaEntity(4, "Linkedln", "https://www.linkedin.com/in/jonatan-arturo-morales-tavera-0ba5b1217/", R.drawable.ic_linkedln),
        SocialMediaEntity(5, "Email", "johnta2610@hotmail.com", R.drawable.ic_email),
        SocialMediaEntity(6, "Web Site", "https://johnmorales26.github.io/portafolio/html/index.html", R.drawable.ic_web_site)
    )
}