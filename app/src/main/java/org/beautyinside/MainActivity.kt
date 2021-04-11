package org.beautyinside

import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import org.beautyinside.Adapter.TabAdapterMain

class MainActivity : AppCompatActivity() {

    private val adapter by lazy{ TabAdapterMain(supportFragmentManager) }
    //realm
    val PERMISSIONS_REQUEST_CODE = 100
    var REQUIRED_PERMISSIONS = arrayOf<String>( Manifest.permission.READ_EXTERNAL_STORAGE)

    companion object{
        lateinit var realm :Realm
    }

    //val realm = Realm.getDefaultInstance()

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Realm.init(this)
        realm = Realm.getDefaultInstance()


        var permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        if(permissionCheck != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){

            }else{
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE )
            }
        }

        vpMainActivity.adapter = adapter
        vpMainActivity.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                when(position){
                    0 -> { }
                    1 -> { }
                    2 -> { }
                }
            }
        })

        tabLayout_main.setupWithViewPager(vpMainActivity)
        tabLayout_main.getTabAt(0)?.setCustomView(R.layout.tablayout_icon_home)
        tabLayout_main.getTabAt(1)?.setCustomView(R.layout.tablayout_icon_category)
        tabLayout_main.getTabAt(2)?.setCustomView(R.layout.tablayout_icon_menual)

    }
}
