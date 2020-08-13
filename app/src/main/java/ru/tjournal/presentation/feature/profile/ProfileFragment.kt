package ru.tjournal.presentation.feature.profile

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import ru.tjournal.MainActivity
import ru.tjournal.R
import ru.tjournal.databinding.FragmentProfileBinding
import ru.tjournal.util.CameraBottomSheet

class ProfileFragment : Fragment() {

    companion object {
        private const val CAMERA_BOTTOM_SHEET_TAG = "CameraTag"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    private val profileViewModel: ProfileViewModel by viewModels()

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val source = (activity as MainActivity).service
        val savedData = (activity as MainActivity).savedData
        profileViewModel.onCreate(source, savedData)

        setListeners()

        getUserData()
    }

    private fun setListeners() {
        binding.btnUnauthorized.setOnClickListener {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    REQUIRED_PERMISSIONS,
                    REQUEST_CODE_PERMISSIONS
                )
            }
        }

        binding.btnLogout.setOnClickListener {
            profileViewModel.logout()
            inVisibleUserData()
        }
    }

    private fun getUserData() {
        profileViewModel.getUserData().observe(viewLifecycleOwner, Observer { data ->
            if (data.first.isNotEmpty() || data.second.isNotEmpty()) {
                setUserData(data.first, data.second)
            }
        })
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireContext(), it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun startCamera() {
        val dialog = CameraBottomSheet(onSuccess = { token ->
            authorization(token)
        }, onError = { error ->
            showError(error)
        })
        dialog.show(parentFragmentManager, CAMERA_BOTTOM_SHEET_TAG)
    }

    private fun authorization(token: String) {
        profileViewModel.auth(token).observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful) setUserData(
                response.body()?.result?.name,
                response.body()?.result?.avatarUrl
            )
            else showError(response.errorBody()?.string() ?: "")
        })
    }

    private fun setUserData(name: String?, avatarUrl: String?) {
        visibleUserData()
        binding.tvUserName.text = name

        Glide.with(this)
            .load(avatarUrl)
            .placeholder(R.drawable.ic_profile)
            .into(binding.ivUserAvatar)
    }

    private fun visibleUserData() {
        binding.btnUnauthorized.visibility = View.GONE
        binding.groupAuthorized.visibility = View.VISIBLE
    }

    private fun inVisibleUserData() {
        binding.btnUnauthorized.visibility = View.VISIBLE
        binding.groupAuthorized.visibility = View.GONE
    }

    private fun showError(error: String) {
        AlertDialog.Builder(requireContext())
            .setMessage(error)
            .setPositiveButton(getString(R.string.ok)) { _, _ -> }
            .show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.permission_not_granted),
                    Toast.LENGTH_SHORT
                ).show()
                requireActivity().finish()
            }
        }
    }
}