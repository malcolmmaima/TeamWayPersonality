package com.malcolmmaima.teamwaypersonality.ui.personalitytest

import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.malcolmmaima.teamwaypersonality.R
import com.malcolmmaima.teamwaypersonality.data.models.Option
import com.malcolmmaima.teamwaypersonality.data.models.Question
import com.malcolmmaima.teamwaypersonality.databinding.ActivityMainBinding
import com.malcolmmaima.teamwaypersonality.ui.personalitytest.adapter.CardAdapter
import com.malcolmmaima.teamwaypersonality.ui.personalitytest.viewmodel.MainViewModel
import com.malcolmmaima.teamwaypersonality.utils.OnSwipeTouchListener
import com.malcolmmaima.teamwaypersonality.utils.calculatePercentage
import com.malcolmmaima.teamwaypersonality.utils.snackbar
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.RewindAnimationSetting
import com.yuyakaido.android.cardstackview.StackFrom
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PersonalityActivity : AppCompatActivity(), CardStackListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var layoutManager: CardStackLayoutManager
    private val viewModel: MainViewModel by viewModels()
    private lateinit var cardAdapter: CardAdapter

    // selected options for each question
    private var selectedOptions = mutableListOf<Option>()
    private var introvertCount = 0
    private var extrovertCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fetchPersonalityQuestions()
    }

    private fun initializeCardStackView(personalityQuestions: List<Question>) {
        val rewindSetting = RewindAnimationSetting.Builder()
            .setDirection(Direction.Top)
            .setDuration(200)
            .setInterpolator(DecelerateInterpolator())
            .build()

        cardAdapter = CardAdapter()
        layoutManager = CardStackLayoutManager(applicationContext, this)
        layoutManager.setDirections(arrayListOf(Direction.Right, Direction.Left))
        layoutManager.setCanScrollHorizontal(true)
        layoutManager.setVisibleCount(5)
        layoutManager.setStackFrom(StackFrom.Bottom)
        layoutManager.setTranslationInterval(5f)
        layoutManager.setRewindAnimationSetting(rewindSetting)
        layoutManager.setCanScrollVertical(false)
        layoutManager.setMaxDegree(90f)
        binding.cards.layoutManager = layoutManager
        binding.cards.adapter = cardAdapter
        cardAdapter.saveData(personalityQuestions)

        cardAdapter.onItemClick { selectedItem ->
            selectedOptions.add(selectedItem)
            binding.cards.postDelayed({
                binding.cards.swipe()
            }, 1000)
        }

        handleCardChanges()

        binding.restack.setOnClickListener {
            if (layoutManager.topPosition != 0) {
                binding.cards.smoothScrollToPosition(0)

                // Refetch the questions
                fetchPersonalityQuestions()
            }
        }

        binding.previousCard.visibility = View.GONE
        binding.previousCard.setOnClickListener {
            binding.cards.rewind()
        }

        binding.container.setOnTouchListener(object : OnSwipeTouchListener(this@PersonalityActivity) {
            override fun onSwipeDown() {
                fetchPersonalityQuestions()
            }
        })
    }

    private fun fetchPersonalityQuestions() {
        // Reset the counts
        introvertCount = 0
        extrovertCount = 0

        binding.imageView.visibility = View.GONE
        binding.placeholderText.visibility = View.GONE

        viewModel.fetchPersonalityQuestions()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.personalityQuestions.collectLatest { personalityQuestions ->
                    // Update the UI.
                    initializeCardStackView(personalityQuestions.questions)
                }
            }
        }

        // observer for error
        viewModel.errorMessage.observe(this) { errorMessage ->
            if (errorMessage != null) {
                binding.imageView.visibility = View.GONE
                binding.placeholderText.visibility = View.GONE
                binding.placeholderText.text = getString(R.string.error_message)
                binding.root.snackbar(errorMessage)
            }
        }
    }

    override fun onCardRewound() {
        handleCardChanges()
    }

    override fun onCardCanceled() {

    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {

    }

    override fun onCardSwiped(direction: Direction?) {
        handleCardChanges()
    }

    private fun handleCardChanges() {
        binding.progressBar.progress = calculatePercentage(layoutManager.topPosition, layoutManager.itemCount)
        binding.progressBar.progressDrawable.setTint(resources.getColor(R.color.green))

        if (layoutManager.topPosition == 0) {
            binding.restack.hide()
            //binding.previousCard.visibility = View.GONE
        } else {
            binding.restack.show()
            //binding.previousCard.visibility = View.VISIBLE
        }
        binding.placeholder.visibility =
            if (layoutManager.topPosition == (layoutManager.itemCount)) View.VISIBLE else View.GONE
        binding.cards.visibility = if (layoutManager.topPosition == (layoutManager.itemCount)) View.GONE else View.VISIBLE

        // Reset the counts
        introvertCount = 0
        extrovertCount = 0

        // Iterate through the selected options and calculate introvert and extrovert counts
        var extraversionSum = 0.0
        for (option in selectedOptions) {
            extraversionSum += option.traits.extraversion
        }

        val extraversionAverage = extraversionSum / selectedOptions.size

        if (extraversionAverage > 0.5) {
            // The person is more extroverted
            extrovertCount++
        } else if (extraversionAverage < 0.5) {
            // The person is more introverted
            introvertCount++
        }


        // Check if the user has answered all the questions
        if (layoutManager.topPosition == (layoutManager.itemCount)) {
            // Determine the user's personality type based on the counts
            val personalityType = when {
                introvertCount > extrovertCount -> "Introvert"
                extrovertCount > introvertCount -> "Extrovert"
                else -> "None"
            }

            // change image of id android:id="@+id/imageView" to the personality type
            binding.imageView.visibility = View.VISIBLE
            binding.placeholderText.visibility = View.VISIBLE

            if(selectedOptions.size <= 4) {
                binding.imageView.visibility = View.GONE
                binding.placeholderText.text = getString(R.string.retry_text)
            } else {
                when (personalityType) {
                    "Introvert" -> {
                        binding.imageView.setImageResource(R.drawable.introvert)
                        binding.placeholderText.text = getString(R.string.introvert_text)
                    }
                    "Extrovert" -> {
                        binding.imageView.setImageResource(R.drawable.extrovert)
                        binding.placeholderText.text = getString(R.string.extrovert_text)
                    }
                    else -> {
                        binding.imageView.visibility = View.GONE
                        binding.placeholderText.text = getString(R.string.retry_text)
                    }
                }
            }
        }
    }

}
