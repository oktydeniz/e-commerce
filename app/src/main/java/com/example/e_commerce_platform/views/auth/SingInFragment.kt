package com.example.e_commerce_platform.views.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.e_commerce_platform.R
import com.example.e_commerce_platform.databinding.FragmentSingInBinding
import com.example.e_commerce_platform.util.Util
import com.example.e_commerce_platform.views.app.AppActivity
import com.google.firebase.auth.FirebaseAuth

class SingInFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    private var _binding:FragmentSingInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentSingInBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }
    private fun init(){
        firebaseAuth = FirebaseAuth.getInstance()
        binding.singInBtn.setOnClickListener {
            singIn()
        }
        binding.singUpTextView.setOnClickListener {
            val action = SingInFragmentDirections.actionSingInFragmentToSingUpFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun singIn(){
        val mail = binding.singInMailInput.text.toString()
        val password = binding.singInPasswordInput.text.toString()
        if(mail.isNotEmpty() && password.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            firebaseAuth.signInWithEmailAndPassword(mail,password)
                .addOnSuccessListener {
                    Util.toastMakerS(requireContext(),getString(R.string.welcome_message))
                    val intent = Intent(requireContext(),AppActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }.addOnFailureListener {
                    Util.toastMakerL(requireContext(),getString(R.string.wrong_message))
                }

        }else {

            Util.inputError(binding.singInMailInput,getString(R.string.error_message))
            Util.inputError(binding.singInPasswordInput,getString(R.string.error_message))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}