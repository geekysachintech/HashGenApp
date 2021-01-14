package com.example.hashgenerator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.viewbinding.ViewBinding
import com.example.hashgenerator.databinding.FragmentSuccessBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SuccessFragment : Fragment()
{
    private val args : SuccessFragmentArgs by navArgs()

    private var _binding : FragmentSuccessBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        _binding = FragmentSuccessBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        binding.OutputTextView.text = args.hash
        binding.copyButton.setOnClickListener {
            onCopyClicked(args.hash)
        }

        return binding.root
    }

    private fun onCopyClicked(value: String)
    {
        val clipboardManager = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("Encrypted Text", value)
        clipboardManager.setPrimaryClip(clipData)
        showSnackbar("Copied")
    }

    private fun showSnackbar(message: String)
    {
        val snackBar = Snackbar.make(
            binding.successFragmentRootLayout,
            message,
            Snackbar.LENGTH_SHORT
        )
        snackBar.setAction("Ok"){}
        snackBar.show()
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }
}