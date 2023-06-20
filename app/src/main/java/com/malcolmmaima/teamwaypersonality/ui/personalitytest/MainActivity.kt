package com.malcolmmaima.teamwaypersonality.ui.personalitytest

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.malcolmmaima.teamwaypersonality.data.models.Question
import com.malcolmmaima.teamwaypersonality.databinding.ActivityMainBinding
import com.malcolmmaima.teamwaypersonality.ui.personalitytest.adapter.CardAdapter
import com.malcolmmaima.teamwaypersonality.utils.OnSwipeTouchListener
import com.malcolmmaima.teamwaypersonality.utils.calculatePercentage
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.RewindAnimationSetting
import com.yuyakaido.android.cardstackview.StackFrom
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), CardStackListener {

    private lateinit var binding: ActivityMainBinding
    lateinit var layoutManager: CardStackLayoutManager
    private val viewModel: MainViewModel by viewModels()
    private lateinit var cardAdapter: CardAdapter

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
        handleCardChanges()

        binding.restack.setOnClickListener {
            if (layoutManager.topPosition != 0) {
                binding.cards.smoothScrollToPosition(0)
            }
        }

        binding.previousCard.setOnClickListener {
            binding.cards.rewind()
        }

        binding.container.setOnTouchListener(object : OnSwipeTouchListener(this@MainActivity) {
            override fun onSwipeDown() {
                binding.cards.rewind()
            }
        })
    }

    private fun fetchPersonalityQuestions() {
        viewModel.fetchPersonalityQuestions()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.personalityQuestions.collectLatest { personalityQuestions ->
                    // Update the UI.
                    initializeCardStackView(personalityQuestions.questions)
                }
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
        if (layoutManager.topPosition == 0) {
            binding.restack.hide()
            binding.previousCard.visibility = View.GONE
        } else {
            binding.restack.show()
            binding.previousCard.visibility = View.VISIBLE
        }
        Log.d("MainActivity", "handleCardChanges: ${layoutManager.topPosition} ${layoutManager.itemCount}")
        binding.placeholder.visibility =
            if (layoutManager.topPosition == (layoutManager.itemCount)) View.VISIBLE else View.GONE
        binding.cards.visibility = if (layoutManager.topPosition == (layoutManager.itemCount)) View.GONE else View.VISIBLE

    }
}