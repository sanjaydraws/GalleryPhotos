package com.sanjay.galleryphotos.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sanjay.galleryphotos.bindingadapter.loadImage
import com.sanjay.galleryphotos.databinding.ItemDirectoriesBinding
import com.sanjay.galleryphotos.models.ModelDirectory


class DirectoriesAdapter(
    var directoriesList: List<ModelDirectory>,
    val onDirectoryClick: ((Array<String>) -> Unit)  ): RecyclerView.Adapter<DirectoriesAdapter.DirectoriesVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirectoriesAdapter.DirectoriesVH {
        val binding = ItemDirectoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DirectoriesVH(binding)
    }
    fun updateData(directoriesList: List<ModelDirectory> ){
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
        fun loadData(directories: ModelDirectory){
            binding.txtTotalImages.text = directories.al_imagepath.size.toString()
            binding.txtDirectoryName.text = directories.str_folder

            binding.directoryImage.loadImage(directories.al_imagepath[0])

            binding?.directoryImage.setOnClickListener {
//                    onDirectoryClick.invoke(directories.al_imagepath)
            }
        }
    }
}