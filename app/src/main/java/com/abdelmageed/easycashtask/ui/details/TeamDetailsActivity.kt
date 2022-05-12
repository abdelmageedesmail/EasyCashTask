package com.abdelmageed.easycashtask.ui.details

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.abdelmageed.easycashtask.MyApplication
import com.abdelmageed.easycashtask.R
import com.abdelmageed.easycashtask.data.remote.responses.TeamDetailsResponse
import com.abdelmageed.easycashtask.databinding.ActivityTeamDetailsBinding
import com.abdelmageed.easycashtask.viewModels.teamDetails.TeamDetailsStateFlow
import com.abdelmageed.easycashtask.viewModels.teamDetails.TeamDetailsViewModel
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

/**
 * This Activity Show Team Details.
 * TeamViewModel is a view model class that call bussiness login in like :-
 * team details && Cash team details when there is an error
 * using data binding to bind response data in XML views method  name is :- updateUi()
 * method that is called loadCashedData() -> return cashed data from Room Database from TeamDetailsDatabase Table class
 */

class TeamDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityTeamDetailsBinding

    @Inject
    lateinit var viewModel: TeamDetailsViewModel
    var url = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpBinding()
        setUpViewModel()
    }

    private fun setUpViewModel() {
        lifecycleScope.launchWhenCreated {
            viewModel.getTeamDetails(intent.extras?.getInt("id").toString())
            viewModel.mutableStateFlow.collect {
                when (it) {
                    is TeamDetailsStateFlow.loading -> {

                    }

                    is TeamDetailsStateFlow.success -> {
                        if (it.teamDetailsResponse != null) {
                            binding.constraintNoData.visibility = View.GONE
                            binding.constraintData.visibility = View.VISIBLE
                            updateUi(it.teamDetailsResponse)
                        } else {
                            binding.constraintNoData.visibility = View.VISIBLE
                            binding.constraintData.visibility = View.GONE
                        }
                    }

                    is TeamDetailsStateFlow.error -> {
                        loadCashedTeams()
                    }

                    is TeamDetailsStateFlow.empty -> {

                    }
                }
            }
        }
    }

    private fun loadCashedTeams() {
        viewModel.readCashedTeamDetails.observe(
            this, Observer {
                if (it != null) {
                    if (intent.extras?.getInt("id") ?: 0 == it.teamDetailsResponse.id) {
                        binding.constraintNoData.visibility = View.GONE
                        binding.constraintData.visibility = View.VISIBLE
                        updateUi(it.teamDetailsResponse)
                    } else {
                        Toast.makeText(this, "Item is not cashed in database", Toast.LENGTH_SHORT)
                            .show()
                        binding.constraintNoData.visibility = View.VISIBLE
                        binding.constraintData.visibility = View.GONE

                    }

                }

            })
    }

    private fun updateUi(teamDetailsResponse: TeamDetailsResponse) {
        binding.teamDetails = teamDetailsResponse
        url = teamDetailsResponse.website
        if (!teamDetailsResponse.crestUrl.isEmpty()) {
            if (teamDetailsResponse.crestUrl.endsWith("svg")) {
                GlideToVectorYou
                    .init()
                    .with(this@TeamDetailsActivity)
                    .load(
                        Uri.parse(teamDetailsResponse.crestUrl),
                        binding.ivImage
                    )
            } else {
                Picasso.get()
                    .load(Uri.parse(teamDetailsResponse.crestUrl))
                    .into(binding.ivImage)
            }
        }
    }

    private fun setUpBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_team_details)
        (application as MyApplication).appComponent.inject(this)
        binding.tvWebsite.setPaintFlags(binding.tvWebsite.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
    }

    fun onClick(view: View) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}