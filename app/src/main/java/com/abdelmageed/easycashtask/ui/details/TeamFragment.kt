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
import com.abdelmageed.easycashtask.data.remote.responses.teams.Team
import com.abdelmageed.easycashtask.data.remote.responses.teams.TeamsCompetitionsResponse
import com.abdelmageed.easycashtask.databinding.FragmentCompetitionBinding
import com.abdelmageed.easycashtask.ui.competitions.MainActivity
import com.abdelmageed.easycashtask.uitls.ViewClick
import com.abdelmageed.easycashtask.viewModels.competitionDetails.CompetitionDetailsStateFlow
import com.abdelmageed.easycashtask.viewModels.competitionDetails.CompetitionsDetailsViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

/**
 * This class show current competition's team
 * Data show according to cashed data of api that return teams
 */
class TeamFragment : Fragment(), ViewClick {

    @Inject
    lateinit var viewModel: CompetitionsDetailsViewModel

    lateinit var binding: FragmentCompetitionBinding
    var teams: MutableList<Team> = mutableListOf()

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_competition, container, false)
        ((requireActivity().application as MyApplication).appComponent).inject(this)

        arguments?.let {
            if (it.getSerializable("id") != null) {
                val id = it.getString("id")
            }
        }
        loadCashedTeams()
        return binding.root
    }

    private fun loadCashedTeams() {
        viewModel.readCashedTeams.observe(
            requireActivity(), Observer {
                if (it != null) {
                    binding.noDataFound.visibility = View.GONE
                    showTeams(it.teamsCompetitionsResponse)
                } else {
                    binding.noDataFound.visibility = View.VISIBLE
                }

            })
    }

    private fun showTeams(teamsResponse: TeamsCompetitionsResponse) {
        teams = teamsResponse.teams
        if (teams != null) {
            if (teams.size > 0) {
                binding.rvTeams.adapter = TeamsAdapter(
                    requireActivity(),
                    teams,
                    this
                )
                binding.rvTeams.layoutManager =
                    LinearLayoutManager(activity)

            }

        }
    }

    override fun onItemClick(position: Int) {
        startActivity(
            Intent(requireActivity(), TeamDetailsActivity::class.java).putExtra(
                "id",
                teams.get(position).id
            )
        )
    }
}