package com.ilmiddin1701.cameraandgallery.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ilmiddin1701.cameraandgallery.databinding.ItemRvBinding
import com.ilmiddin1701.cameraandgallery.models.ImageData

class RvAdapter(var list: ArrayList<ImageData>): Adapter<RvAdapter.Vh>() {

    inner class Vh(var itemRvBinding: ItemRvBinding): ViewHolder(itemRvBinding.root){
        fun onBind(imageData: ImageData){
            itemRvBinding.itemText.text = imageData.name
            itemRvBinding.itemImage.setImageURI(Uri.parse(imageData.image))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }
}