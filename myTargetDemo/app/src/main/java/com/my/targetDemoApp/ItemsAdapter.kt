package com.my.targetDemoApp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_main.view.*

class ItemsAdapter : RecyclerView.Adapter<ItemsAdapter.MainActivityViewHolder>() {

    private var adItems: ArrayList<ListItem>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainActivityViewHolder {
        return MainActivityViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false))
    }

    override fun getItemCount(): Int {
        return adItems?.size ?: 0
    }

    override fun onBindViewHolder(holder: MainActivityViewHolder, position: Int) {
        adItems?.get(position)?.let { holder.bind(it) }
    }

    override fun onViewRecycled(holder: MainActivityViewHolder) {
        super.onViewRecycled(holder)
        holder.containerView.setOnClickListener(null)
    }

    fun setItems(types: ArrayList<ListItem>) {
        adItems = types
        notifyDataSetChanged()
    }

    fun addItem(listItem: ListItem) {
        adItems?.let {
            it.add(listItem)
            notifyItemInserted(it.size)
        }
    }

    fun deleteItem(listItem: ListItem) {
        adItems?.let {
            val pos = it.indexOf(listItem)
            if (pos >= 0) {
                it.remove(listItem)
                notifyItemRemoved(pos)
            }
        }
    }

    class MainActivityViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(listItem: ListItem) {
            containerView.tv_title.text = listItem.title
            containerView.tv_description.text = listItem.description
            val deleteListener = listItem.deleteListener
            if (deleteListener != null) {
                containerView.btn_delete.visibility = View.VISIBLE
                containerView.btn_delete.setOnClickListener { deleteListener.invoke(listItem) }
            }
            else {
                containerView.btn_delete.visibility = View.GONE
                containerView.btn_delete.setOnClickListener(null)
            }

            containerView.setOnClickListener { listItem.clickListener.invoke() }
        }
    }

    class ListItem(var clickListener: () -> Unit, var title: String, var description: String, var deleteListener: ((ListItem) -> Unit)? = null)
}

