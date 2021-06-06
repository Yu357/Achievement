package com.example.y.bottomnav02

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import io.realm.Realm
import io.realm.Sort
import kotlinx.android.synthetic.main.fragment_achievement.*





class AchievementFragment : Fragment() {


    //RealmとかRecyclerViewとか宣言
    private lateinit var realm: Realm
    private lateinit var adapter: CustomRecyclerViewAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_achievement, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //フローティングボタン
        floatingButton.setOnClickListener {
            val intent = Intent(this.context, EditAchievementActivity::class.java)
            startActivity(intent)
        }

        //Realmのデフォルトインスタンスを取得
        realm = Realm.getDefaultInstance()
    }


    override fun onStart() {
        super.onStart()

        //pinRecyclerViewを表示
        layoutManager = GridLayoutManager(this.context, 2)
        pinRecyclerView.layoutManager = layoutManager
        adapter = CustomRecyclerViewAdapter(1, "")
        pinRecyclerView.adapter = this.adapter

        //mainRecyclerViewを表示
        layoutManager = GridLayoutManager(this.context, 2)
//        layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL) //今後使うかも
        mainRecyclerView.layoutManager = layoutManager
        adapter = CustomRecyclerViewAdapter(2, "")
        mainRecyclerView.adapter = this.adapter

        //もしピン止めされたアチーブメントが無いなら、mainRecyclerViewのmarginTopを0にする
        pinRecyclerView.post {
            val param = mainRecyclerView.layoutParams as ViewGroup.MarginLayoutParams
            if(pinRecyclerView.height == 0){
                param.topMargin = 0
            }else{
                param.topMargin = 48
            }
            mainRecyclerView.layoutParams = param
        }

    }


    override fun onDestroy() {
        super.onDestroy()
//        realm.close()
    }


}


