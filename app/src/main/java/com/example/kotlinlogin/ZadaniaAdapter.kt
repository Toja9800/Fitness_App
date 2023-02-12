package com.example.kotlinlogin

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView

class ZadaniaAdapter(
    context: Context, tests: List<Zadanie>,
    val rezerwujZadanieListener: GodzinyActivity,

    ) :
    ArrayAdapter<Zadanie>(context, R.layout.activity_zadania, tests) {

    // Interfejs, który musi implementować klasa, która będzie
    // usuwać dla nas zadania (w naszym przypadku ZadaniaActivity)
    interface RezerwujZadanieListener {
        fun RezerwujZadanie(position: Int)
    }

    @SuppressLint("MissingInflatedId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Pobieramy z listy obiekt Zadanie do wyświelenia
        val zajecia = getItem(position) as Zadanie

        // Tworzymy widok wiersza wg naszego layout'u
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.activity_zadania, parent, false)

        // Pobieramy referencje do pola tekstowego i przycisku
        val sala = rowView.findViewById<TextView>(R.id.SalaZajeciaKlient)

        val rodzaj = rowView.findViewById<TextView>(R.id.RodzajZacieciaKlient)
        val godzina = rowView.findViewById<TextView>(R.id.godzinaZajeciaKlient)

        val Zarezerwuj = rowView.findViewById<Button>(R.id.buttonZarezerwuj)

        // Ustawiamy tekstowy opis zadania
        sala.text = zajecia.sala.toString()

        rodzaj.text = zajecia.rodzaj
        godzina.text = zajecia.godzina


        // Ustawiamy akcję obsługi kliknięcia przycisku
        Zarezerwuj.setOnClickListener { v : View ->
            if ( rezerwujZadanieListener != null) {
                rezerwujZadanieListener.rezerwujZadanie(position)
                Zarezerwuj.setBackgroundColor(0)
                Zarezerwuj.setTextColor(0)
            }
        }

        return rowView
    }
}