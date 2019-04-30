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
        val dialog = dialogFragment.newInstance("This is first arg", "This is second arg")
        dialog.show(manager, "InfoDialog")
        //Don't add to backstack
        return null
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