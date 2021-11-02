package com.example.aibnotesappfragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UpdateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdateFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        // We use 'requireActivity()' to access MainActivity here
        val sharedPreferences = requireActivity().getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)

        val updateViewModel = ViewModelProvider(this).get(UpdateViewModel::class.java)

        val etNote = view.findViewById<EditText>(R.id.etNoteUpdate)
        val btUpdate = view.findViewById<Button>(R.id.btNoteUpdate)

        btUpdate.setOnClickListener {

            val noteId = sharedPreferences.getString("NoteId", "").toString()
            updateViewModel.editNote(noteId, etNote.text.toString())
            with(sharedPreferences.edit()) {
                putString("NoteId", etNote.text.toString())
                apply()
            }
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }

        return view
    }
}