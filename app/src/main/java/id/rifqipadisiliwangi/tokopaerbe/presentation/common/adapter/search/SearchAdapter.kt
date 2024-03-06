package id.rifqipadisiliwangi.tokopaerbe.presentation.common.adapter.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.rifqipadisiliwangi.tokopaerbe.databinding.ItemSearchSuggestionBinding

class SearchAdapter(
    private val search: List<String>,
    private val onClickLister: (String) -> Unit
) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSearchSuggestionBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fruit = search[position]
        holder.bind(fruit)
    }

    override fun getItemCount(): Int {
        return search.size
    }

    inner class ViewHolder(private val binding: ItemSearchSuggestionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(search: String) {
            binding.tvSearch.text = search
            binding.root.setOnClickListener {
                onClickLister(search)
            }
        }
    }
}