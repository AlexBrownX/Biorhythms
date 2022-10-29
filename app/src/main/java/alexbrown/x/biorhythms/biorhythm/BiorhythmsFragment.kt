package alexbrown.x.biorhythms.biorhythm

import alexbrown.x.biorhythms.MainActivity
import alexbrown.x.biorhythms.R
import alexbrown.x.biorhythms.databinding.FragmentBiorhythmsBinding
import alexbrown.x.biorhythms.utils.DateDialogFragment
import alexbrown.x.biorhythms.utils.TimeDialogFragment
import alexbrown.x.biorhythms.utils.DateTimeStorage
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import java.text.SimpleDateFormat

class BiorhythmsFragment : Fragment() {

    private lateinit var dateTimeStorage: DateTimeStorage

    private val dateFormatter = SimpleDateFormat("dd/MM/yyyy HH:mm")
    private var _binding: FragmentBiorhythmsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBiorhythmsBinding.inflate(inflater, container, false)
        dateTimeStorage = context?.let { DateTimeStorage(it, this.activity as MainActivity) }!!
        bindButtons()

        return binding.root
    }

    private fun bindButtons() {
        binding.buttonDate.setOnClickListener { view ->
            Log.d(tag, "Opening date dialog fragment from $view")
            DateDialogFragment(dateTimeStorage).show(childFragmentManager, "DateDialogFragment")
        }

        binding.buttonTime.setOnClickListener { view ->
            Log.d(tag, "Opening time dialog fragment from $view")
            TimeDialogFragment(dateTimeStorage).show(childFragmentManager, "TimeDialogFragment")
        }

        binding.buttonShowDailyResults.setOnClickListener { view ->
            Log.d(tag, "Opening daily result fragment from $view")
            findNavController().navigate(R.id.daily_result_action)
        }

        binding.buttonShowWeeklyResults.setOnClickListener { view ->
            Log.d(tag, "Opening weekly result fragment from $view")
            findNavController().navigate(R.id.weekly_result_action)
        }

        binding.buttonShowLongTermResults.setOnClickListener { view ->
            Log.d(tag, "Opening long term result fragment from $view")
            findNavController().navigate(R.id.long_term_result_fragment)
        }
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