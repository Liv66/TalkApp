package com.example.talkapp.chat

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.talkapp.Chat
import com.example.talkapp.CustomAdapter
import com.example.talkapp.databinding.FragmentChattingBinding
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChattingFragment : Fragment() {
    private val TAG = "ChattingFragment"
    private lateinit var binding: FragmentChattingBinding
    private lateinit var mDatabase: FirebaseDatabase
    private lateinit var mAdapter: CustomAdapter
    private lateinit var chatArrayList: ArrayList<Chat?>
    private lateinit var dateArrayList: ArrayList<String?>
    private lateinit var mRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        val safeArgs: ChattingFragmentArgs by navArgs()
        val loginEmailArg = safeArgs.loginEmail


        binding = FragmentChattingBinding.inflate(inflater, container, false)

        chatArrayList = ArrayList()
        dateArrayList = ArrayList()

        mAdapter = CustomAdapter()
        mAdapter.listData = chatArrayList
        mAdapter.stMyEmail = loginEmailArg

        mDatabase = Firebase.database
        mRef = mDatabase.getReference("TalkApp")

        binding.recyclerViewChat.adapter = mAdapter
        binding.recyclerViewChat.layoutManager = LinearLayoutManager(context)



        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                // A new comment has been added, add it to the displayed list
                val chat = dataSnapshot.getValue(Chat::class.java)
                chatArrayList.add(chat)
                mAdapter.notifyDataSetChanged()
                binding.recyclerViewChat.scrollToPosition(chatArrayList.size-1)
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
                Log.d(TAG, "onChildChanged: ${dataSnapshot.key}")
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.key!!)
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.key!!)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException())
                Toast.makeText(context, "Failed to load comments.",
                    Toast.LENGTH_SHORT).show()
            }
        }

        mRef.child("message").addChildEventListener(childEventListener)

        binding.btnSend.setOnClickListener {
            val etText = binding.etText.text.toString()
            mDatabase = Firebase.database

            val now = Date()
            val sdf = SimpleDateFormat("ah:m")
            sdf.timeZone = TimeZone.getTimeZone("Asia/Seoul")
            val formattedDate = sdf.format(now).toString()

            val numbers: Hashtable<String, String> = Hashtable<String, String>()

            numbers["email"] = loginEmailArg
            numbers["text"] = etText
            numbers["time"] = formattedDate
            mRef.child("message").push().setValue(numbers)

            binding.etText.text = null
        }

        return binding.root

    }
}