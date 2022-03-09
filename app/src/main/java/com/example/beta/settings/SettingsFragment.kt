package com.example.beta.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.beta.databinding.FragmentSettingsBinding
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : Fragment() {
    companion object {
        const val TAG = "SettingsFragment"

    }

    private lateinit var binding: FragmentSettingsBinding
    private var userName: String? = null
    private var emailAddress: String? = null
    private var profileSettings: String? = null
    private var travelPreference1: String? = null
    private var travelPreference2: String? = null
    private var travelPreference3: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        userName="${FirebaseAuth.getInstance().currentUser?.displayName}!"

        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //save userName and profileSettings and travelPreferences to database

        binding.returnBtn.setOnClickListener {
            addUserName(it)
            val settingsToProfile =
                SettingsFragmentDirections.actionSettingsFragmentToProfileFragment()
            findNavController().navigate(settingsToProfile)


        }
    }

    private fun addUserName(view: View) {
        binding.apply {
//            userProfileEntity?.userName = etUserName.text.toString()
            invalidateAll()
            binding.etUserName.visibility = View.GONE
            binding.returnBtn.visibility = View.GONE
            binding.etUserName.visibility = View.VISIBLE
        }
//add top destinations to profile
//use top destinations for machine learning
//add action to make profile public
    }
}
