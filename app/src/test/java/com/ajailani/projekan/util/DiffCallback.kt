package com.ajailani.projekan.util

import androidx.recyclerview.widget.DiffUtil
import com.ajailani.projekan.domain.model.ProjectItem

class DiffCallback : DiffUtil.ItemCallback<ProjectItem>() {
    override fun areItemsTheSame(oldItem: ProjectItem, newItem: ProjectItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ProjectItem, newItem: ProjectItem): Boolean {
        return oldItem == newItem
    }
}