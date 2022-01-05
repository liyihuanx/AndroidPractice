package liyihuan.app.android.androidpractice.appbarlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_app_bar_layout2.*
import liyihuan.app.android.androidpractice.R

class AppBarLayoutActivity : AppCompatActivity() {

    var fragments: List<Fragment> = arrayListOf(
         BarFragment2(),BarFragment()
    )
    var titles: List<String> = arrayListOf("首页","其他")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_bar_layout2)
//        initViewPager()
        initViewPager2()
    }


    private fun initViewPager2() {
        vp_content2.adapter = object : FragmentStateAdapter(this){
            override fun getItemCount(): Int {
                return fragments.size
            }

            override fun createFragment(position: Int): Fragment {
                return fragments.get(position)
            }

        }
        vp_content2.isSaveEnabled = false
        vp_content2.offscreenPageLimit = fragments.size

        TabLayoutMediator(tab_layout, vp_content2) { tab, position ->
            when (position) {
                0 -> tab.text = "相册"
                1 -> tab.text = "视频"
            }
        }.attach()
    }




//    private fun initViewPager() {
//
//        vp_content.adapter = object : FragmentStatePagerAdapter(
//            supportFragmentManager) {
//            override fun getItem(position: Int): Fragment {
//                return fragments.get(position)
//            }
//
//            override fun getCount(): Int {
//                return fragments.size
//            }
//
//            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//                super.destroyItem(container, position, `object`)
//            }
//
//            override fun getPageTitle(position: Int): CharSequence? {
//                return titles.get(position)
//            }
//        }
//
//        tab_layout.setupWithViewPager(vp_content)
//
//    }
}