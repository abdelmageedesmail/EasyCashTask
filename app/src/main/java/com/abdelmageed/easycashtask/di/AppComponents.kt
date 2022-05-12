package com.abdelmageed.islamicapp.di

import com.abdelmageed.easycashtask.ui.competitions.MainActivity
import com.abdelmageed.easycashtask.ui.details.CompetitionsDetails
import com.abdelmageed.easycashtask.ui.details.CrownedTeamFragment
import com.abdelmageed.easycashtask.ui.details.TeamDetailsActivity
import com.abdelmageed.easycashtask.ui.details.TeamFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponents {
    fun inject(mainActivity: MainActivity)
    fun inject(competitionsDetails: CompetitionsDetails)
    fun inject(teamDetailsActivity: TeamDetailsActivity)
    fun inject(teamFragment: TeamFragment)
    fun inject(crownedTeamFragment: CrownedTeamFragment)
}