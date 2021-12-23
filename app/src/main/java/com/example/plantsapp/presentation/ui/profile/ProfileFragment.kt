package com.example.plantsapp.presentation.ui.profile

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.plantsapp.R
import com.example.plantsapp.databinding.FragmentProfileBinding
import com.example.plantsapp.domain.model.User
import com.example.plantsapp.presentation.ui.authentication.AuthFragment
import com.example.plantsapp.presentation.ui.utils.loadPicture
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding: FragmentProfileBinding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel) {
            user.observe(viewLifecycleOwner) { user ->
                showUserProfile(user)
            }

            navigateToAuth.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let {
                    requireActivity().supportFragmentManager.commit {
                        replace(R.id.fragment_container, AuthFragment())
                    }
                }
            }
        }
    }

    private fun showUserProfile(user: User) {
        with(binding) {
            ivProfile.loadPicture(
                picture = user.profilePicture,
                placeholder = R.drawable.placeholder_person
            )

            tvProfileName.text = user.name
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_profile_appbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sign_out -> {
                showDialogSignOutConfirm()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDialogSignOutConfirm() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.title_sign_out_dialog_confirmation)
            .setMessage(R.string.msg_sign_out_dialog_confirmation)
            .setPositiveButton(R.string.title_dialog_confirmation_btn_positive) { _, _ ->
                viewModel.onSignOutClick()
            }
            .setNegativeButton(R.string.title_dialog_confirmation_btn_negative) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
}
