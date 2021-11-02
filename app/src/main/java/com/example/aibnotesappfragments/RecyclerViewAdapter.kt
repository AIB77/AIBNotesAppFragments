package com.example.aibnotesappfragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*

class RecyclerViewAdapter (private val listFragment: ListFragment): RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder> (){
    private var notes = emptyList<MyNote>()


    class ItemViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return  ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val pNote=notes[position]

        holder.itemView.apply {
            tvResult.text= pNote.noteText
            /*deletenote.setOnClickListener {
                activity.mainViewModel.deleteNote(pNote.id)
            }
            updatenote.setOnClickListener {
                activity.raiseDialog(pNote.id)
            }*/
            updatenote.setOnClickListener {
                /**
                 * We will use Shared Preferences here to pass the NoteId from our NoteAdapter to the Update Fragment
                 * There is a much cleaner way of doing this, but we will leave that up to you
                 * Hint: look into 'navArgs'
                 * Another option is 'Shared ViewModel'
                 */
                with(listFragment.sharedPreferences.edit()) {
                    putString("NoteId", pNote.id)
                    apply()
                }
                listFragment.findNavController().navigate(R.id.action_listFragment_to_updateFragment)
            }
            deletenote.setOnClickListener {
                listFragment.listViewModel.deleteNote(pNote.id)
            }



        }
    }

    override fun getItemCount() = notes.size

    fun update(TheNote: List<MyNote>){
        println("Updating Data")
        this.notes=TheNote
        notifyDataSetChanged()

    }
}