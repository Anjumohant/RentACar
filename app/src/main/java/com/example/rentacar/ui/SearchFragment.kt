package com.example.rentacar.ui
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import android.widget.Button
import android.net.Uri
import android.widget.AutoCompleteTextView
import android.app.DatePickerDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.rentacar.R
import com.example.rentacar.viewmodel.SearchViewModel
import java.util.Calendar
import android.content.Intent
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rentacar.adapter.InputFieldAdapter
import com.example.rentacar.databinding.FragmentSearchBinding
import com.example.rentacar.model.InputField
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel
    private lateinit var binding: FragmentSearchBinding
    private lateinit var inputAdapter: InputFieldAdapter
    private lateinit var inputFields: List<InputField>
    private lateinit var searchButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        setupRecyclerView()
        setupSearchButton()

        return binding.root
    }
private fun setupSearchButton() {

    binding.searchButton.setOnClickListener {
        val inputvalues=inputAdapter.getInputValues()

        val pickup = inputvalues[0].value.toString()
        val dropOff = inputvalues[1].value.toString()
        val pickupDate = inputvalues[2].value.toString()
        val dropOffDate = inputvalues[3].value.toString()

        if (pickup.isEmpty() || pickupDate.isEmpty() || dropOffDate.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter required fields!", Toast.LENGTH_SHORT)
                .show()
            return@setOnClickListener
        }

        val kayakUrl = viewModel.generateKayakUrl(pickup, dropOff, pickupDate, dropOffDate)
        Log.d("App logger", "URL : ${kayakUrl}")
        openKayak(kayakUrl)
    }
}

       // return view

    private fun setupRecyclerView() {
        inputFields = listOf(
            InputField("Pickup Location", "", InputType.TYPE_CLASS_TEXT),
            InputField("Drop-off Location", "", InputType.TYPE_CLASS_TEXT),
            InputField("Pickup Date", "", InputType.TYPE_CLASS_DATETIME, isDatePicker = true),
            InputField("Drop-off Date", "", InputType.TYPE_CLASS_DATETIME, isDatePicker = true)
        )

        inputAdapter = InputFieldAdapter(inputFields)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = inputAdapter
    }


    private fun openKayak(url: String) {
        var loadurl:String
       loadurl=URLEncoder.encode(url,StandardCharsets.UTF_8.toString())
        Log.d("App logger","URL2 : ${loadurl}")
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        Log.d("App logger","URL3 : ${url}")
        startActivity(intent)
    }
}
