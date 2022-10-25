package alexbrown.x.biorhythms

import alexbrown.x.biorhythms.databinding.FragmentDateTimeBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat

class DateTimeFragment : Fragment() {

    private lateinit var dateTimeStorage: DateTimeStorage
    private val dateFormatter = SimpleDateFormat("dd/MM/yyyy HH:mm")
    private var _binding: FragmentDateTimeBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDateTimeBinding.inflate(inflater, container, false)
        dateTimeStorage = context?.let { DateTimeStorage(it, this.activity as MainActivity) }!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!dateTimeStorage.firstRun) {
            val dateOfBirthTextView = view.findViewById<TextView>(R.id.textview_date_time)
            dateOfBirthTextView.text = "Selected date of birth: ${dateFormatter.format(dateTimeStorage.savedDateTime.time)}"

            val daily = view.findViewById(R.id.daily_layout) as LinearLayout
            daily.visibility = View.VISIBLE

            val weekly = view.findViewById(R.id.weekly_layout) as LinearLayout
            weekly.visibility = View.VISIBLE

            val longTerm = view.findViewById(R.id.long_term_layout) as LinearLayout
            longTerm.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}