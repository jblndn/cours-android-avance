package com.example.myapplicationtest

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.ImageView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.list.*
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    private var games: ArrayList<Games> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setSupportActionBar(toolbar)

//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }

//        editText.addTextChangedListener(object: TextWatcher {
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//                if (s != null) textView.text = s.toString()
//            }
//
//        })
//
//        button.setOnClickListener { editText.editableText.clear() }


        //List

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager

        recyclerView.adapter = ExempleAdapter(games)

        fetchData(this)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun fetchData(context: Context) {
        val queue = Volley.newRequestQueue(context)


        var request = JsonArrayRequest(
            Request.Method.GET, URL, null,
            Response.Listener<JSONArray> { response: JSONArray ->

                var x: Int = 0

                while (x < response.length()) {
                    val game = response.getJSONObject(x)

                    val id = game.getInt("id")
                    val name = game.getString("name")
                    val img = game.getString("img")
                    val description= game.getString("description")
                    val link = game.getString("link")


                    games.add(Games(id, name, img, description, link))

                    x++
                }

            }, Response.ErrorListener { error -> Log.e("test", error.localizedMessage) })

        queue.add(request)
    }

    companion object {
        const val  URL = "https://my-json-server.typicode.com/bgdom/cours-android/games"
    }
}

class ExempleAdapter(private val games: ArrayList<Games>): RecyclerView.Adapter<ExempleAdapter.ExampleViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExempleAdapter.ExampleViewHolder {
        val viewHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.card, parent, false)
        return ExampleViewHolder(viewHolder)
    }

    override fun getItemCount(): Int = games.size

    override fun onBindViewHolder(
        holder: ExampleViewHolder,
        position: Int
    ) = holder.bind(games[position])

    class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView : TextView = itemView.findViewById(R.id.textView)
        private val imageView: ImageView = itemView.findViewById(R.id.img)

        fun bind(games: Games){
            textView.text = games.name

            Picasso.get().load(games.img).into(imageView)

            print(games)
        }
    }


}
