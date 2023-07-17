package com.example.shoetap

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoetap.databinding.ItemViewBinding
import com.example.shoetap.models.Prefs
import com.example.shoetap.models.Shoe
import com.example.shoetap.models.ShoeProvider
import java.util.prefs.Preferences

// al adapter le puse un boolean para hacer el boton visible o no
//entregar contexto a la clase
class ShoeListAdapter (private var returnItemList: MutableList<Shoe>?, val esvisible: Boolean, val context: Context?=null) : RecyclerView.Adapter<ShoeListAdapter.ShoeListViewHolder>() {

    var onItemClick : ((Shoe) -> Unit)? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoeListViewHolder {
        val binding: ItemViewBinding =
            ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShoeListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShoeListViewHolder, position: Int) {
        val item = returnItemList!![position]
        //con el contexto llamamos a las sharedpreferences
        val elegida=context?.getSharedPreferences("MyShoesDB",Context.MODE_PRIVATE)
        var editar= elegida?.edit()


        Glide.with(holder.image_view).load(item.url).into(holder.image_view)
        holder.title_view.text = item.name.toString()
        holder.descript_view.text = item.description.toString()
        holder.price_view.text = item.price.toString()
        holder.itemView.setOnClickListener{
            onItemClick?.invoke( item )
        }
        //visibilidad del boton
        if (esvisible){
            holder.boton.visibility=View.VISIBLE
            //aca voy a intentar darle funcion al boton
            holder.boton.setOnClickListener{
                returnItemList?.remove(item)
                notifyItemRemoved(position)
                //borramos de las sharedpreferences segun el nombre
                editar?.remove(item.name)?.apply()


            }
        }
    }

    override fun getItemCount(): Int = returnItemList!!.size

    class ShoeListViewHolder(binding: ItemViewBinding) :
        RecyclerView.ViewHolder(binding.getRoot()) {

        var title_view: TextView
        var image_view: ImageView
        var descript_view: TextView
        var price_view: TextView
        //se crea el boton
        var boton: Button



        init {
            image_view = binding.image
            title_view = binding.title
            descript_view = binding.description
            price_view = binding.price
            //se inicializa
            boton= binding.bteliminar
        }

    }

    interface PassElementSelected {
        //esta interfaz guardara el string seleccionado
        fun passElement(url: String?, title: String?, description: String?, price: String?)
    }

    //el escuchador para la interfaz, estatico para que no modifique el dato
    private val listener: PassElementSelected? = null

}

