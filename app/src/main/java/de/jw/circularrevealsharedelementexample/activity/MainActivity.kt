package de.jw.circularrevealsharedelementexample.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import de.jw.circularrevealsharedelementexample.R
import de.jw.circularrevealsharedelementexample.adapter.SimpleAdapter
import de.jw.circularrevealsharedelementexample.ui.RecyclerViewClickListener
import de.jw.circularrevealsharedelementexample.ui.RoundedColorView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), RecyclerViewClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = SimpleAdapter(this)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            this.adapter = adapter
        }
        adapter.addAll(listOf(
                "Item 1",
                "Item 2",
                "Item 3",
                "Item 4",
                "Item 5"
        ))
    }

    override fun onClickRecyclerViewItem(view: RoundedColorView) {
        Intent(this, SecondActivity::class.java).apply {
            val transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(this@MainActivity, view, getString(R.string.roundedColorView_transitionName))
            startActivity(this, transitionActivityOptions.toBundle())
        }
    }
}
