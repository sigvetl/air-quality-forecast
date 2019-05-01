package no.uio.ifi.in2000.gruppe55.destinations

import android.os.Bundle
import android.support.v4.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import no.uio.ifi.in2000.gruppe55.dialogFragment

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
        val arg1 = args?.getString("text1")
        val arg2 = args?.getString("text2")

        val dialog = dialogFragment.newInstance(arg1, arg2)
        dialog.show(manager, "InfoDialog")
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