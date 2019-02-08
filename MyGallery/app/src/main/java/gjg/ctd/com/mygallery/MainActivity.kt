package gjg.ctd.com.mygallery

import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton
import java.util.jar.Manifest
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    private val REQUEST_READ_EXTERNAL_STORAGE = 1000
    private val fragments = ArrayList<Fragment>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermission()
    }

    private fun getAllPhoto(){
        var cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            MediaStore.Images.ImageColumns.DATE_TAKEN+" DESC")
        // 데이터 URI, 어떤 항목, 가져올 조건, 조건 지정, 정렬 방법(날짜별로 내림차순)

        if(cursor!=null){
            while (cursor.moveToNext()){
                var uri = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                Log.d("DEBUGYU",uri)
                fragments.add(PhotoFragment.newInstance(uri))
            }
            cursor.close()
        }
        val adapter= MyPagerAdapter(supportFragmentManager)
        adapter.updateFragments(fragments)
        viewpager.adapter = adapter

        timer(period = 3000){
            runOnUiThread {
                if(viewpager.currentItem < adapter.count - 1){
                    viewpager.currentItem = viewpager.currentItem + 1
                }
                else{
                    viewpager.currentItem = 0
                }
            }
        }
    }
    private fun checkPermission(){
        if(ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            // 권한 허용되지 않음
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)){
                alert("사진 정보를 얻으려면 외부 저장소 권한이 필수로 필요합니다.","권한이 필요한 이유")
                {
                    yesButton {
                        requestPhotoPermission()
                    }
                    noButton {}
                }.show()
            }
            else{
                requestPhotoPermission()
            }
        }
        else{
            getAllPhoto()
        }
    }
    private fun requestPhotoPermission(){
        ActivityCompat.requestPermissions(this@MainActivity,
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
            REQUEST_READ_EXTERNAL_STORAGE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_READ_EXTERNAL_STORAGE ->{
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getAllPhoto()
                }
                else{
                    toast("권한 거부됨")
                }
                return
            }
        }
    }

}
