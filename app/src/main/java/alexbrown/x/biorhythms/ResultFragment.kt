package alexbrown.x.biorhythms

import alexbrown.x.biorhythms.databinding.FragmentResultBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import java.util.*

class ResultFragment : Fragment() {

    lateinit var dateTimeStorage: DateTimeStorage
    lateinit var biorhythmCalculator: BiorhythmCalculator

    private var _binding: FragmentResultBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentResultBinding.inflate(inflater, container, false)

        dateTimeStorage = context?.let { DateTimeStorage(it, this.activity as MainActivity) }!!
        biorhythmCalculator = BiorhythmCalculator()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonEditDate.setOnClickListener {
            findNavController().navigate(R.id.action_ResultFragment_to_DateTimeFragment)
        }

        calculateResults()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun calculateResults() {
        val startDate = dateTimeStorage.savedDateTime.time
        val endDate = Date()
        val results = biorhythmCalculator.calculate(startDate, endDate)
        _binding!!.textviewResult.text = "Emotional: ${results.emotionalScore} \nPhysical: ${results.physicalScore} \nIntellectual: ${results.intellectualScore}"
    }
}