package no.uio.ifi.in2000.gruppe55.destinations

import android.os.Bundle
import android.support.v4.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import no.uio.ifi.in2000.gruppe55.InfoFragment

@Navigator.Name("infoDialog") //tag used in graph
class InfoDialogNavigator (private val manager: FragmentManager):
    Navigator<InfoDialogNavigator.Destination>() {

    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Extras?
    ): NavDestination? {
        //TODO: Consider if should/possible support FragmentDialogs in general instead of special case and args
        val arg1 = args?.getString("stationId")
        val arg2 = args?.getString("stationName")

        val dialog = InfoFragment.newInstance(arg1, arg2)
        dialog.show(manager, "InfoFragmentDialog")
        return null     //Don't add to backstack
    }


    override fun createDestination(): Destination {
        return Destination(this)
    }

    override fun popBackStack(): Boolean {
        return false
    }

    class Destination(infoDialogNavigator: InfoDialogNavigator):
            NavDestination(infoDialogNavigator)
}