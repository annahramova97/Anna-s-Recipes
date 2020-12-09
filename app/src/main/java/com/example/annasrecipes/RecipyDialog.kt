package com.example.annasrecipes

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import com.example.annasrecipes.data.Recipes
import kotlinx.android.synthetic.main.recipy_dialog.view.*


class RecipyDialog : DialogFragment() {

    interface RecipyHandler{
        fun recipyCreated(recipy: Recipes)
        fun recipyUpdated(recipy: Recipes)
    }

    lateinit var recipyHandler: RecipyHandler

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is RecipyHandler){
            recipyHandler = context
        } else {
            throw RuntimeException(
                "The Activity is not implementing the ItemHandler interface.")
        }
    }

    lateinit var etRecipyName: EditText
    lateinit var spinnerCategory: Spinner
    lateinit var etRecipyDescription: EditText


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(requireContext())

        dialogBuilder.setTitle("Item dialog")
        val dialogView = requireActivity().layoutInflater.inflate(
            R.layout.recipy_dialog, null
        )
        etRecipyName = dialogView.etRecipyName
        etRecipyDescription = dialogView.etRecipyDescription
        spinnerCategory = dialogView.spinnerCategory

        var categoryAdapter = ArrayAdapter.createFromResource(
            context!!,
            R.array.categories,
            android.R.layout.simple_spinner_item
        )
        categoryAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        spinnerCategory.adapter = categoryAdapter
        //spinnerCategory.setSelection(1)


        dialogBuilder.setView(dialogView)

        val arguments = this.arguments
        // if we are in EDIT mode
        if (arguments != null && arguments.containsKey(MainActivity.KEY_EDIT)) {
            val item = arguments.getSerializable(MainActivity.KEY_EDIT) as Recipes

            etRecipyName.setText(item.recipyName)
            etRecipyDescription.setText(item.recipyDescription)


            dialogBuilder.setTitle("Edit item")
        }

        dialogBuilder.setPositiveButton("Ok") {
                dialog, which ->
        }
        dialogBuilder.setNegativeButton("Cancel") {
                dialog, which ->
        }


        return dialogBuilder.create()
    }

    override fun onResume() {
        super.onResume()

        val positiveButton = (dialog as AlertDialog).getButton(Dialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener {
            if (etRecipyName.text.isNotEmpty()) {
                val arguments = this.arguments
                // IF EDIT MODE
                if (arguments != null && arguments.containsKey(MainActivity.KEY_EDIT)) {
                    handleItemEdit()
                } else {
                    handleItemCreate()
                }

                dialog!!.dismiss()
            } else {
                etRecipyName.error = "This field can not be empty"
            }
        }
    }

    private fun handleItemCreate() {
        recipyHandler.recipyCreated(
            Recipes(
                null,
                etRecipyName.text.toString(),
                spinnerCategory.selectedItemPosition,
                etRecipyDescription.text.toString(),
            false

            )
        )
    }

    private fun handleItemEdit() {
        val recipyToEdit = arguments?.getSerializable(
            MainActivity.KEY_EDIT
        ) as Recipes
        recipyToEdit.recipyName = etRecipyName.text.toString()
        recipyToEdit.recipyCategory = spinnerCategory.selectedItemPosition
        recipyToEdit.recipyDescription = etRecipyDescription.text.toString()


        recipyHandler.recipyUpdated(recipyToEdit)
    }


}