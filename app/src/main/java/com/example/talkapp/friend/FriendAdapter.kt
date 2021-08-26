package com.example.talkapp.friend
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.talkapp.databinding.FriendListRecyclerBinding


import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class FriendAdapter : RecyclerView.Adapter<Holder>() {
    var listData = ArrayList<String?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = FriendListRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setData(listData.get(position))

    }

    override fun getItemCount(): Int {
        return listData.size
    }

}

class Holder(val binding: FriendListRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
    val database = Firebase.database
    val myRef = database.getReference("TalkApp")

    fun setData(name: String?) {
        binding.tvName.text = name
        binding.recyclerViewFriend.setOnClickListener {
            Log.d("##13","name: $name")
//            myRef.child("chat").child(CurrentU).updateChildren()
        }
    }
}

