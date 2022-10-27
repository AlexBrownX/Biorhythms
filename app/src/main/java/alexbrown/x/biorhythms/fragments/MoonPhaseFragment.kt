package alexbrown.x.biorhythms.fragments

import alexbrown.x.biorhythms.R
import alexbrown.x.biorhythms.utils.CarouselMoonPhaseTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class MoonPhaseFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_moon_phase, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CarouselMoonPhaseTask(view)
    }
}