package com.example.shoetap


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoetap.databinding.FragmentFirstBinding
import com.example.shoetap.models.Shoe
import com.example.shoetap.models.ShoeProvider


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(),ShoeListAdapter.PassElementSelected {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ShoeListAdapter(ShoeProvider.ShoeList,false)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.setHasFixedSize(true)

        adapter.onItemClick = {
            val bundle = Bundle()
            bundle.putString("url", it.url)
            bundle.putString("title", it.name)
            bundle.putString("description", it.description)
            bundle.putString("price", it.price)
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun passElement(url: String?, title: String?, description: String?, price: String?) {
        val bundle = Bundle()
        bundle.putString("url", url)
        bundle.putString("title", title)
        bundle.putString("description", description)
        bundle.putString("price", price)

        val navController: NavController = Navigation.findNavController(requireActivity(), R.id.recyclerView)
        navController.navigate(
            R.id.action_FirstFragment_to_SecondFragment,
            bundle
        )
    }
}