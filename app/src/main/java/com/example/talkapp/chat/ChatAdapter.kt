package com.example.talkapp


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CustomAdapter : RecyclerView.Adapter<Holder>() {
    var listData = ArrayList<Chat?>()
    var stMyEmail: String = ""

    override fun getItemViewType(position: Int): Int {
        if (listData.get(position)!!.email.equals(stMyEmail)) {
            return 1
        } else {
            return 2
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        var v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.chat_item_recycler, parent, false)

        if (viewType == 1) {
            var v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.right_chat_item_recycler, parent, false)
            return Holder(v)
        }

        return Holder(v)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setData(listData.get(position))

    }

    override fun getItemCount(): Int {
        return listData.size
    }

}

class Holder(var v: View) : RecyclerView.ViewHolder(v) {
    fun setData(chat: Chat?) {
        v.findViewById<TextView>(R.id.message_text).text = chat?.text
//        val time = chat?.time!!.split(" ")[1]
        v.findViewById<TextView>(R.id.date_text).text = chat?.time
    }
}

