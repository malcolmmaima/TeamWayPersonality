package com.malcolmmaima.teamwaypersonality.ui.personalitytest.adapter

import android.os.Build
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.malcolmmaima.teamwaypersonality.data.models.Question
import com.malcolmmaima.teamwaypersonality.databinding.CardItemLayoutBinding

class CardAdapter : RecyclerView.Adapter<CardViewHolder>() {

    var quotes = arrayOf(
        "Be yourself; everyone else is already taken",
        "Be the change that you wish to see in the world.",
        "No one can make you feel inferior without your consent.",
        "Without music, life would be a mistake.",
        "We accept the love we think we deserve."
    )
    var author = arrayOf("Oscar Wilde", "Mahatma Gandhi", "Eleanor Roosevelt", "Friedrich Nietzsche", "Stephen Chbosky")

    override fun onCreateViewHolder(viewGroup: ViewGroup, itemType: Int): CardViewHolder {
        val binding = CardItemLayoutBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return CardViewHolder(binding)
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Question>() {
        override fun areItemsTheSame(
            oldItem: Question,
            newItem: Question
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Question,
            newItem: Question
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(viewHolder: CardViewHolder, position: Int) {
        val personalityData = asyncListDiffer.currentList[position]
        val tempText = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml("&quot;${personalityData.question}&quot;", Html.FROM_HTML_MODE_LEGACY).toString()
        } else {
            Html.fromHtml("&quot;${personalityData.question}&quot;").toString()
        }
        viewHolder.question.text = tempText
        viewHolder.optionA.text = personalityData.options[0].text
        viewHolder.optionB.text = personalityData.options[1].text
        viewHolder.optionC.text = personalityData.options[2].text
        viewHolder.optionD.text = personalityData.options[3].text
    }

    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)
    fun saveData(personalityQuestions: List<Question>) {
        asyncListDiffer.submitList(personalityQuestions)
    }
}
