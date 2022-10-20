package com.hee.instagram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout

var pager: ViewPager2? = null
class InstaMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insta_main)

        // tab을 가져와서 각각의 탭에 아이콘이미지를 적용
        val tabs = findViewById<TabLayout>(R.id.main_tab)
        tabs.addTab(tabs.newTab().setIcon(R.drawable.btn_outsta_home))
        tabs.addTab(tabs.newTab().setIcon(R.drawable.btn_outsta_post))
        tabs.addTab(tabs.newTab().setIcon(R.drawable.btn_outsta_my))

        // pager 가져오고 adapter생성해주기
        pager = findViewById<ViewPager2>(R.id.main_pager)
        pager!!.adapter = InstaMaingPagerAdapter(this@InstaMainActivity, 3)

        // tab이랑 pager랑 연결해주기
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{

            override fun onTabSelected(tab: TabLayout.Tab?) {
                pager!!.setCurrentItem(tab!!.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })




    }

    fun changeFragment(index: Int){
        when(index){
            0 ->{
                pager!!.setCurrentItem(0)
            }
            1 ->{
                pager!!.setCurrentItem(1)
            }
            else ->{
                pager!!.setCurrentItem(2)
            }
        }
    }


}

class InstaMaingPagerAdapter(
    // 탭으로 pager를 전환할때마다 바뀌는 fragment
    fragmentActivity : FragmentActivity,
    val tabCount: Int
) : FragmentStateAdapter(fragmentActivity){

    override fun getItemCount(): Int {
        return tabCount
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return InstaFeedFragment()
            1 -> return InstaPostFragment()
            else -> return InstaProfileFragment()
        }
    }
}