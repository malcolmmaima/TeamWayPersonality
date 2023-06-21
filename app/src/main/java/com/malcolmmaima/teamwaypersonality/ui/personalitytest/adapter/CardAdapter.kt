package com.malcolmmaima.teamwaypersonality.ui.personalitytest.adapter

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.malcolmmaima.teamwaypersonality.data.models.Option
import com.malcolmmaima.teamwaypersonality.data.models.Question
import com.malcolmmaima.teamwaypersonality.databinding.CardItemLayoutBinding

class CardAdapter : RecyclerView.Adapter<CardViewHolder>() {

    private var optionClickCallBack: ((Option) -> Unit)? = null
    private val selectedOptionsMap: MutableMap<Int, Option> = HashMap()

    fun onItemClick(onItemClick: (Option) -> Unit) {
        this.optionClickCallBack = onItemClick
    }

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
        val tempText =
            Html.fromHtml("&quot;${personalityData.question}&quot;", Html.FROM_HTML_MODE_LEGACY).toString()
        viewHolder.question.text = tempText
        viewHolder.optionA.text = personalityData.options[0].text
        viewHolder.optionB.text = personalityData.options[1].text
        viewHolder.optionC.text = personalityData.options[2].text
        viewHolder.optionD.text = personalityData.options[3].text

        viewHolder.radioGroup.setOnCheckedChangeListener(null)

        val selectedOption = selectedOptionsMap[position]
        if (selectedOption != null) {
            val selectedOptionId = selectedOption.id
            when (selectedOptionId) {
                "A" -> viewHolder.optionA.isChecked = true
                "B" -> viewHolder.optionB.isChecked = true
                "C" -> viewHolder.optionC.isChecked = true
                "D" -> viewHolder.optionD.isChecked = true
            }
        } else {
            viewHolder.radioGroup.clearCheck()
        }

        viewHolder.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val selectedOption = when (checkedId) {
                viewHolder.optionA.id -> personalityData.options[0]
                viewHolder.optionB.id -> personalityData.options[1]
                viewHolder.optionC.id -> personalityData.options[2]
                viewHolder.optionD.id -> personalityData.options[3]
                else -> null
            }

            if (selectedOption != null) {
                selectedOptionsMap[position] = selectedOption
                optionClickCallBack?.invoke(selectedOption)
            }
        }
    }

    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)
    fun saveData(personalityQuestions: List<Question>) {
        asyncListDiffer.submitList(personalityQuestions)
    }
}

