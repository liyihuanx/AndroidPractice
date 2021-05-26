package liyihuan.app.android.androidpractice.hovertop

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_hover_top.*
import liyihuan.app.android.androidpractice.R
import liyihuan.app.android.androidpractice.refresh.SmartRefreshAdapter

class HoverTopActivity : AppCompatActivity() {
    private val adapter = SmartRefreshAdapter()
    private var list = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hover_top)

        repeat(20) {
            list.add(" -- 数据$it -- ")
        }

        rvHover.layoutManager = LinearLayoutManager(this)
        rvHover.adapter = adapter
        adapter.setNewData(list)
//        rvHover.addItemDecoration(FixedBarDecoration(this))


        val header = LayoutInflater.from(this).inflate(R.layout.header_layout, rvHover, false)
        adapter.addHeaderView(header)
        adapter.setOnItemClickListener { adapter, view, position ->
            Log.d("QWER", "onCreate:  $position --- ${adapter.data[position]}")
//            Toast.makeText(this, "${adapter.data[position]}", Toast.LENGTH_SHORT)
        }

        rvHover.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.d("QWER", "onScrolled: dy -- $dy  dx -- $dx")
            }
        })

    }
}