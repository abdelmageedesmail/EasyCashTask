package com.abdelmageed.easycashtask.ui.competitions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdelmageed.easycashtask.MyApplication
import com.abdelmageed.easycashtask.R
import com.abdelmageed.easycashtask.data.remote.responses.competitions.CompetionsResponse
import com.abdelmageed.easycashtask.data.remote.responses.competitions.Competition
import com.abdelmageed.easycashtask.databinding.ActivityMainBinding
import com.abdelmageed.easycashtask.ui.details.CompetitionsDetails
import com.abdelmageed.easycashtask.uitls.Utils
import com.abdelmageed.easycashtask.uitls.ViewClick
import com.abdelmageed.easycashtask.viewModels.competition.CompetitionStateFlow
import com.abdelmageed.easycashtask.viewModels.competition.CompetitionViewModel
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

/**
 * This class show list of competitions
 */

class MainActivity : AppCompatActivity(), ViewClick {

    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModel: CompetitionViewModel

    var competitions: MutableList<Competition> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpBinding()
        setUpViewModel()
    }

    private fun setUpViewModel() {
        if (Utils.checkIsEmulator()) {
            Toast.makeText(this, "Dangerous !! it is emulator", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "No Its safe", Toast.LENGTH_SHORT).show()
        }

        Utils.getSafetyNet(this)
        lifecycleScope.launchWhenCreated {
            viewModel.getCompetitions()
            viewModel.mutableStateFlow.collect {
                when (it) {
                    is CompetitionStateFlow.loading -> {
                        binding.loadingView.visibility = View.VISIBLE
                    }
                    is CompetitionStateFlow.success -> {
                        binding.noDataFound.visibility = View.GONE
                        showCompetions(it.competitionResponse)
                    }
                    is CompetitionStateFlow.error -> {
                        binding.loadingView.visibility = View.GONE
                        viewModel.readCashedCompetitions.observe(this@MainActivity, Observer {
                            if (it != null) {
                                showCashedCompetitions(it.competionsResponse)
                            } else {
                                binding.noDataFound.visibility = View.VISIBLE
                            }

                        })
                    }
                    is CompetitionStateFlow.empty -> {
                        binding.noDataFound.visibility = View.VISIBLE
                    }
                }
            }
        }
    }



    private fun showCompetions(competitionResponse: CompetionsResponse) {
        binding.loadingView.visibility = View.GONE
        competitions = competitionResponse.competitions
        if (competitions != null) {
            competitions
            if (competitions.size > 0) {
                binding.rvCompetitions.adapter = CompetitionsAdapter(
                    this@MainActivity,
                    competitions, this@MainActivity
                )
                binding.rvCompetitions.layoutManager =
                    LinearLayoutManager(this@MainActivity)
                viewModel.cashCompetitions(competitionResponse)
            }
        }
    }

    private fun showCashedCompetitions(competionsResponse: CompetionsResponse) {
        competitions = competionsResponse.competitions
        if (competitions.isNotEmpty()) {

            binding.rvCompetitions.adapter = CompetitionsAdapter(
                this@MainActivity,
                competitions, this@MainActivity
            )
            binding.rvCompetitions.layoutManager =
                LinearLayoutManager(this@MainActivity)
        }
    }

    private fun setUpBinding() {
        ((application as MyApplication).appComponent.inject(this))
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    override fun onItemClick(position: Int) {
        startActivity(
            Intent(this, CompetitionsDetails::class.java).putExtra(
                "id",
                competitions.get(position).id
            )
        )
        finish()
    }
}