package com.sanjay.galleryphotos.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sanjay.galleryphotos.bindingadapter.loadImage
import com.sanjay.galleryphotos.databinding.ItemDirectoriesBinding
import com.sanjay.galleryphotos.models.Directory


class DirectoriesAdapter (var directoriesList: List<Directory> ): RecyclerView.Adapter<DirectoriesAdapter.DirectoriesVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirectoriesAdapter.DirectoriesVH {
        val binding = ItemDirectoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DirectoriesVH(binding)
    }
    fun updateData(directoriesList: List<Directory> ){
        this.directoriesList = directoriesList
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
        return directoriesList.size
    }

    override fun onBindViewHolder(holder: DirectoriesAdapter.DirectoriesVH, position: Int) {
        holder.loadData(directoriesList[position])
    }
    inner  class DirectoriesVH(val binding :ItemDirectoriesBinding):RecyclerView.ViewHolder(binding.root){
        fun loadData(directories : Directory){
            binding.txtTotalImages.text = "12"
            binding?.txtDirectoryName.text = directories.dirName

            binding?.directoryImage.loadImage(directories.firstImgUrl)
        }
    }
}