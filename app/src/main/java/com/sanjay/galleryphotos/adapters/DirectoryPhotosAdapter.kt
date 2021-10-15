package com.sanjay.galleryphotos.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sanjay.galleryphotos.bindingadapter.loadImage
import com.sanjay.galleryphotos.databinding.ItemDirectoriesBinding
import com.sanjay.galleryphotos.databinding.ItemDirectoryphotosGridBinding


class DirectoryPhotosAdapter (var photosList: List<String> ): RecyclerView.Adapter<DirectoryPhotosAdapter.DirectoriesPhotosVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirectoryPhotosAdapter.DirectoriesPhotosVH {
        val binding = ItemDirectoryphotosGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DirectoriesPhotosVH(binding)
    }
    fun updateData(photosList: List<String> ){
        this.photosList = photosList
        notifyDataSetChanged()
    }

//    companion object : DiffUtil.ItemCallback<Directories>() {
//        override fun areItemsTheSame(oldItem: Directories, newItem: Directories): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(oldItem: Directories, newItem: Directories): Boolean {
//            return oldItem.id == newItem.id && oldItem.dirName == newItem.dirName
//        }
//    }


    override fun getItemCount(): Int {
        return photosList.size
    }

    override fun onBindViewHolder(holder: DirectoryPhotosAdapter.DirectoriesPhotosVH, position: Int) {
        holder.loadData(photosList.get(position))
    }
    inner  class DirectoriesPhotosVH(val binding : ItemDirectoryphotosGridBinding): RecyclerView.ViewHolder(binding.root){
        fun loadData(photosUri : String){
            binding.photoImage.loadImage(photosUri)
            binding.photoImage.setOnClickListener {
            }
        }
    }
}