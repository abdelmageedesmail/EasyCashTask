package com.abdelmageed.easycashtask.ui.details

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdelmageed.easycashtask.MyApplication
import com.abdelmageed.easycashtask.R
import com.abdelmageed.easycashtask.data.remote.responses.competitionsDetails.CompetitionsDetailsResponse
import com.abdelmageed.easycashtask.data.remote.responses.teams.TeamsCompetitionsResponse
import com.abdelmageed.easycashtask.databinding.FragmentCompetitionBinding
import com.abdelmageed.easycashtask.databinding.FragmentCrownedTeamBinding
import com.abdelmageed.easycashtask.ui.competitions.MainActivity
import com.abdelmageed.easycashtask.viewModels.competitionDetails.CompetitionDetailsStateFlow
import com.abdelmageed.easycashtask.viewModels.competitionDetails.CompetitionsDetailsViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

/**
 * This class show the competition's teams that had been crowned before
 * the data showed using cashed data in competition details and teams of competitions
 */

class CrownedTeamFragment : Fragment() {

    @Inject
    lateinit var viewModel: CompetitionsDetailsViewModel

    lateinit var binding: FragmentCrownedTeamBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                startActivity(Intent(requireActivity(), MainActivity::class.java))
                requireActivity().finish()
            }

        })
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Toast.makeText(requireActivity(), "Crowned Team", Toast.LENGTH_SHORT).show()
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_crowned_team, container, false)
        ((requireActivity().application as MyApplication).appComponent).inject(this)

        arguments?.let {
            if (it.getSerializable("id") != null) {
                val id = it.getString("id")
            }

        }

        loadConorationTeamsDataBase()

        return binding.root
    }


    private fun loadConorationTeamsDataBase() {
        viewModel.readCashedCompetitions.observe(
            requireActivity(), Observer {
                if (it != null) {
                    binding.noDataFound.visibility = View.GONE
                    showCrownedTeams(it.competitionsDetailsResponse)
                } else {
                    binding.noDataFound.visibility = View.VISIBLE
                }

            })
    }


    private fun showCrownedTeams(response: CompetitionsDetailsResponse) {
        if (response.seasons != null) {

            if (response.seasons.size > 0) {
                if (activity != null) {
                    binding.rvTeams.adapter = CompetitionsTeamsAdapter(
                        requireActivity(),
                        response.seasons
                    )
                    binding.rvTeams.layoutManager =
                        LinearLayoutManager(activity)
                }
            }
        }
    }
}