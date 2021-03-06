package com.example.y.achievement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmResults
import io.realm.Sort
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_optional_search.*

class OptionalSearchActivity : AppCompatActivity() {


    //Realmのインスタンス取得
    val realm: Realm = Realm.getDefaultInstance()

    //取得するレコードを格納する変数realmResults
    private lateinit var realmResults: RealmResults<Achievement>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_optional_search)

        //putExtraを取得
        val optionId = intent.getIntExtra("optionId", 0)
        val achievedDateNumber = intent.getIntExtra("achievedDateNumber", 0)
        val achievedDateString = intent.getStringExtra("achievedDateString")

        //SearchFragmentから渡されたoptionIdに合ったレコードを取得&labelTextに文字列をセット
        when(optionId){
            1 -> {
                //未達成のアチーブメントを取得
                realmResults = realm.where<Achievement>()
                    .equalTo("isAchieved", false)
                    .findAll()
                    .sort("id", Sort.DESCENDING)
                //タイトルをセット
                labelText.text = "未達成"
            }
            2 -> {
                //達成済みアチーブメントを取得
                realmResults = realm.where<Achievement>()
                    .equalTo("isAchieved", true)
                    .findAll()
                    .sort("achievedDate", Sort.DESCENDING, "achievedTime", Sort.DESCENDING)
                //タイトルをセット
                labelText.text = "達成済み"
            }
            3 -> {
                //ピン止めされていないアチーブメントを取得
                realmResults = realm.where<Achievement>()
                    .equalTo("isPinned", false)
                    .findAll()
                    .sort("id", Sort.DESCENDING)
                //タイトルをセット
                labelText.text = "ピン止めなし"
            }
            4 -> {
                //ピン止めされているアチーブメントを取得
                realmResults = realm.where<Achievement>()
                    .equalTo("isPinned", true)
                    .findAll()
                    .sort("id", Sort.DESCENDING)
                //タイトルをセット
                labelText.text = "ピン止め済み"
            }
            10 -> {
                //白色のアチーブメントを取得
                val index = 0
                realmResults = realm.where<Achievement>()
                    .equalTo("colorId", index)
                    .findAll()
                    .sort("id", Sort.DESCENDING)
                //タイトルをセット
                labelText.text = "白色のアチーブメント"
            }
            11 -> {
                //緑色のアチーブメントを取得
                val index = 1
                realmResults = realm.where<Achievement>()
                    .equalTo("colorId", index)
                    .findAll()
                    .sort("id", Sort.DESCENDING)
                //タイトルをセット
                labelText.text = "緑色のアチーブメント"
            }
            12 -> {
                //青色のアチーブメントを取得
                val index = 2
                realmResults = realm.where<Achievement>()
                    .equalTo("colorId", index)
                    .findAll()
                    .sort("id", Sort.DESCENDING)
                //タイトルをセット
                labelText.text = "青色のアチーブメント"
            }
            13 -> {
                //紫色のアチーブメントを取得
                val index = 3
                realmResults = realm.where<Achievement>()
                    .equalTo("colorId", index)
                    .findAll()
                    .sort("id", Sort.DESCENDING)
                //タイトルをセット
                labelText.text = "紫色のアチーブメント"
            }
            14 -> {
                //金色のアチーブメントを取得
                val index = 4
                realmResults = realm.where<Achievement>()
                    .equalTo("colorId", index)
                    .findAll()
                    .sort("id", Sort.DESCENDING)
                //タイトルをセット
                labelText.text = "ゴールドアチーブメント"
            }
            20 -> {
                //HistoryFragmentのカレンダーでタップされた日に達成されたアチーブメントを取得
                realmResults = realm.where<Achievement>()
                    .equalTo("achievedDate", achievedDateNumber)
                    .and()
                    .equalTo("isAchieved", true)
                    .findAll()
                    .sort("achievedTime", Sort.ASCENDING)
                //タイトルをセット
                labelText.text = (achievedDateString + "に達成")
            }
        }

        //RecyclerViewを表示
        optionalSearchRecyclerView.layoutManager = GridLayoutManager(this, 2)
        optionalSearchRecyclerView.adapter = FrameRecyclerViewAdapter(realmResults)

        //結果が0件なら、メッセージを表示
        if(realmResults.size == 0){
            noResultText3.visibility = View.VISIBLE
        }else{
            noResultText3.visibility = View.GONE
        }
        realmResults.addChangeListener(RealmChangeListener {
            if(realmResults.size == 0){
                noResultText3.visibility = View.VISIBLE
            }else{
                noResultText3.visibility = View.GONE
            }
        })

        //backButton2を押すと、finish
        backButton2.setOnClickListener {
            finish()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }


}