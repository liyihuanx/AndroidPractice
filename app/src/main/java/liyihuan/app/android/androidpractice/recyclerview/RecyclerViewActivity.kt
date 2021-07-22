package liyihuan.app.android.androidpractice.recyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_recycler_view.*
import liyihuan.app.android.androidpractice.R

class RecyclerViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

//        val arrayList = ArrayList<SectionBean>()
//        repeat(5) {
//            val sectionBean = SectionBean(true, "分组$it")
//            arrayList.add(sectionBean)
//            repeat(5) {
//                arrayList.add(SectionBean(SectionBean.UserList.UserBean("item ", "liyihuan")))
//            }
//        }
//        val sectionAdapter = SectionAdapter()
//
//        val linearLayoutManager = LinearLayoutManager(this)
//        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL;
//        rvView.layoutManager = linearLayoutManager
//        rvView.adapter = sectionAdapter
//
//        sectionAdapter.setNewData(arrayList)
//        sectionAdapter.setOnItemClickListener { adapter, view, position ->
//
//        }
        btnOpenDialogActivity.setOnClickListener {
            val intent = Intent(this, HalfActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("QWER", "RecyclerViewActivity: onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("QWER", "RecyclerViewActivity: onPause")

    }

    override fun onStop() {
        super.onStop()
        Log.d("QWER", "RecyclerViewActivity: onStop")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("QWER", "RecyclerViewActivity: onDestroy")
    }
}