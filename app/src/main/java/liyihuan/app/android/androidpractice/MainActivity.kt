package liyihuan.app.android.androidpractice

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import liyihuan.app.android.androidpractice.touchevent.BadAdapter
import liyihuan.app.android.mrouter_annotation.MRouter

@MRouter(path = "/app/MainActivity")
class MainActivity : AppCompatActivity(), MainAdapter.OnRecyclerItemClickListener {

    private var data = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()
        val adapter = MainAdapter(data)
        val layout = LinearLayoutManager(this)
        rv_list.layoutManager = layout
        rv_list.adapter = adapter
        adapter.itemClickListener = this

    }


    private fun initData(){
        data.add("112233")

    }

    override fun onItemClick(Position: Int) {
        Log.d("QWER", "onItemClick: ")
    }
}


