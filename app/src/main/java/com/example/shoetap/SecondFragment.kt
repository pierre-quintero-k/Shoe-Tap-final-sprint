package com.example.shoetap

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.shoetap.databinding.FragmentSecondBinding
import com.example.shoetap.models.Shoe
import com.example.shoetap.models.ShoeTapApplication.Companion.prefs

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private var mParam1: String? = null
    private var mParam2: String? = null
    private var mParam3: String? = null
    private var mParam4: String? = null
    private var ARG_PARAM_URL = "url"
    private var ARG_PARAM_TITLE = "title"
    private var ARG_PARAM_DESCRIPTION = "description"
    private var ARG_PARAM_PRICE = "price"
    var shoeSaved: Shoe? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments?.getString(ARG_PARAM_URL);
            mParam2 = arguments?.getString(ARG_PARAM_TITLE);
            mParam3 = arguments?.getString(ARG_PARAM_DESCRIPTION);
            mParam4 = arguments?.getString(ARG_PARAM_PRICE);
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        Glide.with(binding.image2).load(mParam1).into(binding.image2)
        binding.title2.text = mParam2.toString()
        binding.description2.text = mParam3.toString()
        binding.price2.text = mParam4.toString()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shoeSaved = Shoe(binding.title2.text.toString(), binding.description2.text.toString(),
            binding.price2.text.toString(),binding.image2.toString())
        initUI()
        binding.btnGoToCart.setOnClickListener{
            findNavController().navigate(R.id.action_SecondFragment_to_shoppingCartFragment)
        }

    }

    private fun initUI() {
        binding.btnAddToCart.setOnClickListener {
            accessToDetail()
            findNavController().navigate(R.id.action_SecondFragment_to_shoppingCartFragment)
        }
    }

    private fun accessToDetail() {
        shoeSaved?.let { prefs.saveShoe(it) }
    //TODO llamar a esto
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}