package com.example.kotlinlogin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView

class ZajeciaTrenerAdapter(context: Context, tests: List<Zajecia>,
                          val usunZadanieListener : UsunZadanieListener) :
    ArrayAdapter<Zajecia>(context, R.layout.activity_zajecia, tests) {

    // Interfejs, który musi implementować klasa, która będzie
    // usuwać dla nas zadania (w naszym przypadku ZadaniaActivity)
    interface UsunZadanieListener {
        fun usunZadanie(position: Int)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Pobieramy z listy obiekt Zadanie do wyświelenia
        val teacherTest = getItem(position) as Zajecia

        // Tworzymy widok wiersza wg naszego layout'u
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.activity_zajecia, parent, false)

        // Pobieramy referencje do pola tekstowego i przycisku
        val trenerSala = rowView.findViewById<TextView>(R.id.SalaZajeciaTrener)
        val trenerTermin = rowView.findViewById<TextView>(R.id.TerminZajeciaTrener)
        val trenerRodzaj = rowView.findViewById<TextView>(R.id.RodzajZacieciaTrener)

        val buttonUsunZadanie = rowView.findViewById<Button>(R.id.buttonUsunZadanie)

        // Ustawiamy tekstowy opis zadania
        trenerSala.text = teacherTest.sala.toString()
        trenerTermin.text = teacherTest.termin
        trenerRodzaj.text = teacherTest.rodzaj


        // Ustawiamy akcję obsługi kliknięcia przycisku
        buttonUsunZadanie.setOnClickListener { v : View ->
            if (usunZadanieListener != null) {
                // Metoda usunZadanie z klasy ZadaniaActivity
                // zostanie wywołana z odpowiednią wartością
                // parametru position, co pozwoli nam zidentyfikować
                // zadanie do usunięcia
                usunZadanieListener.usunZadanie(position)
            }
        }

        return rowView
    }
}