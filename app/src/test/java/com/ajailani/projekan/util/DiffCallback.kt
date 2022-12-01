package com.ajailani.projekan.util

import androidx.recyclerview.widget.DiffUtil
import com.ajailani.projekan.domain.model.Project

class DiffCallback : DiffUtil.ItemCallback<Project>() {
    override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean {
        return oldItem == newItem
    }
}