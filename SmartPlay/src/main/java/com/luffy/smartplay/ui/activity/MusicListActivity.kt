package com.luffy.smartplay.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.luffy.smartplay.ui.adapter.MusicListAdapter
import com.luffy.smartplay.Config
import com.luffy.smartplay.db.bean.AlbumData
import com.luffy.smartplay.R
import com.luffy.smartplay.db.bean.MusicData
import com.luffy.smartplay.databinding.ItemMenuBinding
import com.luffy.smartplay.databinding.ListActivityBinding
import com.luffy.smartplay.ui.fragment.AlbunFragment
import com.luffy.smartplay.ui.base.BaseActivity
import com.luffy.smartplay.ui.viewmodel.ListActivityViewModel
import java.util.*

/**
 * Created by axnshy on 16/5/20.
 */
class MusicListActivity : BaseActivity<ListActivityBinding, ListActivityViewModel>(),
    View.OnClickListener,
    AlbunFragment.OnItemClickListener, Observer,
    MusicListAdapter.OnItemMenuClickListener {

    //绑定service与activity
    var mAlbumList: List<AlbumData>? = null
    var CurrentListPosition = 0


    private val selectItem = 0
    private var listId = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        listId = intent.getIntExtra("list_id", -1)
        CurrentListPosition = intent.getIntExtra(Config.Companion.LIST, -1)
        initView()
    }

    private fun initView() {
        viewBinding.tvToolbarTitle.text = ""
        setSupportActionBar(viewBinding.toolbar)
        Log.w("TAG", "musicname-----------comment------------ ")
        val fragment = AlbunFragment()
        supportFragmentManager.beginTransaction().replace(
            R.id.FL_list_fragment, fragment
        ).commit()
        viewBinding.tvToolbarTitle.text = mAlbumList!![CurrentListPosition].albumName
    }


    override fun onClick(v: View) {
        //判断Service是否存在
    }

    override fun updateToolbar(string: String?) {
        //topMusicDisplay.setText(string);
    }

//    fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu2, menu)
//        return true
//    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        getMenuInflater().inflate(R.menu.menu2, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun bindViewBinding(): ListActivityBinding {
        return ListActivityBinding.inflate(
            layoutInflater
        )
    }
    override val viewModel: ListActivityViewModel by lazy { ViewModelProvider(this)[ListActivityViewModel::class.java] }
    val mediaMenuViewBinding: ItemMenuBinding by lazy {
        ItemMenuBinding.inflate(
            layoutInflater
        )
    }

    override fun update(p0: Observable?, p1: Any?) {
    }

    override fun showItemMenu(music: MusicData) {
    }
}