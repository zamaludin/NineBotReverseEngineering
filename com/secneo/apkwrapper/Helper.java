package com.secneo.apkwrapper;

import android.app.Application;
import android.content.Context;

public class Helper
{
  public static String APPNAME = "cn.ninebot.ninebot.BaseApp";
  public static String PKGNAME;
  public static String PPATH = null;
  public static ClassLoader cl;
  
  static
  {
    PKGNAME = "cn.ninebot.ninebot";
  }
  
  public static native void attach(Application paramApplication, Context paramContext);
  
  public static void stub() {}
}


/* Location:              D:\project\semiHumanoidRobot\unzip APK tool\cn.ninebot.ninebot_dex2jar.jar!\com\secneo\apkwrapper\Helper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */