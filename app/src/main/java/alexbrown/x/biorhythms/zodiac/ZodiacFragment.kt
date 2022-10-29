package alexbrown.x.biorhythms.zodiac

import alexbrown.x.biorhythms.MainActivity
import alexbrown.x.biorhythms.R
import alexbrown.x.biorhythms.utils.DateTimeStorage
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class ZodiacFragment : Fragment() {

    private lateinit var dateTimeStorage: DateTimeStorage

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dateTimeStorage = context?.let { DateTimeStorage(it, this.activity as MainActivity) }!!
        return inflater.inflate(R.layout.fragment_zodiac, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CarouselZodiacTask(view, dateTimeStorage)
    }
}