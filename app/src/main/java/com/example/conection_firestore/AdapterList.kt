package com.example.conection_firestore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


class AdapterList(val mCtx: Context, val layoutResId: Int, val userList: List<data>) :
    ArrayAdapter<data>(mCtx, layoutResId, userList) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val textViewNome = view.findViewById<TextView>(R.id.textViewNome2)
        val textViewValor = view.findViewById<TextView>(R.id.textViewIdade2)

        val data = userList[position]

        textViewNome.text = data.nome
        textViewValor.text = data.idade

        return view
    }


}