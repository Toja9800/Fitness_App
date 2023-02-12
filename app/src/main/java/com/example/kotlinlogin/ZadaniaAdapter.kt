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
    val usunZadanieListener: GodzinyActivity,

    ) :
    ArrayAdapter<Zadanie>(context, R.layout.activity_zadania, tests) {

    // Interfejs, który musi implementować klasa, która będzie
    // usuwać dla nas zadania (w naszym przypadku ZadaniaActivity)
    interface UsunZadanieListener {
        fun usunZadanie(position: Int)
    }

    @SuppressLint("MissingInflatedId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Pobieramy z listy obiekt Zadanie do wyświelenia
        val zajeciaTrener = getItem(position) as Zajecia

        // Tworzymy widok wiersza wg naszego layout'u
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.activity_zadania, parent, false)

        // Pobieramy referencje do pola tekstowego i przycisku
        val trenerSala = rowView.findViewById<TextView>(R.id.SalaZajeciaKlient)

        val trenerRodzaj = rowView.findViewById<TextView>(R.id.RodzajZacieciaKlient)
        val trenerGodzina = rowView.findViewById<TextView>(R.id.godzinaZajeciaKlient)

        val Zarezerwuj = rowView.findViewById<Button>(R.id.buttonZarezerwuj)

        // Ustawiamy tekstowy opis zadania
        trenerSala.text = zajeciaTrener.sala.toString()

        trenerRodzaj.text = zajeciaTrener.rodzaj
        trenerGodzina.text = zajeciaTrener.godzina


        // Ustawiamy akcję obsługi kliknięcia przycisku
        Zarezerwuj.setOnClickListener { v : View ->
            if ( usunZadanieListener != null) {
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