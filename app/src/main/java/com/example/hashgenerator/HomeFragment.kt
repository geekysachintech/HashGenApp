package com.example.hashgenerator

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.hashgenerator.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : Fragment()
{

    private val HomeViewModel : HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        binding.generateButton.setOnClickListener {
            onGenerateClick()
        }

        return binding.root
    }

    private fun onGenerateClick()
    {

        if (binding.editTextMainInput.text.isEmpty())
        {
            showSnackbar("Field Empty")
        }
        else
        {
            lifecycleScope.launch {
                applyAnimations()
                navigateToSuccess(getHashValue())
            }
        }

    }

    private fun showSnackbar(message: String)
    {
        val snackBar = Snackbar.make(
            binding.rootLayout,
            message,
            Snackbar.LENGTH_SHORT
        )
        snackBar.setAction("Okay"){}
        snackBar.show()
    }

    private fun getHashValue() : String
    {
        val algorithm = binding.autoCompleteTextView.text.toString()
        val plaintext = binding.editTextMainInput.text.toString()
        return HomeViewModel.getHash(plaintext, algorithm)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater)
    {
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        if(item.itemId == R.id.clear_text)
        {
            binding.editTextMainInput.text.clear()
            showSnackbar("Cleared")
            return true
        }
        return true
    }

    private suspend fun applyAnimations()
    {
        binding.titleTextView.animate().alpha(0f).duration = 400L
        binding.generateButton.animate().alpha(0f).duration = 400L
        binding.generateButton.isClickable = false

        binding.textInputLayout.animate()
            .alpha(0f)
            .translationXBy(1200f)
            .duration = 400L

        binding.editTextMainInput.animate()
            .alpha(0f)
            .translationXBy(-1200f)
            .duration = 400L

        delay(300)

        binding.successAnimation.animate()
            .alpha(1f)
            .rotationBy(720f)
            .duration = 600L
        binding.successAnimation.animate()
            .scaleXBy(800f)
            .scaleYBy(900f)
            .duration = 800L

        delay(200)
        binding.successImageView.animate().alpha(1f).duration = 300L
        binding.successImageView.animate().rotationBy(-360f).duration = 300L

        delay(1500)

    }

    override fun onResume()
    {
        super.onResume()
        val hashAlgorithm = resources.getStringArray(R.array.Hash_Algorithm)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, hashAlgorithm)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
    }


    private fun navigateToSuccess(hash:String)
    {
        val directions = HomeFragmentDirections.fromHomeToSuccess(hash)
        findNavController().navigate(directions)
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }


}