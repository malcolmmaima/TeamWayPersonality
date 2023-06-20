package com.malcolmmaima.teamwaypersonality.ui.personalitytest.adapter

import android.widget.RadioButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.malcolmmaima.teamwaypersonality.databinding.CardItemLayoutBinding

class CardViewHolder(var view: CardItemLayoutBinding) : RecyclerView.ViewHolder(view.root) {

    var question: AppCompatTextView = view.question
    var optionA: RadioButton = view.optionA
    var optionB: RadioButton = view.optionB
    var optionC: RadioButton = view.optionC
    var optionD: RadioButton = view.optionD
}