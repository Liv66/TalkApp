package com.example.talkapp.login


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.talkapp.R
import com.example.talkapp.UserAccount
import com.example.talkapp.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var mFirebaseAuth: FirebaseAuth
    private lateinit var mDatabaseRef: DatabaseReference
    private lateinit var mEtEmail: EditText
    private lateinit var mEtName: EditText
    private lateinit var mEtPwd: EditText
    private lateinit var mBtnRegister: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
       //val v:View = inflater.inflate(R.layout.fragment_register,container,false)

        mFirebaseAuth = Firebase.auth
        mDatabaseRef = Firebase.database.getReference("TalkApp")

        mEtEmail = binding.etEmail
        mEtPwd = binding.etPwd
        mEtName = binding.etName
        mBtnRegister = binding.btnRegister

        mBtnRegister.setOnClickListener {
            val strEmail: String = mEtEmail.text.toString()
            val strPwd: String = mEtPwd.text.toString()
            val strName: String = mEtName.text.toString()
            mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Log.d("##12"," task : $task")
                        val firebasUser: FirebaseUser = task.result!!.user!!
                        val account =
                            UserAccount(firebasUser.email!!, strPwd,strName, firebasUser.uid)
                        //database에 insert
                        mDatabaseRef.child("UserAccount").child(firebasUser.uid)
                            .setValue(account)
                        Toast.makeText(activity, "회원가입에 성공하였습니다!", Toast.LENGTH_LONG).show()
                        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                        findNavController().navigate(action)

                    } else {
                        if (binding.etPwd.text.length < 6){
                            Toast.makeText(activity, "비밀번호를 6자 이상으로 해주세요", Toast.LENGTH_LONG).show()
                        }
                        Log.d("##12", "${task.exception}")
                    }
                }
        }
        // Inflate the layout for this fragment
        return binding.root
    }

}