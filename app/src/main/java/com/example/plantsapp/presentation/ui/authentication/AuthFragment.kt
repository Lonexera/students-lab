package com.example.plantsapp.presentation.ui.authentication

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.plantsapp.R
import com.example.plantsapp.databinding.FragmentAuthBinding
import com.example.plantsapp.presentation.ui.tasksfordays.TasksForDaysFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthFragment : Fragment(R.layout.fragment_auth) {

    private val binding: FragmentAuthBinding by viewBinding(FragmentAuthBinding::bind)
    private val viewModel: AuthViewModel by viewModels()
    @Inject
    lateinit var gso: GoogleSignInOptions
    private val googleSignInClient by lazy {
                GoogleSignIn.getClient(requireActivity(), gso)
    }
    // TODO check this thing in next iteration of pull request
    private val googleSignInLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { activityResult ->
            activityResult.data?.let { viewModel.onSignInResult(it) }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel) {
            authResult.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { result ->
                    when (result) {
                        is AuthViewModel.AuthResult.NavigateToTasks -> {
                            requireActivity().supportFragmentManager.commit {
                                replace(R.id.fragment_container, TasksForDaysFragment())
                            }
                        }
                        is AuthViewModel.AuthResult.AuthError -> {
                            Toast.makeText(requireContext(), result.errorId, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }

        with(binding) {
            btnGoogleSignIn.setOnClickListener {
                googleSignInLauncher.launch(googleSignInClient.signInIntent)
            }
        }
    }
}
