package com.example.moviepedia.dialog

import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog
import com.example.moviepedia.LoginActivity
import com.example.moviepedia.R
import com.example.moviepedia.db.FirestoreUtils

class ListBottomSheet {
  val TAG = "ListBottomSheet"

  fun createDialog(context: Context, layoutInflater: LayoutInflater) {
    val mBottomSheetDialog = RoundedBottomSheetDialog(context)
    val sheetView = layoutInflater.inflate(R.layout.list_creation_bottom_sheet, null)
    mBottomSheetDialog.setContentView(sheetView)
    mBottomSheetDialog.show()

    menageCreateButton(mBottomSheetDialog)
  }

  private fun menageCreateButton(mBottomSheetDialog: RoundedBottomSheetDialog) {
    mBottomSheetDialog.findViewById<TextView>(R.id.btn_create_list)!!.setOnClickListener {
      val list_name = mBottomSheetDialog.findViewById<EditText>(R.id.tw_bs_list_name)!!.text.toString()
      if (list_name != "")
        FirestoreUtils().createEmptyList(LoginActivity.getUser(), list_name)
      else {
        // TODO popup "List name must be not null
      }
      mBottomSheetDialog.dismiss()
    }
  }
}