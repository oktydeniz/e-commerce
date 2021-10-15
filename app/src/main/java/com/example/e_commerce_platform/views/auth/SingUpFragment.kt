package com.example.e_commerce_platform.views.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.e_commerce_platform.R
import com.example.e_commerce_platform.databinding.FragmentSingUpBinding
import com.example.e_commerce_platform.util.Util
import com.example.e_commerce_platform.views.app.AppActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class SingUpFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database : DatabaseReference
    private var _binding:FragmentSingUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSingUpBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }
    private fun init(){
        firebaseAuth = FirebaseAuth.getInstance()
        database = Firebase.database.reference
        binding.singUpBtn.setOnClickListener {
            val mail = binding.singUpMailInput.text.toString()
            val password = binding.singUpPasswordInput.text.toString()
            val pConfirmation = binding.singUpPasswordInput.text.toString()
            if( passwordConfirmation(password,pConfirmation) && mailConfirmation(mail)){
                singUp(mail,password)
            }else{
                Util.toastMakerL(requireContext(),getString(R.string.wrong_message))
            }
        }
    }

    private fun singUp(mail: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(mail,password).addOnSuccessListener {
            val intent = Intent(requireContext(), AppActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            requireActivity().finish()

        }.addOnCompleteListener {
            if (it.isSuccessful){
                val user = FirebaseAuth.getInstance().currentUser
                user?.let { id->
                    val uId = id.uid
                    val map = HashMap<String,Any>()
                    map["mail"] = mail
                    map["user_name"] = "null"
                    map["about_user"] = "null"
                    map["user_image"] = "null"
                    database.child("users").child(uId).setValue(map)
                }
            }
        }.addOnFailureListener {
            Util.toastMakerL(requireContext(),getString(R.string.wrong_message))
        }

    }

    private fun mailConfirmation(mail: String): Boolean {
        return mail.trim().isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(mail).matches()

    }

    private fun passwordConfirmation(password: String, pConfirmation: String): Boolean {
        return (password == pConfirmation && password.length >= 6)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }


}