package org.beautyinside

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.tasks.Task

class Intro : AppCompatActivity() {

    val REQUEST_CODE_UPDATE = 205
    lateinit var appUpdateManager : AppUpdateManager
    lateinit var appUpdateInfoTask: Task<AppUpdateInfo>
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE_UPDATE){
            if(resultCode != Activity.RESULT_OK){
                Toast.makeText(this,"업데이트가 취소 되었습니다.", Toast.LENGTH_SHORT).show()
                finishAffinity()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        appUpdateManager.appUpdateInfo.addOnSuccessListener {
            if(it.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS){
                appUpdateManager.startUpdateFlowForResult(
                    it, AppUpdateType.IMMEDIATE, this, REQUEST_CODE_UPDATE
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appUpdateManager = AppUpdateManagerFactory.create(this)
        appUpdateInfoTask = appUpdateManager.appUpdateInfo


        appUpdateManager?.let{
            it.appUpdateInfo.addOnSuccessListener {appUpdateInfo ->
                if(appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)){
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo, AppUpdateType.IMMEDIATE, this, REQUEST_CODE_UPDATE
                    )
                }
            }
        }

        try{
            Thread.sleep(1000);
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        catch(e:Exception){
            return
        }
    }
}
