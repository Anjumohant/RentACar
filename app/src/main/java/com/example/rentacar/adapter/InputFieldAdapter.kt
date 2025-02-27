package com.example.rentacar.adapter

import android.app.DatePickerDialog
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rentacar.databinding.ItemInputFieldBinding
import com.example.rentacar.model.InputField

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class InputFieldAdapter(private val inputFields: List<InputField>) :
    RecyclerView.Adapter<InputFieldAdapter.InputFieldViewHolder>() {

    class InputFieldViewHolder(val binding: ItemInputFieldBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InputFieldViewHolder {
        val binding = ItemInputFieldBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return InputFieldViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InputFieldViewHolder, position: Int) {
        val inputField = inputFields[position]
        holder.binding.inputLabel.text = inputField.label
        holder.binding.inputField.setText(inputField.value)
        holder.binding.inputField.inputType = inputField.inputType

        holder.binding.inputField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                inputFields[position].value = s.toString()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        if (inputField.isDatePicker) {
            holder.binding.inputField.isFocusable = false
            holder.binding.inputField.setOnClickListener {
                showDatePicker(holder, inputField)
            }
        }
    }

    override fun getItemCount() = inputFields.size

    private fun showDatePicker(holder: InputFieldViewHolder, inputField: InputField) {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            holder.itemView.context,
            { _, year, month, dayOfMonth ->
                val selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
                inputField.value = selectedDate
                holder.binding.inputField.setText(selectedDate) // Update UI
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }
    fun getInputValues(): List<InputField> {
        return inputFields
    }
}
