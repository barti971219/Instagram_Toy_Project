package com.hee.instagram

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

var context  : InstaMainActivity = InstaMainActivity()

class InstaFeedFragment : Fragment(){

    // 1. onCreateView를 override하여 처음 이 액티비티로 올때 insta_feed_fragment 그려주기
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.insta_feed_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 2. 위에 그려진 위에 그려진 insta_feed_fragment에서 RecyclerView 찾아주기
        val feedListView = view.findViewById<RecyclerView>(R.id.feed_list)

        // 3. 서버로부터 데이터 가져와서 화면에 RecyclerView로 화면에 뿌리는 과정 ------------------------

        // 3_1 Retrofit객체와 RetroService객체 생성하기
        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitService = retrofit.create(RetrofitService::class.java)

        // 3_2 getInstagramPosts() 호출하여 ArrayList에 담아주기
        retrofitService.getInstagramPosts().enqueue(object : Callback<ArrayList<InstaPost>>{
            override fun onResponse(
                call: Call<ArrayList<InstaPost>>,
                response: Response<ArrayList<InstaPost>>
            ) {
                val postList = response.body()
                val postRecyclerView = view.findViewById<RecyclerView>(R.id.feed_list)
                postRecyclerView.adapter = PostRecyclerViewAdapter(
                    postList!!,
                    LayoutInflater.from(activity),
                    Glide.with(activity!!),

                )
            }

            override fun onFailure(call: Call<ArrayList<InstaPost>>, t: Throwable) {
            }
        })
    }

    // 3_3 RecyclerViewAdapter 클래스 생성
    class PostRecyclerViewAdapter(
        val postList : ArrayList<InstaPost>,
        val inflater : LayoutInflater,
        val glide : RequestManager,
    ): RecyclerView.Adapter<PostRecyclerViewAdapter.ViewHolder>(){

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val ownerImg: ImageView
            val ownerUsername: TextView
            val postImg: ImageView
            val postContent: TextView

            init {
                ownerImg = itemView.findViewById(R.id.owner_img)
                ownerUsername = itemView.findViewById(R.id.owner_username)
                postImg = itemView.findViewById(R.id.post_img)
                postContent = itemView.findViewById(R.id.post_content)

            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(inflater.inflate(R.layout.post_item, parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val post = postList.get(position)

//            if(post.owner_profile.image != null){
//                glide.load(post.owner_profile).into(holder.ownerImg)
//            }else{
//                glide.load(ResourcesCompat.getDrawable(context as Resources, R.drawable.btn_outsta_my, null)).centerCrop().into(holder.ownerImg)
//            }
            post.owner_profile.let{
                glide.load(it).centerCrop().into(holder.ownerImg)
            }
            post.image.let{
                glide.load(it).centerCrop().into(holder.postImg)
            }
            holder.ownerUsername.text = post.owner_profile.username
            holder.postContent.text = post.content
        }

        override fun getItemCount(): Int {
            return postList.size
        }
    }
}