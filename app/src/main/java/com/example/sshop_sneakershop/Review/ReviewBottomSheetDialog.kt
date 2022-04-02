package com.example.sshop_sneakershop.Review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sshop_sneakershop.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ReviewBottomSheetDialog: BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_bottom_review, container, false)
        return view
    }
}