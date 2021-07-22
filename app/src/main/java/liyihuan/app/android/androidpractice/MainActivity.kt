package liyihuan.app.android.androidpractice

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import liyihuan.app.android.androidpractice.touchevent.BadAdapter
import liyihuan.app.android.mrouter_annotation.MRouter
import liyihuan.app.android.mrouter_annotation.Parameter
import liyihuan.app.android.mrouter_api.ParameterManager
import java.util.concurrent.TimeUnit

@MRouter(path = "/app/MainActivity")
class MainActivity : AppCompatActivity(), MainAdapter.OnRecyclerItemClickListener {


    @Parameter
    lateinit var name: String

    private var data = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ParameterManager.getInstance().register(this)
        Log.d("QWER", "onCreate: $name")
        initData()
        val adapter = MainAdapter(data)
        val layout = LinearLayoutManager(this)
        rv_list.layoutManager = layout
        rv_list.adapter = adapter
        adapter.itemClickListener = this

        Observable.timer(10, TimeUnit.SECONDS)
                .subscribe {
                    Log.d("QWER", "onCreate: ")
                }
    }


    private fun initData() {
        data.add("112233")

    }

    override fun onItemClick(Position: Int) {
        Log.d("QWER", "onItemClick: ")
    }
}


