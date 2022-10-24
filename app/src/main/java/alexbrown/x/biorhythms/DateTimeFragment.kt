package alexbrown.x.biorhythms

import alexbrown.x.biorhythms.databinding.FragmentDateTimeBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import java.time.format.DateTimeFormatter

class DateTimeFragment : Fragment() {

    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
    private lateinit var dateTimeStorage: DateTimeStorage

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

        binding.buttonShowResults.setOnClickListener {
            findNavController().navigate(R.id.action_DateTimeFragment_to_ResultFragment)
        }

        if (!dateTimeStorage.firstRun) {
            val dateOfBirthTextView = view.findViewById<TextView>(R.id.textview_date_time)
            dateOfBirthTextView.text = "Date of birth: ${dateTimeStorage.savedDateTime.format(dateFormatter)}"

            val calculateButton = view.findViewById(R.id.button_show_results) as Button
            calculateButton.isEnabled = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}