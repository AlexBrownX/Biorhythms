package alexbrown.x.biorhythms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.GregorianCalendar

class MoonPhaseFragment : Fragment() {

    private val dateFormat = SimpleDateFormat("EEEE dd MMMM yyyy")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val moonPhaseInstructionText = view.findViewById(R.id.textview_moon_phase_instructions) as TextView
        moonPhaseInstructionText.text = "${moonPhaseInstructionText.text} ${dateFormat.format(GregorianCalendar().time)}"

        MoonPhaseTask(view)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_moon_phase, container, false)
    }
}