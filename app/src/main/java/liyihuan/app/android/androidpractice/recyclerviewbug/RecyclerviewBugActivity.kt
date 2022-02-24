package liyihuan.app.android.androidpractice.recyclerviewbug

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_recyclerview_bug.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import liyihuan.app.android.androidpractice.R
import liyihuan.app.android.androidpractice.dialogfragment.DialogHelper

class RecyclerviewBugActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview_bug)


        val list = arrayListOf<String>()
        repeat(1500) {
            list.add("第 $it 个")
        }
        val adapter = RvBugAdapter()
        adapter.setNewData(list)
        rvBug.layoutManager = LinearLayoutManager(this)
        rvBug.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->
//            adapter.getItem(position)
//            Toast.makeText(this, "${adapter.data[position]}", Toast.LENGTH_SHORT).show()

        }

        btnIn.setOnClickListener {
            rvBug.postDelayed({
                rvBug.scrollToPosition(list.size - 1)
                Log.d("QWER", "scrollToPosition: ")
            }, 5000L)
//            list.removeLast()
//            adapter.notifyItemRemoved(list.size - 1)
//            list.add("新加入的")
//            adapter.notifyItemChanged(list.size)
//            rvBug.scrollToPosition(list.size)
//            lifecycleScope.launch(Dispatchers.Main) {
//                repeat(1000) {
//                    list.add("新加入的$it 个")
//                    adapter.notifyItemChanged(list.size)
//                    delay(100)
//                    rvBug.scrollToPosition(list.size - 1)
//                }
//            }
//            repeat(1000) {
//                Handler().postDelayed(Runnable {
//                    list.add("新加入的$it 个")
//                    adapter.notifyItemInserted(list.size)
//                    rvBug.scrollToPosition(list.size - 1)
//                }, 100)
//            }
        }
    }
}