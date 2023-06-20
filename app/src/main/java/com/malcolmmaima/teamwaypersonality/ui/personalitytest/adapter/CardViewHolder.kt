package com.malcolmmaima.teamwaypersonality.ui.personalitytest.adapter

import android.widget.RadioButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.malcolmmaima.teamwaypersonality.databinding.CardItemLayoutBinding

class CardViewHolder(var view: CardItemLayoutBinding) : RecyclerView.ViewHolder(view.root) {

    var question = view.question
    var optionA = view.optionA
    var optionB = view.optionB
    var optionC = view.optionC
    var optionD = view.optionD

    var radioGroup = view.radioGroup
}