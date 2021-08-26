package com.example.talkapp.friend

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.talkapp.*
import com.example.talkapp.databinding.FragmentFriendBinding
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class FriendFragment : Fragment() {
    private val TAG = "FriendFragment"
    private lateinit var binding: FragmentFriendBinding
    private lateinit var mDatabase: FirebaseDatabase
    private lateinit var userArrayList: ArrayList<UserAccount?>
    private lateinit var friendArrayList: List<String>
    private lateinit var nameArrayList: ArrayList<String?>
    private lateinit var adapter: FriendAdapter
    private lateinit var mRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val safeArgs: FriendFragmentArgs by navArgs()
        val loginEmailArg = safeArgs.loginEmail
        val currentUser = safeArgs.uid

        binding = FragmentFriendBinding.inflate(inflater, container, false)

        userArrayList = ArrayList()
        friendArrayList = ArrayList()
        nameArrayList = ArrayList()

        mDatabase = Firebase.database
        mRef = mDatabase.getReference("TalkApp")

        adapter = FriendAdapter()

        adapter.listData = nameArrayList
        binding.recyclerViewFriend.adapter = adapter
        binding.recyclerViewFriend.layoutManager = LinearLayoutManager(context)

        binding.apply {
            btnChatlist.setOnClickListener {

                val action = FriendFragmentDirections.actionFriendFragmentToListFragment(
                    loginEmailArg,
                    currentUser)
                findNavController().navigate(action)

            }

            imgFriend.setOnClickListener {
                val text = etFriendEmail.text.toString()
                for (i in userArrayList) {
                    if (!nameArrayList.contains(i!!.name) && i.emailId == text) {
                        val numbers: Hashtable<String, Any> = Hashtable<String, Any>()
                        numbers[i.idToken] = true
                        //친구 목록에 추가
                        mRef.child("FriendList").child(currentUser).updateChildren(numbers)
                    }
                }
            }
        }
        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                val user = dataSnapshot.getValue(UserAccount::class.java)
                userArrayList.add(user)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                Log.d(TAG, "onChildChanged: ${snapshot.key}")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }


        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val friend = dataSnapshot.getValue<Any>()
                if (friend != null) {
                    val friendlength = friend.toString().length
                    friendArrayList = friend.toString().substring(1, friendlength - 6).split("=true, ")
                }
                for (i in userArrayList) {
                    if (friendArrayList.contains(i?.idToken) && !nameArrayList.contains(i?.name)) {
                        nameArrayList.add(i?.name)
                    }
                }
                adapter.notifyDataSetChanged()
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }

        mRef.child("UserAccount").addChildEventListener(childEventListener)
        mRef.child("FriendList").child(currentUser).addValueEventListener(postListener)

        return binding.root
    }
}
