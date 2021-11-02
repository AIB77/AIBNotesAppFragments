package com.example.aibnotesappfragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ListFragment :Fragment() {
    private lateinit var rvNotes: RecyclerView
    private lateinit var rvAdapter: RecyclerViewAdapter
    private lateinit var editText: EditText
    private lateinit var submitBtn: Button

    lateinit var listViewModel: ListViewModel
    private lateinit var notes: List<MyNote>

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_list, container, false)


        sharedPreferences = requireActivity().getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)

        notes = arrayListOf()

        listViewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        listViewModel.getNotes().observe(viewLifecycleOwner, {
                notes -> rvAdapter.update(notes)
        })

        editText = view.findViewById(R.id.tvNewNote)
        submitBtn = view.findViewById(R.id.btSubmit)
        submitBtn.setOnClickListener {
            listViewModel.addNote(MyNote("", editText.text.toString()))
            editText.text.clear()
            editText.clearFocus()
            Toast.makeText(context,"Note Updated",Toast.LENGTH_LONG).show()
        }

        rvNotes = view.findViewById(R.id.rvNotes)
        rvAdapter = RecyclerViewAdapter(this)
        rvNotes.adapter = rvAdapter
        rvNotes.layoutManager = LinearLayoutManager(requireContext())

        listViewModel.getData()

        return view
    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            listViewModel.getData()
        }
    }

}