package com.example.shoetap

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoetap.databinding.FragmentFirstBinding
import com.example.shoetap.databinding.FragmentShoppingCartBinding
import com.example.shoetap.models.Shoe
import com.example.shoetap.models.ShoeProvider
import com.example.shoetap.models.ShoeTapApplication

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShoppingCartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShoppingCartFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    //agregué binding
    private var _binding: FragmentShoppingCartBinding? = null
    private val binding get() = _binding!!



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //inflé con binding
        _binding=FragmentShoppingCartBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // una nueva lista de zapatos que son los que estaran en el carro
         var zapatoEnCarro: MutableList<Shoe> = mutableListOf<Shoe>()
        //lista original
        var lista=ShoeProvider.ShoeList
        //los datos de sharedpreferences
        //TODO revisar esta llamada
        var preferencia= context?.getSharedPreferences("MyShoesDB",Context.MODE_PRIVATE)
        //el editor
        var editor= preferencia?.edit()

        //en la lista vamos a compararar con el original

        lista.forEach{

            //si en sharedpref esta el nombre
            if (preferencia!!.contains(it.name)){
                //lo agregamos a nuestra nueva lista
                zapatoEnCarro.add(it)

            }

        }
        // aca usamos el mismo adapter pero pasandole la nueva lista
        //la nueva lista es mutable porque va cambiando con el uso
        val adapter = ShoeListAdapter(zapatoEnCarro, true,context)
        binding.rv2.adapter = adapter
        binding.rv2.layoutManager = LinearLayoutManager(context)
        binding.rv2.setHasFixedSize(true)

        binding.fabclear.setOnClickListener{
            //borro toda la lista
            zapatoEnCarro.clear()
            //le aviso al adapter
            adapter.notifyDataSetChanged()
            //le digo al editor que borre todas las sharedpreferenes
            editor?.clear()
            //se aplican los cambios
            editor?.apply()

        }


    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ShoppingCartFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ShoppingCartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}