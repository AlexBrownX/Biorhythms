package alexbrown.x.biorhythms

import alexbrown.x.biorhythms.databinding.FragmentDateTimeBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
            dateOfBirthTextView.text = "Date of birth: ${dateFormatter.format(dateTimeStorage.savedDateTime.time)}"

            val dailyResultsButton = view.findViewById(R.id.button_show_daily_results) as Button
            dailyResultsButton.isEnabled = true

            val weeklyResultsButton = view.findViewById(R.id.button_show_weekly_results) as Button
            weeklyResultsButton.isEnabled = true

            val longTermResultsButton = view.findViewById(R.id.button_show_long_term_results) as Button
            longTermResultsButton.isEnabled = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}