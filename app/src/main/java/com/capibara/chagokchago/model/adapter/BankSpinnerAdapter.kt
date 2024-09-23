package com.capibara.chagokchago.model.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.capibara.chagokchago.R
import com.capibara.chagokchago.model.BankItem
class BankSpinnerAdapter(context: Context, private val resource: Int, private val items: List<BankItem>)
    : ArrayAdapter<BankItem>(context, resource, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    private fun createItemView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)

        val imageViewBank = view.findViewById<ImageView>(R.id.imageViewBank)
        val textViewBankName = view.findViewById<TextView>(R.id.textViewBankName)

        val bankItem = items[position]

        imageViewBank.setImageResource(bankItem.imageResId)
        textViewBankName.text = bankItem.name

        return view
    }
}