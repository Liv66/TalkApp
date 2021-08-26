package com.example.talkapp.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.talkapp.R
import com.example.talkapp.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {
    private val TAG = "LoginFragment"
    private lateinit var binding: FragmentLoginBinding
    private lateinit var mFirebaseAuth: FirebaseAuth
    private lateinit var mEtEmail: EditText
    private lateinit var mEtPwd: EditText
    private lateinit var mBtnLogin: Button
    private lateinit var mBtnRegister: Button
    private lateinit var progreeBar: ProgressBar

//    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login, container, false)

        mFirebaseAuth = Firebase.auth

        mEtEmail = binding.etEmail
        mEtPwd = binding.etPwd

        mEtPwd.text = null
        mEtEmail.text = null

        mBtnLogin = binding.btnLogin
        mBtnRegister = binding.btnRegister0
        progreeBar = binding.progressBar
        mBtnLogin.setOnClickListener {
            val strEmail: String = mEtEmail.text.toString()
            val strPwd: String = mEtPwd.text.toString()
            if (strEmail != "" && strPwd != "") {
                progreeBar.visibility = View.VISIBLE
                mFirebaseAuth.signInWithEmailAndPassword(strEmail, strPwd)
                    .addOnCompleteListener(requireActivity()) { task ->
                        progreeBar.visibility = View.GONE
                        if (task.isSuccessful) {
                            val currentUid = mFirebaseAuth.currentUser!!.uid
                            val loginEmailArg = strEmail
                            val action = LoginFragmentDirections.actionLoginToFriend(loginEmailArg,
                                currentUid)
                            findNavController().navigate(action)
                        } else {
                            mEtPwd.text = null
                            Log.d(TAG, "${task.exception}")
                            Toast.makeText(context, "아이디(비밀번호)를 확인해주세요", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(context, "아이디(비밀번호)를 입력하세요", Toast.LENGTH_SHORT).show()
            }
        }
        mBtnRegister.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        // Inflate the layout for this fragment
        return binding.root
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        mainActivity = context as MainActivity
//    }
}