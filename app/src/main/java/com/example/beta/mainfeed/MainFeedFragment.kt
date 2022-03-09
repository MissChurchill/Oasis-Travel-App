package com.example.beta.mainfeed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beta.R
import com.example.beta.databinding.FragmentMainFeedBinding
import com.example.beta.login.LoginViewModel
import com.firebase.ui.auth.AuthUI
import com.google.firebase.firestore.FirebaseFirestore


class MainFeedFragment : Fragment() {
    private lateinit var binding: FragmentMainFeedBinding

    companion object {
        const val TAG = "MainFeedFragment"
    }
        // Get a reference to the ViewModel scoped to this Fragment
    private val viewModel by viewModels<LoginViewModel>()


        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

            binding = FragmentMainFeedBinding.inflate(inflater, container, false)
            return binding.root

            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(activity)
                // Inflate the layout for this fragment


                fetchData()
            }
        }

    private fun fetchData() {
        FirebaseFirestore.getInstance().collection("users")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    //Let's create our model and an adapter for working with our data
                    val user = documents.toObjects(TravelPosts::class.java)
                    binding.recyclerView.adapter = MainFeedAdapter(activity!!, user)
                }
            }
            .addOnFailureListener {

                //We need a toast for an error
                showToast(activity,"An error occurred: ${it.localizedMessage}")
            }
    }

    private fun showToast(activity: FragmentActivity?, message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT)
            .show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            when (authenticationState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> Log.i(TAG, "Authenticated")


                // If the user is not logged in, they should not be able to set any preferences,
                // so navigate them to the main fragment to Login
                LoginViewModel.AuthenticationState.UNAUTHENTICATED -> navController.navigate(
                    R.id.mainFragment
                )
                else -> Log.e(
                    TAG, "New $authenticationState state that doesn't require any UI change"
                )
            }

        })

        view.findViewById<ImageView>(R.id.main_profile_icon).setOnClickListener {


            val mainToProfileFragment =
                MainFeedFragmentDirections.actionMainFeedFragmentToProfileFragment()
            findNavController().navigate(mainToProfileFragment)

        }
        view.findViewById<TextView>(R.id.logoutBtn).text = getString(R.string.logout_button_text)
        view.findViewById<TextView>(R.id.logoutBtn).setOnClickListener {
            AuthUI.getInstance().signOut(requireContext())
            //signs out user when clicked
        }

    }
}
