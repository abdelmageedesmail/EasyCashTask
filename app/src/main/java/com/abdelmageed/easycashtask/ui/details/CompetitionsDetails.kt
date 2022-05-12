package com.abdelmageed.easycashtask.ui.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdelmageed.easycashtask.MyApplication
import com.abdelmageed.easycashtask.R
import com.abdelmageed.easycashtask.data.remote.responses.competitionsDetails.CompetitionsDetailsResponse
import com.abdelmageed.easycashtask.data.remote.responses.competitionsDetails.Season
import com.abdelmageed.easycashtask.data.remote.responses.teams.TeamsCompetitionsResponse
import com.abdelmageed.easycashtask.databinding.ActivityCompetitionsDetailsBinding
import com.abdelmageed.easycashtask.viewModels.competitionDetails.CompetitionDetailsStateFlow
import com.abdelmageed.easycashtask.viewModels.competitionDetails.CompetitionsDetailsViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_competitions_details.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

/**
 * this class show competition details that contains:-
 * information about competition and 2 navigation view (Current competition teams - past Crowned tams)
 * navigation component show 2 fragments
 * (TeamsFragment (show competitions current team) - CrownedTeamFragment (show Crowned teams))
 * method get getCashedDetails() show details when connection is off using room database
 * from table class that called CompetitionsDetailsDatabase.class
 */

class CompetitionsDetails : AppCompatActivity() {

    lateinit var binding: ActivityCompetitionsDetailsBinding

    @Inject
    lateinit var viewModel: CompetitionsDetailsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpBinding()
        setUpViewModel()
    }

    private fun setUpViewModel() {
        lifecycleScope.launchWhenCreated {
            viewModel.getCompetitionsDetails(intent.extras?.getInt("id").toString())
            viewModel.mutableStateFlow.collect {
                when (it) {
                    is CompetitionDetailsStateFlow.loading -> {
                        binding.loadingView.visibility = View.VISIBLE
                    }
                    is CompetitionDetailsStateFlow.success -> {
                        binding.loadingView.visibility = View.GONE
                        showDetails(it.competitionsDetailsResponse)
                    }

                    is CompetitionDetailsStateFlow.successTeams -> {
                        showTeams(it.teamsCompetitionsResponse)
                    }
                    is CompetitionDetailsStateFlow.error -> {
                        binding.loadingView.visibility = View.GONE
                        Toast.makeText(
                            this@CompetitionsDetails,
                            "an error occurd please try again later",
                            Toast.LENGTH_SHORT
                        ).show()
                        getCashedDetails()

                    }
                    is CompetitionDetailsStateFlow.empty -> {

                    }

                }
            }
        }

    }

    private fun getCashedDetails() {
        viewModel.readCashedCompetitions.observe(
            this, Observer {
                if (it != null) {
                    if (intent.extras!!.getInt("id") == it.competitionsDetailsResponse.id) {
                        showDetails(it.competitionsDetailsResponse)
                    } else {
                        binding.constraintNoData.visibility = View.VISIBLE
                        binding.constraintData.visibility = View.GONE
                    }
                } else {
                    binding.constraintNoData.visibility = View.VISIBLE
                    binding.constraintData.visibility = View.GONE
                }
            })
    }

    private fun showTeams(teamsResponse: TeamsCompetitionsResponse) {
        if (teamsResponse.teams != null) {
            binding.constraintNoData.visibility = View.GONE
            binding.constraintData.visibility = View.VISIBLE
            viewModel.cashTeams(teamsResponse)
        } else {
            binding.constraintNoData.visibility = View.VISIBLE
            binding.constraintData.visibility = View.GONE
        }
    }

    private fun showDetails(response: CompetitionsDetailsResponse) {
        if (response != null) {
            binding.details = response
            binding.constraintNoData.visibility = View.GONE
            binding.constraintData.visibility = View.VISIBLE
            if (response.seasons.size > 0) {
                if (!response.emblemUrl.isEmpty()) {
                    Picasso.get().load(response.emblemUrl)
                        .into(binding.ivImage)
                }
                viewModel.cashCompetitionsDetails(response)
            }
        } else {
            binding.constraintNoData.visibility = View.VISIBLE
            binding.constraintData.visibility = View.GONE
        }

    }


    private fun setUpBinding() {
        ((application as MyApplication).appComponent.inject(this))
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_competitions_details)

        val bundle = bundleOf("id" to intent.extras!!.getInt("id").toString())
        val navController = Navigation.findNavController(this, R.id.newsNavHostFragment)

        navController.navigate(R.id.crownedTeamFragment, bundle)
        navController.navigate(R.id.teamFragment, bundle)
        setUpBottomNavigation(navController)
    }

    private fun setUpBottomNavigation(navController: NavController) {
        binding.bottomNavigationView?.let { it ->
            NavigationUI.setupWithNavController(it, navController)
        }
    }
}